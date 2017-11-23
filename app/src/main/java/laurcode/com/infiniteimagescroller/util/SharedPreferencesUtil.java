package laurcode.com.infiniteimagescroller.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Contains helper methods related to the Shared Preferences
 *
 * <p/>
 * Created by laurie on 2017/09/08.
 */

public final class SharedPreferencesUtil {

    private SharedPreferencesUtil() {
        // Helper class - not publicly instantiable
    }

    private static final String USER_HAS_SEEN_SPLASH = "user_has_seen_splash";

    /**
     * Gets the boolean indicating that the user has seen the splash before
     */
    public static boolean hasUserSeenSplash(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(USER_HAS_SEEN_SPLASH, false);
    }

    /**
     * Sets and saves the boolean indicating that the user has seen the splash before in the shared preferences
     */
    public static void setUserHasSeenSplash(Context context, boolean userHasSeenSplash) {
        SharedPreferences.Editor sharedPreferencesEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        sharedPreferencesEditor.putBoolean(USER_HAS_SEEN_SPLASH, userHasSeenSplash);
        sharedPreferencesEditor.apply();
    }
}
