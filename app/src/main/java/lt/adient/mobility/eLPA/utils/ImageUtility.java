package lt.adient.mobility.eLPA.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import lt.adient.mobility.eLPA.database.model.PictureQuality;

public class ImageUtility {


    public static String imageToBase64(String filePath) {
        try {
            InputStream inputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decodeAndSaveImage(String path, String base64Image) throws IOException {
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        FileOutputStream stream = new FileOutputStream(path);
        stream.write(decodedString);
        stream.close();
        return path;
    }


    public static File compressImage(String photoFilePath, int sampleSize, int quality, PictureQuality pictureQuality) throws IOException, InterruptedException {
        File photoFile = new File(photoFilePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;

        FileInputStream in = new FileInputStream(photoFile);
        Bitmap original = BitmapFactory.decodeStream(in, null, options);
        Bitmap resizedAndRotated = rotateAndResizeBitmap(photoFilePath, original, pictureQuality);
        in.close();

        OutputStream stream = new FileOutputStream(photoFile);
        resizedAndRotated.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        stream.close();

        original.recycle();
        resizedAndRotated.recycle();
        return photoFile;
    }

    public static Bitmap rotateAndResizeBitmap(String photoPath, Bitmap originalBitmap, PictureQuality quality) throws IOException {
        ExifInterface exif = new ExifInterface(photoPath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        Log.d("EXIF", "Exif: " + orientation);
        Matrix matrix = new Matrix();
        final int originalBitmapWidth = originalBitmap.getWidth();
        final int originalBitmapHeight = originalBitmap.getHeight();
        float aspectRatio = originalBitmapWidth /
                (float) originalBitmapHeight;
        int width = 0, height = 0;

        if (orientation == 6) {
            matrix.postRotate(90);
        } else if (orientation == 3) {
            matrix.postRotate(180);
        } else if (orientation == 8) {
            matrix.postRotate(270);
        }
        switch (quality) {
            case SD:
                width = 480;
                height = Math.round(width / aspectRatio);
                break;
            case HD:
                width = 720;
                height = Math.round(width / aspectRatio);
                break;
            case FULL_HD:
                width = 1080;
                height = Math.round(width / aspectRatio);
                break;
            case ORIGINAL:
                width = originalBitmapWidth;
                height = originalBitmapHeight;
                break;
        }
        final float sx = width / (float) originalBitmapWidth;
        final float sy = height / (float) originalBitmapHeight;
        matrix.postScale(sx, sy);
        return Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmapWidth, originalBitmapHeight, matrix, true);
    }
}
