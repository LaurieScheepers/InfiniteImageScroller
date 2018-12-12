package laurcode.com.retire.main;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import laurcode.com.retire.BuildConfig;
import timber.log.Timber;

/**
 * The Main Application class of this app. This is mainly used for initialisation of various libraries
 * <br><br>
 * Created by lauriescheepers on 2017/11/06.
 */

@SuppressWarnings("FieldCanBeLocal")
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // To enable backwards-compatible vector resources
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // Lib initialisation only for release builds
        if (BuildConfig.RELEASE) {

            // Init Crashlytics
            Fabric.with(this, new Crashlytics());
        }

        // Lib initialisation only for debug builds
        if (BuildConfig.DEBUG) {
            // Init Timber, for logging.
            // NOTE: The tree is only planted in debug mode. The Timber.log() statements in the code will be a no-op in production.
            // Take a look at (https://github.com/JakeWharton/timber/issues/210)
            Timber.plant(new Timber.DebugTree());
        }
    }
}
