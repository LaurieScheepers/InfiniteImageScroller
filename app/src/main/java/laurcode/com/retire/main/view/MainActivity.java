package laurcode.com.retire.main.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import laurcode.com.retire.R;
import laurcode.com.retire.base.BaseActivity;
import laurcode.com.retire.base.BaseFragment;
import laurcode.com.retire.base.BasePresenter;
import laurcode.com.retire.base.BaseViewModel;
import laurcode.com.retire.databinding.ActivityMainBinding;
import laurcode.com.retire.main.presenter.MainPresenter;
import laurcode.com.retire.main.viewmodel.MainViewModel;

/**
 * The Main Activity of this app. This is the activity that contains the infinite scrolling of images retrieved from 500px API
 * <br><br>
 * Created by lauriescheepers on 2017/11/06.
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter> implements IMainView {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // TODO what do we init?
    }

    @Override
    protected boolean isMvpvm() {
        return true;
    }

    @Nullable
    @Override
    protected Class getPresenterClass() {
        return MainPresenter.class;
    }

    @Nullable
    @Override
    protected Class getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void bindToActivity(ActivityMainBinding binding, MainViewModel viewModel, MainPresenter presenter) {
        binding.setViewModel(viewModel);
        binding.setPresenter(presenter);
    }

    @Override
    public void setBundle(Bundle bundle) {
        // No bundle to set - this is a dumb activity
    }

    @Override
    public void onDataLoaded() {
        // Ignore - no data to be loaded here
    }

    @Override
    public void onFragmentAttached(BaseFragment fragment) {
        // Nothing needed here as yet
    }

    @Override
    public void onFragmentDetached(BaseFragment fragment) {
        // Nothing needed here as yet
    }

    @Override
    public void onFragmentResumed(BaseFragment fragment) {
        // Nothing needed here as yet
    }

    @Override
    public void onChildFragmentAttached(BaseFragment childFragment) {
        // Nothing needed here as yet
    }
}
