package laurcode.com.retire.util.callbacks;

import android.animation.ValueAnimator;

/**
 * A listener that will notify when the color fade has been completed
 *
 * <p/>
 * Created by laurie on 2017/09/12.
 */

public interface ColorFadeAnimationCompletedCallback {
    void onColorFadeComplete(ValueAnimator animator);
}
