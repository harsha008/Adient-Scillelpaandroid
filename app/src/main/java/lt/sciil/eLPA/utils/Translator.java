package lt.sciil.eLPA.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import lt.sciil.eLPA.database.TranslationRepository;
import lt.sciil.eLPA.database.model.TranslatedValue;
import lt.sciil.eLPA.database.model.ViewToTranslate;

public class Translator {

    private static String TAG = Translator.class.getSimpleName();
    private List<TranslatedValue> translatedValueList = new ArrayList<>();
    private boolean updateNeeded = true;
    private PublishSubject<ViewToTranslate> viewTranslation = PublishSubject.create();
    private final TranslationRepository translationRepository;

    @Inject
    public Translator(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
        viewTranslation
                .flatMapMaybe(viewToTranslate -> {
                    checkifUpdateNeeded();
                    return Observable.fromIterable(translatedValueList)
                            .filter(translatedValue -> translatedValue.getTag().equals(viewToTranslate.getTag()))
                            .map(TranslatedValue::getTranslatedText)
                            .map(translatedText -> Pair.create(viewToTranslate, translatedText))
                            .firstElement();
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                    translateView(pair.first, pair.second);
                }, Throwable::printStackTrace);
    }

    public void translate(ViewToTranslate viewToTranslate) {
        if (viewToTranslate.getView() == null)
            return;
        String tag = viewToTranslate.getTag();
        if (tag == null || tag.equals(""))
            return;
        viewTranslation.onNext(viewToTranslate);
    }

    public void translateMenuItem(MenuItem menuItem, String tag) {
        ViewToTranslate<MenuItem> viewToTranslate = new ViewToTranslate<>(menuItem, tag);
        if (viewToTranslate.getView() == null)
            return;
        if (tag == null || tag.equals(""))
            return;
        viewTranslation.onNext(viewToTranslate);
    }

    private void checkifUpdateNeeded() {
        if (updateNeeded) {
            translatedValueList.clear();
            translatedValueList.addAll(translationRepository.getAll());
            updateNeeded = false;
        }
    }

    public String getTranslation(String tag) {
        checkifUpdateNeeded();
        return Observable.fromIterable(translatedValueList)
                .filter(translatedValue -> translatedValue.getTag().equals(tag))
                .map(TranslatedValue::getTranslatedText)
                .blockingFirst(null);
    }

    public String getTranslation(Context context, @StringRes int stringResource) {
        checkifUpdateNeeded();
        try {
            String tag = context.getResources().getResourceEntryName(stringResource);
            return Observable.fromIterable(translatedValueList)
                    .filter(translatedValue -> translatedValue.getTag().equals(tag))
                    .map(TranslatedValue::getTranslatedText)
                    .blockingFirst(context.getString(stringResource));
        } catch (Resources.NotFoundException exception) {
            Log.e(TAG, "Not found resource " + stringResource, exception);
            return "not found";
        }
    }

    public void translate(View... objects) {
        for (View o : objects) {
            ViewToTranslate viewToTranslate = new ViewToTranslate<>(o);
            if (viewToTranslate.getView() == null)
                continue;
            String tag = viewToTranslate.getTag();
            if (tag == null || tag.equals(""))
                continue;
            viewTranslation.onNext(viewToTranslate);
        }
    }

    private void translateView(ViewToTranslate viewToTranslate, String translatedText) {
        Object view = viewToTranslate.getView();
        if (view instanceof Button) {
            ((Button) view).setText(translatedText);
        } else if (view instanceof EditText) {
            ((EditText) view).setHint(translatedText);
        } else if (view instanceof TextView) {
            ((TextView) view).setText(translatedText);
        } else if (view instanceof Toolbar) {
            ((Toolbar) view).setTitle(translatedText);
        } else if (view instanceof MenuItem) {
            ((MenuItem) view).setTitle(translatedText);
        } else if (view instanceof Dialog) {
            ((Dialog) view).setTitle(translatedText);
        } else {
            Log.e("Translator", "Can't translate: " + view.toString());
        }
        if (viewToTranslate.getCallback() != null) {
            viewToTranslate.getCallback().onFinishedTranslating();
        }
    }

    public void setUpdateNeeded(boolean updateNeeded) {
        this.updateNeeded = updateNeeded;
    }

    public interface FinishedTranslating {
        void onFinishedTranslating();
    }
}
