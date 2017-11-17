package laurcode.com.infiniteimagescroller.databinding;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import java.util.Arrays;

import laurcode.com.infiniteimagescroller.R;
import laurcode.com.infiniteimagescroller.main.GlideApp;
import timber.log.Timber;

/**
 * Collection of custom data binding resource attributes
 * <p>
 * Created by lauriescheepers on 2017/11/16.
 */

@SuppressWarnings("WeakerAccess")
public class DataBindingAdapters {

    @BindingAdapter("imageUrl")
    public static void loadImageUrl(ImageView view, String url) {
        // Load into image view with Glide
        GlideApp
                .with(view)
                .load(url)
                .placeholder(new ColorDrawable(ContextCompat.getColor(view.getContext(), R.color.placeholder_bg_color)))
                .error(R.drawable.ic_error_404)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    @BindingAdapter("imageRes")
    public static void loadImageRes(ImageView view, @DrawableRes int resId) {
        // Load the image as a resource
        view.setImageResource(resId);
    }

    /**
     * Adds a bounce animation to the given view when touched.
     */
    @BindingAdapter("bounceOnTouch")
    public static void bounceOnTouch(@NonNull View view, boolean bounce) {

        if (!bounce) {
            return;
        }

        Spring spring = SpringSystem.create().createSpring();
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.12f);

                if (ViewCompat.isAttachedToWindow(view)) {
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            }
        });

        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    spring.setEndValue(1);
                    break;
                case MotionEvent.ACTION_UP:
                    if (spring.getEndValue() != 0) {
                        // ACTION_CANCEL or ACTION_OUTSIDE was not called already
                        // i.e. we don't want to click when user dragged the finger outside of view bounds.
                        view.performClick();
                    }
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_CANCEL:
                    spring.setEndValue(0);
                    break;
            }
            return true;
        });
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("drawText")
    public static void drawText(@NonNull TextView textView, boolean draw) {

        if (!draw) {
            return;
        }

        int length = textView.getText().length();

        char[] chars = new char[length];

        // Fill the char array
        TextUtils.getChars(textView.getText(), 0, length, chars, 0);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(chars[0], chars[length - 1]);
        valueAnimator.setDuration(1000);

        valueAnimator.addUpdateListener(animation -> {
            String text = textView.getText().toString().concat(Arrays.toString(Character.toChars((Integer) valueAnimator.getAnimatedValue())));

            Timber.d("Text is now: " + text);

            textView.setText(text);
        });

        valueAnimator.start();
    }
}
