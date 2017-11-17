package laurcode.com.infiniteimagescroller.startup;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import laurcode.com.infiniteimagescroller.R;
import laurcode.com.infiniteimagescroller.databinding.ActivitySplashBinding;
import laurcode.com.infiniteimagescroller.events.PhotosRetrievedFailedEvent;
import laurcode.com.infiniteimagescroller.events.PhotosRetrievedSuccessEvent;
import laurcode.com.infiniteimagescroller.main.MainApplication;
import laurcode.com.infiniteimagescroller.rx.ImageDrawableFadeEmitter;
import laurcode.com.infiniteimagescroller.rx.listeners.ImageDrawableFadeEmitterListener;
import laurcode.com.infiniteimagescroller.startup.viewmodel.SplashViewModel;
import laurcode.com.infiniteimagescroller.util.ViewUtil;
import timber.log.Timber;

/**
 * The Splash Activity is shown each time the app starts up. It handles the starting of the sync service, loading the initial freshest photos.
 * <p>
 * Created by lauriescheepers on 2017/11/05.
 */

public class SplashActivity extends AppCompatActivity {

    private SplashViewModel splashViewModel;

    @BindView(R.id.root_container)
    RelativeLayout rootContainer;
    @BindView(R.id.ape_to_digital_man_image)
    ImageView evolutionImage;
    @BindView(R.id.company_name)
    TextView companyNameTextView;
    @BindView(R.id.app_name)
    TextView appNameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        // Create view model for the splash
        splashViewModel = new SplashViewModel();

        // Cache the view model
        MainApplication.registerViewModel(SplashActivity.class, splashViewModel);

        // Set the view model on the generated view binding class
        binding.setViewModel(splashViewModel);

        // Bind Butterknife for easy access to the views in this activity
        ButterKnife.bind(this);

        // First image is the ape with a branch
        splashViewModel.setEvolutionImageResId(R.drawable.ape_branch_final);

        // Start le nice animations
        startAnimations();
    }

    private void startAnimations() {
        List<Integer> drawables = new ArrayList<>();

        drawables.add(R.drawable.ape_branch_final);
        drawables.add(R.drawable.ape_stick_final);
        drawables.add(R.drawable.man_tool_final);
        drawables.add(R.drawable.man_digital_final);

        ImageDrawableFadeEmitter.setDrawables(drawables);

        // Start the custom, amazing, image drawable fade emitter
        ImageDrawableFadeEmitter.start(evolutionImage, R.drawable.ape_branch_final, new ImageDrawableFadeEmitterListener() {
            @Override
            public void onComplete() {
                // Now fade in the company name and app name
                ViewUtil.fadeViewIn(companyNameTextView, () -> ViewUtil.fadeViewIn(appNameTextView));
            }

            @Override
            public void onError() {
                // Ignore the error and go ahead and show the text underneath the image. Just log it at least.
                Timber.e("There was an error with the image fade emitter, but life must go on. Ignoring.");
                onComplete();
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PhotosRetrievedSuccessEvent event) {

    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PhotosRetrievedFailedEvent event) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
}
