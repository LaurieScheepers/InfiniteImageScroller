package laurcode.com.infiniteimagescroller.startup;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

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
import laurcode.com.infiniteimagescroller.rx.ImageDrawableFader;
import laurcode.com.infiniteimagescroller.rx.listeners.ImageDrawableFaderListener;
import laurcode.com.infiniteimagescroller.startup.viewmodel.SplashViewModel;
import laurcode.com.infiniteimagescroller.sync.SyncService;
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
    TextView companyNameTextView;
    @BindView(R.id.app_name)
    TextView appNameTextView;
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


    public static final String BLACK_FRIDAY_URL = "www.superbalist.com/showdown/";

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

        // No image must be visible initially
        if (ViewUtil.isViewVisible(evolutionImage)) {
            evolutionImage.setVisibility(View.GONE);
        }

        splashViewModel.setBlackFridayUrl(BLACK_FRIDAY_URL);

        // Start le nice animations
        startAnimations();

        // Click listeners for black friday section
        blackFridaySectionFront.setOnClickListener(v -> {
            blackFridayFlipView.flipTheView();

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
            blackFridayFlipView.flipTheView();

            blackFridayFlipView.postDelayed(() -> {
                blackFridayDealsHeading.setVisibility(View.INVISIBLE);
                blackFridayDealsLink.setVisibility(View.INVISIBLE);
                blackFridayLinkHint.setVisibility(View.INVISIBLE);
            }, 400);
        });
    }

    private void startAnimations() {
        List<Integer> drawables = new ArrayList<>();

        SyncService.enqueueWork(this, MainApplication.getCurrentPage());

        // The drawables we want to show
        drawables.add(R.drawable.ape_branch_final);
        drawables.add(R.drawable.ape_stick_final);
        drawables.add(R.drawable.man_tool_final);
        drawables.add(R.drawable.man_digital_final);

        // Start the custom, image drawable fader
        new ImageDrawableFader().setDrawables(drawables).start(evolutionImage, new ImageDrawableFaderListener() {
            @Override
            public void onComplete() {
                // Now fade in the company name, app name, black friday hint & image, in that logical order (top to bottom).
                ViewUtil.fadeViewIn(companyNameTextView,
                        () -> {
                            companyNameTextView.animate();

                            ViewUtil.fadeViewIn(appNameTextView,
                                    () -> {
                                        appNameTextView.animate();

                                        ViewUtil.fadeViewIn(blackFridayHint);
                                        ViewUtil.fadeViewIn(blackFridayHintHint);
                                        ViewUtil.fadeViewIn(blackFridayHintImage);
                                    });
                        });
            }

            @Override
            public void onError(Exception e) {
                // Ignore the error and go ahead and show the text underneath the image. Just log it at least.
                Timber.e(e,"There was an error with the image drawable fader, but life must go on. Ignoring.");
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
