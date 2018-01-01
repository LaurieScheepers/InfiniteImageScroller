package laurcode.com.retire.startup;

import android.app.Activity;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import laurcode.com.retire.R;
import laurcode.com.retire.base.BaseActivity;
import laurcode.com.retire.base.BaseFragment;
import laurcode.com.retire.base.BasePresenter;
import laurcode.com.retire.base.BaseViewModel;
import timber.log.Timber;

/**
 * Insert description of class here
 * <br><br>
 * Created by lauriescheepers on 2018/01/01.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.continue_button)
    Button continueButton;

    @Override
    public void setBundle(Bundle bundle) {
        // Nothing needed
    }

    @Override
    public void onChildFragmentAttached(BaseFragment childFragment) {
        // Nothing needed
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        continueButton.setOnClickListener(view -> Timber.d("Continue clicked!"));
    }

    @Override
    protected boolean isMvpvm() {
        return false;
    }

    @Nullable
    @Override
    protected Class getPresenterClass() {
        return null;
    }

    @Nullable
    @Override
    protected Class getViewModelClass() {
        return null;
    }

    @Override
    protected void bindToActivity(ViewDataBinding binding, BaseViewModel viewModel, BasePresenter presenter) {
        // Nothing needed
    }

    @Override
    public void onDataLoaded() {
        // Nothing needed
    }

    @Override
    public void onFragmentAttached(BaseFragment fragment) {
        // Nothing needed
    }

    @Override
    public void onFragmentDetached(BaseFragment fragment) {
        // Nothing needed
    }

    @Override
    public void onFragmentResumed(BaseFragment fragment) {
        // Nothing needed
    }
}
