package lt.sciil.eLPA.utils;


import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageLoading {

    private final Picasso picasso;

    public ImageLoading(Picasso picasso) {
        this.picasso = picasso;
    }

    public void loadImage(ImageView target, String filePath, @DrawableRes int errorDrawable) {
        loadImage(target, filePath, errorDrawable, new Callback.EmptyCallback());
    }

    public void loadImage(ImageView target, String filePath, @DrawableRes int errorDrawable, Callback callback) {
        File file = new File(filePath);
        picasso.load(file)
                .centerInside()
                .fit()
                .error(errorDrawable)
                .into(target, callback);
    }

    public void loadImage(ImageView target, String filePath, @DrawableRes int errorDrawable, ProgressBar progressBar) {
        loadImage(target, filePath, errorDrawable, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImage(ImageView target, @DrawableRes int drawable, @DrawableRes int errorDrawable, ProgressBar progressBar) {
        loadImage(target, drawable, errorDrawable, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void loadImage(ImageView target, @DrawableRes int drawable, @DrawableRes int errorDrawable, Callback callback) {
        picasso.load(drawable)
                .centerInside()
                .fit()
                .error(errorDrawable)
                .into(target, callback);
    }
}
