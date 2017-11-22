package laurcode.com.infiniteimagescroller.rx;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import laurcode.com.infiniteimagescroller.rx.listeners.ImageDrawableFaderListener;
import laurcode.com.infiniteimagescroller.util.ImageUtil;
import timber.log.Timber;

/**
 * A useful little class that does an image fade-in, fade-out at specific time intervals
 * <p>
 * Created by lauriescheepers on 2017/11/17.
 */

@SuppressWarnings({"WeakerAccess", "UnnecessaryReturnStatement", "UnusedReturnValue"})
public class ImageDrawableFader {

    // The current drawable list index
    private int currentIndex;

    // The view that will be used for the fade in/fade out
    private ImageView viewToWorkOn;

    // The listener notifying the UI when this class has finished its work, or when there was an error.
    private ImageDrawableFaderListener listener;

    // Feel free to tweak these times according to your needs and desires
    public static final int FADE_IN_TIME = 850;
    public static final int FADE_OUT_TIME = 850;
    public static final int WAIT_TIME = 500;

    private List<Integer> drawableResIds = new ArrayList<>();

    public ImageDrawableFader addDrawable(@DrawableRes int resId) {
        drawableResIds.add(resId);

        return this;
    }

    public ImageDrawableFader setDrawables(@NonNull List<Integer> resIds) {
        drawableResIds = resIds;

        return this;
    }

    public ImageDrawableFader start(@NonNull ImageView view, @NonNull ImageDrawableFaderListener listener) {
        this.viewToWorkOn = view;
        this.listener = listener;

        doFadeInWaitFadeOut();

        return this;
    }

    /**
     * This method loads a drawable into an image view, fades it in, waits a while, then fades it out, before starting again with the next drawable in the list
     */
    public void doFadeInWaitFadeOut() {

        Timber.d("Attempting to load drawable with index = " + currentIndex);

        if (currentIndex == drawableResIds.size()) {
            Timber.d("We are done!");

            listener.onComplete();
            return;
        }

        viewToWorkOn.postDelayed(() -> ImageUtil.loadImageWithFadeIn(viewToWorkOn,
                drawableResIds.get(currentIndex), FADE_IN_TIME, FADE_OUT_TIME, () -> viewToWorkOn.postDelayed(() -> {
                    currentIndex++;
                    doFadeInWaitFadeOut();
        }, WAIT_TIME)), WAIT_TIME);
    }
}
