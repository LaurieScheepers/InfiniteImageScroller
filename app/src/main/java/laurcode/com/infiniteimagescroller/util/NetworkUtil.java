package laurcode.com.infiniteimagescroller.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Contains helper methods related to the network
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public final class NetworkUtil {

    private NetworkUtil() {
        // Not publicly instantiable
    }

    /**
     * Returns true if connected to the internet, false if not
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
