package laurcode.com.infiniteimagescroller.util;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import laurcode.com.infiniteimagescroller.util.callbacks.FadeInAnimationCompletedCallback;

/**
 * Helper class for anything related to images
 * <p>
 * Created by lauriescheepers on 2017/11/22.
 */

public class ImageUtil {

    /**
     * Loads a drawable into an image and fades it in
     */
    public static void loadImageWithFadeIn(@NonNull final ImageView imageView,
                                           @DrawableRes final int resId,
                                           final int fadeInTime,
                                           final int fadeOutTime,
                                           @NonNull final FadeInAnimationCompletedCallback fadeInAnimationCompletedCallback) {

        // The image view must be not visible initially
        if (ViewUtil.isViewVisible(imageView)) {
            ViewUtil.fadeViewOut(imageView, fadeOutTime, () -> {
                // Load the image as a resource
                imageView.setImageResource(resId);

                // And fade it in
                ViewUtil.fadeViewIn(imageView, fadeInTime, fadeInAnimationCompletedCallback);
            });
        } else {
            // Load the image as a resource
            imageView.setImageResource(resId);

            // And fade it in
            ViewUtil.fadeViewIn(imageView, fadeInTime, fadeInAnimationCompletedCallback);
        }
    }
}
