package laurcode.com.retire.startup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import laurcode.com.retire.R;
import laurcode.com.retire.databinding.ActivitySplashBinding;
import laurcode.com.retire.main.MainApplication;
import laurcode.com.retire.startup.viewmodel.SplashViewModel;
import laurcode.com.retire.util.SharedPreferencesUtil;

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
