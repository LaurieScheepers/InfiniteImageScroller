package laurcode.com.infiniteimagescroller.custom;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import laurcode.com.infiniteimagescroller.custom.listeners.ImageDrawableFaderListener;
import laurcode.com.infiniteimagescroller.util.ImageUtil;
import timber.log.Timber;

/**
 * A useful little class that does an image fade-in, fade-out at specific time intervals
 * <p>
 * Created by lauriescheepers on 2017/11/17.
 */

@SuppressWarnings({"WeakerAccess", "UnnecessaryReturnStatement", "UnusedReturnValue", "unused"})
public class ImageDrawableFader {

    // The current drawable list index
    private int currentIndex;

    // The view that will be used for the fade in/fade out
    private ImageView viewToWorkOn;

    // The listener notifying the UI when this class has finished its work, or when there was an error.
    private ImageDrawableFaderListener listener;

    // Feel free to tweak these times according to your needs and desires
    private int fadeInTime = 450;
    private int fadeOutTime = 450;
    private int waitTime = 250;

    private List<Integer> drawableResIds = new ArrayList<>();

    public ImageDrawableFader addDrawable(@DrawableRes int resId) {
        drawableResIds.add(resId);

        return this;    // For a simple builder pattern approach
    }

    public ImageDrawableFader setDrawables(@NonNull List<Integer> resIds) {
        // First clear
        drawableResIds.clear();

        drawableResIds = resIds;

        return this;    // For a simple builder pattern approach
    }

    public ImageDrawableFader init(int fadeInTime, int fadeOutTime, int waitTime) {
        this.fadeInTime = fadeInTime;
        this.fadeOutTime = fadeOutTime;
        this.waitTime = waitTime;

        return this;    // For a simple builder pattern approach
    }

    public ImageDrawableFader start(@NonNull ImageView view, @NonNull ImageDrawableFaderListener listener) {
        this.viewToWorkOn = view;
        this.listener = listener;

        doFadeInWaitFadeOut();

        return this;    // For a simple builder pattern approach
    }

    /**
     * This method loads a drawable into an image view, fades it in, waits a while, then fades it out, before starting again with the next drawable in the list
     */
    public void doFadeInWaitFadeOut() {


        if (currentIndex == drawableResIds.size() - 1) {

            // This is the last image - let's pause a bit and also make the fade effect slower
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // Ignore
            }

            fadeInTime += 300;
            fadeOutTime += 300;
        }

        if (currentIndex >= drawableResIds.size()) {
            Timber.d("We are done!");

            listener.onComplete();
            return;
        }

        viewToWorkOn.postDelayed(() -> ImageUtil.loadImageWithFadeIn(viewToWorkOn,
                drawableResIds.get(currentIndex), fadeInTime, fadeOutTime, () -> viewToWorkOn.postDelayed(() -> {
                    currentIndex++;
                    ImageDrawableFader.this.doFadeInWaitFadeOut();  // Do a recursive call
                }, waitTime)), waitTime >> 1);
    }
}
