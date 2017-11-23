package laurcode.com.infiniteimagescroller.util;

import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.accessibility.AccessibilityManager;

import java.lang.reflect.Field;

import timber.log.Timber;

/**
 * Hacking Android... For those cases where you just can't fix something properly because you are
 * pressed for time or just because an elegant solution doesn't exist. Yet.
 *
 * <p/>
 *
 * Please when adding a hack here give some context to why you are doing it.
 *
 * <p/>
 * Created by lauriescheepers on 2017/09/29.
 */

public class HackUtil {

    /**
     * A hack that uses reflection to set a boolean in Snackbar.java to always make Snackbar animations appear.
     * For some reason Google thinks it is correct to disable snackbar animations when some app on the device
     * uses special Accessibility Functions. Why they only do this for a snackbar I don't know.
     * @see <a href="https://stackoverflow.com/questions/37221914/snackbar-and-other-animations-stopped-working-on-some-android-devices/37233527">Snackbar and other animations stopped working on some Android devices

    </a> <br>
     */
    public static Snackbar hackSnackbarAnimation(Snackbar snackitySnack) {
        try {
            Field mAccessibilityManagerField = BaseTransientBottomBar.class.getDeclaredField("mAccessibilityManager");
            mAccessibilityManagerField.setAccessible(true);

            AccessibilityManager accessibilityManager = (AccessibilityManager) mAccessibilityManagerField.get(snackitySnack);

            Field mIsEnabledField = AccessibilityManager.class.getDeclaredField("mIsEnabled");

            mIsEnabledField.setAccessible(true);
            mIsEnabledField.setBoolean(accessibilityManager, false);

            mAccessibilityManagerField.set(snackitySnack, accessibilityManager);
        } catch (Exception e) {
            Timber.e(e, "Snackbar reflection hack failed");
        }

        return snackitySnack;
    }
}
