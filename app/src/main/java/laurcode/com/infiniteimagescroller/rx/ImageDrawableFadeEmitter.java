package laurcode.com.infiniteimagescroller.rx;

import android.annotation.SuppressLint;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import laurcode.com.infiniteimagescroller.rx.listeners.ImageDrawableFadeEmitterListener;
import laurcode.com.infiniteimagescroller.util.ViewUtil;
import laurcode.com.infiniteimagescroller.util.callbacks.FadeInAnimationCompletedCallback;
import laurcode.com.infiniteimagescroller.util.callbacks.FadeOutAnimationCompletedCallback;
import timber.log.Timber;

/**
 * A debouncer that does an image fade-in, fade-out at certain time intervals
 * <p>
 * Created by lauriescheepers on 2017/11/17.
 */

@SuppressWarnings("WeakerAccess")
public class ImageDrawableFadeEmitter {

    private static int currentIndex = 0;

    @SuppressLint("StaticFieldLeak")
    private static ImageView viewToWorkOn;  // This could be a memory leak, but it is ensured that the view is cleaned up later and available for the GC'er
    private static ImageDrawableFadeEmitterListener listener;

    // Feel free to tweak these times according to your desires
    public static final int FADE_IN_TIME = 500;
    public static final int FADE_OUT_TIME = 500;

    private static List<Integer> drawableResIds = new ArrayList<>();

    public static void addDrawable(@DrawableRes int resId) {
        drawableResIds.add(resId);
    }

    public static void setDrawables(@NonNull List<Integer> resIds) {
        drawableResIds = resIds;
    }

    public static void start(@NonNull ImageView view, @DrawableRes int startDrawable, @NonNull ImageDrawableFadeEmitterListener l) {
        viewToWorkOn = view;
        listener = l;

        if (drawableResIds.isEmpty()) {
            addDrawable(startDrawable);
        }

        // Get and subscribe to the observable. NOTE: as we are working with views, all of the operations must be done on the UI thread
        getObservable()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    public static Observable<Object> getObservable() {
        return Observable.create(emitter -> {

            while (currentIndex < drawableResIds.size()) {
                Timber.d("Doing fade-in-fade-out of image index " + currentIndex);

                // Fade in the image
                doFadeIn(currentIndex, () -> {

                    viewToWorkOn.postDelayed(() -> {
                        // Fade out the image. NOTE: we don't do this for the last image
                        if (currentIndex < drawableResIds.size()) {
                            doFadeOut(currentIndex, new FadeOutAnimationCompletedCallback() {
                                @Override
                                public void onCompleted() {
                                    currentIndex++;
                                    emitter.onNext(null);
                                }
                            });
                        }
                    }, 500);
                });
            }

            Timber.d("We are done.");

            emitter.onComplete();
        });
    }

    private static Observer<Object> getObserver() {

        return new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Timber.d("Started subscribing to observer.");
            }

            @Override
            public void onNext(Object obj) {
                Timber.d("onNext() received from observer.");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "There was an error during the flow of operations");

                listener.onError();

                cleanup();
            }

            @Override
            public void onComplete() {
                Timber.d("Operation completed. Notifying listener and doing cleanup()");

                listener.onComplete();

                cleanup();
            }
        };
    }

    /**
     * Fades in the image using the first drawable resource in the list, and returns the next drawable res id in the list
     * @param resIndex the current index of the resource in the list
     * @return the next drawable res id in the list
     */
    private static void doFadeIn(int resIndex, @NonNull final FadeInAnimationCompletedCallback fadeInAnimationCompletedCallback) {

        if (drawableResIds == null) {
            return;
        }

        if (drawableResIds.isEmpty()) {
            return;
        }

        Integer resInt = drawableResIds.get(resIndex);

        Timber.d("Doing fade in of image");

        // Set the correct drawable resource
        viewToWorkOn.setImageResource(resInt);

        if (resIndex == 0 || (resIndex == drawableResIds.size() - 1)) {
            // A bit longer time for the first and last image
            ViewUtil.fadeViewIn(viewToWorkOn, (int)(FADE_IN_TIME * 1.5), fadeInAnimationCompletedCallback);
        } else {
            ViewUtil.fadeViewIn(viewToWorkOn, FADE_IN_TIME, fadeInAnimationCompletedCallback);
        }
    }

    /**
     * Fades out the image using a drawable resource in the list, and returns the next drawable res id in the list
     * @param resIndex the current index of the resource in the list
     */
    private static void doFadeOut(int resIndex, @NonNull final FadeOutAnimationCompletedCallback fadeOutAnimationCompletedCallback) {
        if (drawableResIds == null) {
            return;
        }

        if (drawableResIds.isEmpty()) {
            return;
        }

        if (resIndex + 1 < drawableResIds.size()) {
            Integer nextResInt = drawableResIds.get(resIndex + 1);

            Timber.d("Doing fade out of image");

            if (viewToWorkOn == null) {
                throw new RuntimeException("The view has been cleaned up when it shouldn't have been! Timing must be off.");
            }

            // Fade out and set the next resource drawable on the image
            ViewUtil.fadeViewOut(viewToWorkOn, FADE_OUT_TIME, () -> {
                viewToWorkOn.setImageResource(nextResInt);
                fadeOutAnimationCompletedCallback.onCompleted();
            });
        }
    }

    private static void cleanup() {
        // Fade in the view if it's not currently visible
        if (viewToWorkOn != null && ViewUtil.isViewNotVisible(viewToWorkOn)) {
            ViewUtil.fadeViewIn(viewToWorkOn);
        }

        currentIndex = 0;
        viewToWorkOn = null;
        drawableResIds.clear();
        drawableResIds = null;
    }
}
