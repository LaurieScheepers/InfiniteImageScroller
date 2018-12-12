package laurcode.com.retire.util;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import laurcode.com.retire.R;
import laurcode.com.retire.util.callbacks.FadeInAnimationCompletedCallback;
import laurcode.com.retire.util.callbacks.FadeOutAnimationCompletedCallback;
import timber.log.Timber;

/**
 * Contains helper methods related to views. For animations, add a static helper method here
 * that can be called on any view.
 *
 * <p/>
 * Created by Laurie on 2017/09/11.
 */

@SuppressWarnings({"unused", "SameParameterValue"})
public final class ViewUtil {

    private ViewUtil() {
        // Not publicly instantiable
    }

    // Default durations (in milliseconds)
    public static final int DEFAULT_ROTATE_FOREVER_ANIMATION_DURATION = 2 * 1000;
    public static final int DEFAULT_COLOR_FADE_ANIMATION_DURATION = 1000;
    public static final int LOADING_SCREEN_COLOR_FADE_DURATION = 5 * 1000;

    /**
     * Convenience method for fading the specified view in
     */
    public static void fadeViewIn(@NonNull final View v) {
        fadeViewIn(v, 0, null);
    }

    /**
     * Convenience method for fading the specified view in
     */
    public static void fadeViewIn(@NonNull final View v, @Nullable final FadeInAnimationCompletedCallback fadeInAnimationCompletedCallback) {
        fadeViewIn(v, 500, fadeInAnimationCompletedCallback);
    }

    /**
     * Convenience method for fading the specified view in
     */
    public static void fadeViewIn(@NonNull final View v, final int duration) {
        fadeViewIn(v, duration, null);
    }

