package laurcode.com.infiniteimagescroller.startup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanks.htextview.typer.TyperTextView;
import com.wajahatkarim3.easyflipview.EasyFlipView;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import laurcode.com.infiniteimagescroller.R;
import laurcode.com.infiniteimagescroller.custom.ImageDrawableFader;
import laurcode.com.infiniteimagescroller.custom.SnackbarWrapper;
import laurcode.com.infiniteimagescroller.custom.listeners.ImageDrawableFaderListener;
import laurcode.com.infiniteimagescroller.databinding.ActivitySplashBinding;
import laurcode.com.infiniteimagescroller.events.PhotosRetrievedFailedEvent;
import laurcode.com.infiniteimagescroller.events.PhotosRetrievedSuccessEvent;
import laurcode.com.infiniteimagescroller.main.MainApplication;
import laurcode.com.infiniteimagescroller.main.view.MainActivity;
import laurcode.com.infiniteimagescroller.startup.viewmodel.SplashViewModel;
import laurcode.com.infiniteimagescroller.sync.SyncService;
import laurcode.com.infiniteimagescroller.util.CrashUtil;
import laurcode.com.infiniteimagescroller.util.SharedPreferencesUtil;
import laurcode.com.infiniteimagescroller.util.ViewUtil;
import timber.log.Timber;

/**
 * The Splash Activity is shown each time the app starts up. It handles the starting of the sync service, loading the initial freshest photos.
 * <p>
 * Created by lauriescheepers on 2017/11/05.
 */

@SuppressWarnings("FieldCanBeLocal")
public class SplashActivity extends AppCompatActivity {

    private SplashViewModel splashViewModel;

    @BindView(R.id.root_container)
    RelativeLayout rootContainer;

    // A flag indicating that the user has seen this splash before
    private boolean userHasSeenSplashBefore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userHasSeenSplashBefore = SharedPreferencesUtil.hasUserSeenSplash(this);

        if (userHasSeenSplashBefore) {
            goToMainActivity();
            return;
        }

        // Get a handle on the binding
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        // Create view model for the splash
        splashViewModel = new SplashViewModel();

        // Cache the view model (using the class name as the key)
        MainApplication.registerViewModel(SplashActivity.class, splashViewModel);

        // Set the view model on the generated view binding class
        binding.setViewModel(splashViewModel);

        // Bind Butterknife for easy access to the views in this activity
        ButterKnife.bind(this);
    }

    private void goToMainActivity() {
        SharedPreferencesUtil.setUserHasSeenSplash(this, true);

        // TODO go to main activity
    }
}
