package lt.sciil.eLPA.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.sciil.eLPA.App;
import lt.sciil.eLPA.R;
import lt.sciil.eLPA.events.DeletePictureEvent;
import lt.sciil.eLPA.events.TakePictureEvent;
import lt.sciil.eLPA.utils.Translator;
import uk.co.senab.photoview.PhotoView;

import static lt.sciil.eLPA.utils.Utility.showTranslatedToast;


public class FullScreenDialog extends DialogFragment {

    private static final String IMAGE_PATH = "imagePath";
    private static final String ANSWER_PHOTO = "answerPhoto";

    @BindView(R.id.takePicture)
    protected ImageButton takePictureButton;
    @BindView(R.id.deletePicture)
    protected ImageButton deletePictureButton;
    @BindView(R.id.close)
    protected ImageButton closeButton;
    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;
    @BindView(R.id.full_size_image_view)
    protected PhotoView imageView;
    @Inject
    protected Translator translator;


    public static FullScreenDialog newInstance(@NonNull String imagePath, @NonNull Boolean answerPhoto) {
        FullScreenDialog fullScreenDialog = new FullScreenDialog();
        fullScreenDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_PATH, imagePath);
        bundle.putBoolean(ANSWER_PHOTO, answerPhoto);
        fullScreenDialog.setArguments(bundle);
        return fullScreenDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.full_size_image_dialog, container, true);
        ButterKnife.bind(this, view);
        App.getApplicationComponent().inject(this);
        String imagePath = getArguments().getString(IMAGE_PATH, "");
        Boolean answerPhoto = getArguments().getBoolean(ANSWER_PHOTO, false);
        takePictureButton.setVisibility(View.GONE);
        deletePictureButton.setVisibility(View.GONE);
        closeButton.setVisibility(View.GONE);
        File file = new File(imagePath);
        Picasso.with(getActivity()).load(file).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
                takePictureButton.setVisibility(answerPhoto ? View.VISIBLE : View.GONE);
                deletePictureButton.setVisibility(answerPhoto ? View.VISIBLE : View.GONE);
                closeButton.setVisibility(View.VISIBLE);
                closeButton.setOnClickListener(v -> {
                    dismiss();
                });
                if (answerPhoto) {
                    takePictureButton.setOnClickListener(view1 -> {
                        EventBus.getDefault().post(new TakePictureEvent(imagePath));
                        dismiss();
                    });
                    deletePictureButton.setOnClickListener(view1 -> {
                        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialog)
                                .setMessage(translator.getTranslation(getActivity(), R.string.photo_delete_confirmation))
                                .setCancelable(false)
                                .setPositiveButton(translator.getTranslation(getActivity(), R.string.yes), (dialog, which) -> {
                                    EventBus.getDefault().post(new DeletePictureEvent(imagePath));
                                    dialog.dismiss();
                                })
                                .setNegativeButton(translator.getTranslation(getActivity(), R.string.no), (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .create();
                        alertDialog.show();
                    });
                }
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
                showTranslatedToast(getActivity(), R.string.photo_is_not_available, Toast.LENGTH_LONG, translator);
                dismiss();
            }
        });

        return view;
    }
}