    public static void fadeViewIn(@NonNull final View v, final int duration, @Nullable final FadeInAnimationCompletedCallback fadeInAnimationCompletedCallback) {
        if (v.getVisibility() != View.VISIBLE) {

            ViewPropertyAnimator anim = v.animate();

            if (anim != null) {
                anim.setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (v.getVisibility() != View.VISIBLE) {
                            Timber.d("For some reason the view is not visible after fade in. Setting it to visible.");
                            v.setVisibility(View.VISIBLE);
                        }

                        if (fadeInAnimationCompletedCallback != null) {
                            fadeInAnimationCompletedCallback.onCompleted();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });

                v.setAlpha(0);
                v.setVisibility(View.VISIBLE);

                if (duration > 0) {
                    anim.setDuration(duration);
                }

                anim.alpha(1);
            } else {
                v.setAlpha(1);
                v.setVisibility(View.VISIBLE);
            }
        } else {
            Timber.d("View is already visible, so ignoring fade in.");
        }
    }

    /**
     * Convenience method for fading the specified view out
     */
    public static void fadeViewOut(@NonNull final View v) {
        fadeViewOut(v, 500);
    }

    /**
     * Fade out and hide an image view over a specified duration.
     *
     * @param view     The view that will be faded out
     * @param callback A callback that will be invoked when fade out is done
     */
    public static void fadeViewOut(@NonNull final View view, @Nullable FadeOutAnimationCompletedCallback callback) {
        fadeViewOut(view, 500, callback);
    }

    /**
     * Fade out and hide an image view over a specified duration.
     *
     * @param view     The view that will be faded out
     * @param duration The duration in milliseconds of the fade out.
     */
    public static void fadeViewOut(@NonNull final View view, final int duration) {
        fadeViewOut(view, duration, null);
    }

    /**
     * Fade out and hide an image view over a specified duration.
     *
     * @param view     The view that will be faded out
     * @param duration The duration in milliseconds of the fade out.
     * @param callback Callback that will be called when the animation has completed
     */
    public static void fadeViewOut(@NonNull final View view, final int duration, @Nullable final FadeOutAnimationCompletedCallback callback) {

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(duration);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);

                if (callback != null) {
                    callback.onCompleted();
                }
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        view.startAnimation(fadeOut);
    }

    /**
     * Convenience method to start a rotate forever animation. Default duration for this animation is 2 seconds
     * @param view the view to start rotating
     */
    public static void rotateForever(@NonNull final View view) {
        rotateForever(view, DEFAULT_ROTATE_FOREVER_ANIMATION_DURATION);
    }

    /**
     * Starts a rotating animation that continues forever
     * @param duration the duration of the animation
     * @param view the view to rotate
     */
    public static void rotateForever(@NonNull final View view, final int duration) {

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(duration);
        rotateAnimation.setRepeatCount(Animation.INFINITE);

        view.startAnimation(rotateAnimation);
    }

    /**
     * Convenience method to color fade a view
     */
    public static void colorFade(@NonNull View view, int startColorResId, int endColorResId) {
        colorFade(view, startColorResId, endColorResId, DEFAULT_COLOR_FADE_ANIMATION_DURATION);
    }

    /**
     * Fades the background of this view from one color to another
     * @param view the view to color fade
     * @param startColorResId the start color resource id
     * @param endColorResId the end color resource id
     * @param duration the duration of the animation
     */
    public static void colorFade(@NonNull View view, int startColorResId, int endColorResId, int duration) {
        ColorDrawable[] color = {
                new ColorDrawable(ContextCompat.getColor(view.getContext(), startColorResId)),
                new ColorDrawable(ContextCompat.getColor(view.getContext(), endColorResId)),
        };

        TransitionDrawable trans = new TransitionDrawable(color);

        view.setBackground(trans);
        trans.setCrossFadeEnabled(true);
        trans.startTransition(duration);
    }

    /**
     * Grays out an icon drawable (indicating a disabled state)
     */
    public static void grayOutIconDrawable(@NonNull Context context, @NonNull Drawable drawable) {
        drawable.mutate();
        drawable.setColorFilter(ContextCompat
                .getColor(context, R.color.grey_600), PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Helper function to clear this view of any margins
     */
    public static void clearMargins(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

            p.setMargins(0, 0, 0, 0);
            view.requestLayout();
        }
    }

    /**
     * Helper function to clear this view of any padding
     */
    public static void clearPadding(View view) {
        view.setPadding(0, 0, 0,0);
    }

    /**
     * Helper method to get the action bar height
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;

        TypedValue tv = new TypedValue();

        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        return actionBarHeight;
    }

    /**
     * Helper method to check if a view exists in an activity
     * @param activity the activity that should contain the view
     * @param viewId the id of the view as defined in the layout
     * @return true if view exists in the activity layout, false if not.
     */
    public static boolean doesViewExistInActivity(@NonNull Activity activity, @IdRes int viewId) {
        return activity.findViewById(viewId) != null;
    }

    /**
     * Helper method to check if a view exists in a fragment
     * @param fragment the fragment containing the view
     * @param viewId the id of the view as defined in the layout file.
     * @return true if view exists in the fragment layout, false if not
     */
    public static boolean doesViewExistInFragment(@NonNull Fragment fragment, @IdRes int viewId) {

        if (fragment.getView() == null) {
            Timber.e("Fragment is not ready yet. onCreateView() must be called before getting a view in the fragment by its id.");
            return false;
        }

        return fragment.getView().findViewById(viewId) != null;
    }

    public static @CheckResult View getView(@NonNull Fragment fragment, @IdRes int viewId) {

        if (fragment.getView() == null) {
            Timber.e("Fragment is not ready yet. onCreateView() must be called before getting a view in the fragment by its id.");
            return null;
        }

        return fragment.getView().findViewById(viewId);
    }

    public static @CheckResult View getView(@NonNull Activity activity, @IdRes int viewId) {
        return activity.findViewById(viewId);
    }

    /**
     * Helper method to make a view visible, setting its visibility to {@link View#VISIBLE}.
     */
    public static void showViewInFragment(@NonNull View view) {
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Helper method to setting a view visibility to be {@link View#GONE}, effectively hiding the view.
     */
    public static void hideView(@NonNull View view) {
        view.setVisibility(View.GONE);
    }

    /**
     * Helper method to setting a view visibility in an activity to be {@link View#VISIBLE}
     */
    public static void showViewInActivity(@NonNull Activity activity, @IdRes int viewId) {
        View view = getView(activity, viewId);

        if (view == null) {
            // Try parent
            showViewInActivity(activity.getParent(), viewId);
        }

        if (view != null) {
            showViewInFragment(view);
        } else {
            Timber.e("View does not exist in activity " + activity.getClass().getSimpleName());
        }
    }

    /**
     * Helper method to setting a view visibility in a fragment to be {@link View#VISIBLE}
     */
    public static void showViewInFragment(@NonNull Fragment fragment, @IdRes int viewId) {
        View view = getView(fragment, viewId);

        if (view == null) {
            // Try parent
            showViewInFragment(fragment.getParentFragment(), viewId);
        }

        if (view != null) {
            showViewInFragment(view);
        } else {
            Timber.e("View does not exist in fragment" + fragment.getClass().getSimpleName());
        }
    }

    /**
     * Helper method to setting a view visibility in an activity to be {@link View#GONE}, effectively hiding the view.
     */
    public static void hideViewInActivity(@NonNull Activity activity, @IdRes int viewId) {
        View view = getView(activity, viewId);

        if (view == null) {
            // Try parent
            hideViewInActivity(activity.getParent(), viewId);
        }

        if (view != null) {
            hideView(view);
        } else {
            Timber.e("View does not exist in activity " + activity.getClass().getSimpleName());
        }
    }

    /**
     * Helper method to setting a view visibility in a fragment to be {@link View#GONE}, effectively hiding the view.
     */
    public static void hideViewInFragment(@NonNull Fragment fragment, @IdRes int viewId) {
        View view = getView(fragment, viewId);

        if (view == null) {
            // Try parent
            hideViewInFragment(fragment.getParentFragment(), viewId);
        }

        if (view != null) {
            hideView(view);
        } else {
            Timber.e("View does not exist in fragment" + fragment.getClass().getSimpleName());
        }
    }

    /**
     * Convenience method to check whether a view is visible
     */
    public static boolean isViewVisible(@NonNull View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    /**
     * Convenience method to check whether a view is NOT visible (meaning it's INVISIBLE, or GONE)
     */
    public static boolean isViewNotVisible(@NonNull View view) {
        return !isViewVisible(view);
    }
}
