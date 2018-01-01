package laurcode.com.retire.custom;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import laurcode.com.retire.util.HackUtil;

/**
 * A wrapper for a snack bar. Enables animations even when Google deems it not correct.
 * For a visual representation of this class
 * see <a href="http://1.bp.blogspot.com/-NBUc5zHpfwg/Vi2K8OG7e1I/AAAAAAAABW8/EyTRlc2uBCw/s1600/Candy%2BWrappers.jpg">here</a>.
 *
 * <p/>
 * Created by lauriescheepers on 2017/09/29.
 */

public class SnackbarWrapper {

    public Snackbar snackbar;

    public SnackbarWrapper(View root, @StringRes int stringRes, int duration) {
        this(root, stringRes, -1, duration, null);
    }

    public SnackbarWrapper(View root, @StringRes int stringRes, int duration, @StringRes int actionStringRes, @Nullable View.OnClickListener onClickListener) {
        snackbar = Snackbar.make(root, stringRes, duration);

        // Apply hack
        snackbar = HackUtil.hackSnackbarAnimation(snackbar);

        if (actionStringRes != -1 && onClickListener != null) {
            snackbar.setAction(actionStringRes, onClickListener);
        }
    }

    /**
     * Convenience method to show the snackbar
     * @param rootView the root view of the layout in which this snackbar is going to show
     * @param stringRes the string resource id of the message
     * @param duration the duration of the showing of the snackbar
     */
    public static void show(View rootView, @StringRes int stringRes, int duration) {
        show(rootView, stringRes, duration, -1, null);
    }

    /**
     * Convenience method to show the snackbar with an action
     * @param rootView the root view of the layout in which this snackbar is going to show
     * @param stringRes the string resource id of the message
     * @param duration the duration of the showing of the snackbar
     * @param actionStringRes the string resource id of the action button
     * @param onClickListener the on click listener that will do the work once the button is clicked
     *
     */
    public static void show(View rootView, @StringRes int stringRes, int duration, @StringRes int actionStringRes, @Nullable View.OnClickListener onClickListener) {
        new SnackbarWrapper(rootView, stringRes, duration, actionStringRes, onClickListener).snackbar.show();
    }
}
