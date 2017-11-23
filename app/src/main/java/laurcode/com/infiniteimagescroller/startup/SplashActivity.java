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
import laurcode.com.infiniteimagescroller.startup.viewmodel.SplashViewModel;
import laurcode.com.infiniteimagescroller.sync.SyncService;
import laurcode.com.infiniteimagescroller.util.CrashUtil;
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
    @BindView(R.id.ape_to_digital_man_image)
    ImageView evolutionImage;
    @BindView(R.id.company_name)
    TyperTextView companyNameTextView;
    @BindView(R.id.app_name)
    TyperTextView appNameTextView;
    @BindView(R.id.black_friday_section)
    EasyFlipView blackFridayFlipView;
    @BindView(R.id.view_black_friday_section_front)
    LinearLayout blackFridaySectionFront;
    @BindView(R.id.view_black_friday_section_back)
    LinearLayout blackFridaySectionBack;
    @BindView(R.id.black_friday_hint)
    TextView blackFridayHint;
    @BindView(R.id.black_friday_hint_hint)
    TextView blackFridayHintHint;
    @BindView(R.id.black_friday_hint_image)
    ImageView blackFridayHintImage;
    @BindView(R.id.black_friday_deals_heading)
    TextView blackFridayDealsHeading;
    @BindView(R.id.black_friday_deals_link)
    TextView blackFridayDealsLink;
    @BindView(R.id.black_friday_link_hint)
    TextView blackFridayLinkHint;
    @BindView(R.id.pacman_loading_indicator)
    AVLoadingIndicatorView pacManLoadingIndicator;
    @BindView(R.id.syncing_data_text_view)
    TextView syncingDataTextView;
    @BindView(R.id.indicating_arrow)
    ImageView continueIndicatingArrow;
    @BindView(R.id.click_me_to_continue)
    TextView continueTextView;

    public static final String BLACK_FRIDAY_URL = "https://superbalist.com/showdown";

    // A flag indicating that the main ape-to-man evolution image is finished
    private boolean mainImageAnimationDone;

    // A flag indicating that the service has finished with the API call
    private boolean serviceFinishedWork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        splashViewModel.setBlackFridayUrl(BLACK_FRIDAY_URL);

        // Start le nice animations
        startAnimations();

        // Click listeners for black friday section
        blackFridaySectionFront.setOnClickListener(v -> {
            blackFridayFlipView.flipTheView();  // Flippit

            blackFridayDealsHeading.setVisibility(View.VISIBLE);
            blackFridayDealsLink.setVisibility(View.VISIBLE);
            blackFridayLinkHint.setVisibility(View.VISIBLE);

            blackFridayDealsLink.setOnClickListener(v1 -> {
                // Launch the browser
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(BLACK_FRIDAY_URL));
                startActivity(i);
            });
        });

        blackFridaySectionBack.setOnClickListener(v -> {
            blackFridayFlipView.flipTheView();  // Flippit

            blackFridayFlipView.postDelayed(() -> {
                blackFridayDealsHeading.setVisibility(View.INVISIBLE);
                blackFridayDealsLink.setVisibility(View.INVISIBLE);
                blackFridayLinkHint.setVisibility(View.INVISIBLE);
            }, 400);    // Add a bit of delay here
        });

        // These two text views must be empty by default
        companyNameTextView.setText("");
        appNameTextView.setText("");

        evolutionImage.setOnClickListener(v -> SnackbarWrapper.show(rootContainer, R.string.black_friday_hint, Snackbar.LENGTH_SHORT));
    }

    private void startAnimations() {
        List<Integer> drawables = new ArrayList<>();

        showProgress();

        // Sanity check to ensure the image is not visible
        if (ViewUtil.isViewVisible(evolutionImage)) {
            evolutionImage.setVisibility(View.GONE);
        }

        // The drawables we want to show
        drawables.add(R.drawable.ape_branch_final);
        drawables.add(R.drawable.ape_stick_final);
        drawables.add(R.drawable.man_tool_final);
        drawables.add(R.drawable.man_digital_final);

        // Start the custom image drawable fader
        new ImageDrawableFader().setDrawables(drawables).start(evolutionImage, new ImageDrawableFaderListener() {
            @Override
            public void onComplete() {
                ViewUtil.fadeViewIn(companyNameTextView,
                    // Now fade in all the things in a logical order (in a top to bottom way)
                    () -> {
                        companyNameTextView.setAnimationListener(hTextView -> ViewUtil.fadeViewIn(appNameTextView,
                            () -> {
                                appNameTextView.setAnimationListener(
                                    hTextView1 -> ViewUtil.fadeViewIn(blackFridayHint,
                                        () -> ViewUtil.fadeViewIn(blackFridayHintHint,
                                            () -> ViewUtil.fadeViewIn(blackFridayHintImage,
                                                () -> {
                                                    mainImageAnimationDone = true;

                                                    // If we get here and the service is done, show the arrow indicating where to click
                                                    if (serviceFinishedWork) {
                                                        showIndicatingArrowHint();
                                                    }
                                                }))));

                                // Start type-writer for app name
                                appNameTextView.animateText(getString(R.string.app_name));
                            }));

                        // Start type-writer for company name (LaurCode)
                        companyNameTextView.animateText(getString(R.string.company_name));
                    });
            }

            @Override
            public void onError(Exception e) {
                // Ignore the error and go ahead and show the text underneath the image. Just log it at least.
                CrashUtil.logCrash("There was an error with the image drawable fader, but life must go on. Ignoring.", e);
                onComplete();
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PhotosRetrievedSuccessEvent event) {
        Timber.d("We have a successful response for photos retrieved!");

        serviceFinishedWork = true;

        // Success! Hide the progress indicator
        hideProgress();

        // Show the user where to press to continue
        showIndicatingArrowHint();
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PhotosRetrievedFailedEvent event) {

        serviceFinishedWork = false;

        // It failed - hide the progress indicator
        hideProgress();

        // And show a snackity-snack snackbar
        showErrorSnackbar();
    }

    private void showProgress() {
        ViewUtil.fadeViewIn(pacManLoadingIndicator, () -> {
            pacManLoadingIndicator.show();
            ViewUtil.fadeViewIn(syncingDataTextView);
        });
    }

    private void hideProgress() {
        ViewUtil.fadeViewOut(pacManLoadingIndicator, () -> ViewUtil.fadeViewOut(syncingDataTextView));
    }

    private void showIndicatingArrowHint() {
        // The arrow should only be shown once the main image animation is done (I want people to look at it, see what it means)
        if (mainImageAnimationDone) {
            ViewUtil.fadeViewIn(continueIndicatingArrow, () -> ViewUtil.fadeViewIn(continueTextView));
        }
    }

    private void hideIndicatingArrowHint() {
        ViewUtil.fadeViewOut(continueTextView, () -> ViewUtil.fadeViewOut(continueIndicatingArrow));
    }

    private void showErrorSnackbar() {
        SnackbarWrapper.show(rootContainer, R.string.error_loading_photos, Snackbar.LENGTH_LONG, R.string.try_again, v -> {

            // If try again is pressed show progress again
            showProgress();

            hideIndicatingArrowHint();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Register event bus if not registered already
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        serviceFinishedWork = false;

        // Start the service work as soon as possible!
        SyncService.enqueueWork(this, MainApplication.getCurrentPage());
    }

    @Override
    protected void onDestroy() {

        // Be sure to unregister once this activity goes away completely
        // NOTE: we still want to receive events if the activity is paused or stopped
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }
}
