package laurcode.com.retire.databinding;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import timber.log.Timber;

/**
 * Insert description of class here
 * <br><br>
 * Created by lauriescheepers on 2018/01/01.
 */

public class DataBindingAdapters {

    // Adjusting the TENSION constant will change the speed of the animation; the higher the tension the faster the speed.
    private static final double SPRING_TENSION = 260;

    // Adjusting the DAMPER constant will change the bounce at the end of the animation, the lower the damper the more bounce.
    private static final double SPRING_DAMPER = 9;     // also called friction

    /**
     * Adds a bounce animation to the given view when touched.
     */
    @BindingAdapter("android:bounceOnTouch")
    public static void bounceOnTouch(@NonNull View view, boolean bounce) {

        Timber.d("BOUNCING ON TOUCH!");

        if (!bounce) {
            return;
        }

        Timber.d("SETTING UP SPRING!");

        Spring spring = SpringSystem.create().createSpring();

        SpringConfig config = new SpringConfig(SPRING_TENSION, SPRING_DAMPER);

        spring.setSpringConfig(config);

        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.35f);

                if (ViewCompat.isAttachedToWindow(view)) {
                    view.setScaleX(scale);
                    view.setScaleY(scale);
                }
            }
        });

        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    // When pressed start solving the spring to 1.
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

                    // When released start solving the spring to 0.
                    spring.setEndValue(0);
                    break;
            }
            return true;
        });
    }
}
