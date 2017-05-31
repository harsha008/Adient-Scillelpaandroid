package lt.sciil.eLPA.database.model;

import android.view.View;

import lt.sciil.eLPA.utils.Translator;

public class ViewToTranslate<T> {

    private final T view;
    private final String tag;
    private final Translator.FinishedTranslating callBack;

    public ViewToTranslate(T view, Object tag) {
        this.view = view;
        this.tag = (String) tag;
        this.callBack = null;
    }

    public ViewToTranslate(T view) {
        this.view = view;
        this.callBack = null;
        if (view instanceof View) {
            this.tag = (String) ((View) view).getTag();
        } else {
            tag = null;
        }
    }

    public ViewToTranslate(T view, Translator.FinishedTranslating callback) {
        this.view = view;
        this.callBack = callback;
        if (view instanceof View) {
            this.tag = (String) ((View) view).getTag();
        } else {
            tag = null;
        }
    }


    public T getView() {
        return view;
    }

    public String getTag() {
        return tag;
    }

    public Translator.FinishedTranslating getCallback() {
        return callBack;
    }
}
