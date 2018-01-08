package laurcode.com.retire.startup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;
import com.hanks.htextview.typer.TyperTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import laurcode.com.retire.R;
import laurcode.com.retire.databinding.ActivitySplashBinding;
import laurcode.com.retire.main.MainApplication;
import laurcode.com.retire.main.view.MainActivity;
import laurcode.com.retire.startup.viewmodel.SplashViewModel;
import laurcode.com.retire.util.SharedPreferencesUtil;
import laurcode.com.retire.util.ViewUtil;
import laurcode.com.retire.util.callbacks.FadeInAnimationCompletedCallback;

/**
 * The Splash Activity is shown each time the app starts up. It handles the starting of the sync service, loading the initial freshest photos.
 * <p>
 * Created by lauriescheepers on 2017/11/05.
 */

@SuppressWarnings("FieldCanBeLocal")
public class SplashActivity extends AppCompatActivity {

    // TODO fix memory leak here, something weird holds on to a context. Figure out what it is.

    private SplashViewModel splashViewModel;

    @BindView(R.id.root_container)
    RelativeLayout rootContainer;
    @BindView(R.id.retire_heading)
    TextView retireHeading;
    @BindView(R.id.retire_caption)
    TyperTextView retireCaption;
    @BindView(R.id.continue_button)
    Button continueButton;

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

        ViewUtil.fadeViewIn(retireHeading, () -> {

            // Caption starts off empty
            retireCaption.setText("");

            retireCaption.setAnimationListener(hTextView -> ViewUtil.fadeViewIn(continueButton));

            retireCaption.animateText(getText(R.string.splash_caption));
        });

        continueButton.setOnClickListener(view -> goToMainActivity());
    }

    private void goToMainActivity() {
        SharedPreferencesUtil.setUserHasSeenSplash(this, true);

        startActivity(new Intent(this, MainActivity.class));

        // Finish splash activity so that it doesn't appear on the back stack
        finish();
    }
}
