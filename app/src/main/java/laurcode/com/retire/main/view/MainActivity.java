package laurcode.com.retire.main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import laurcode.com.retire.R;
import laurcode.com.retire.base.BaseActivity;
import laurcode.com.retire.base.BaseFragment;
import laurcode.com.retire.databinding.ActivityMainBinding;
import laurcode.com.retire.main.presenter.MainPresenter;
import laurcode.com.retire.main.viewmodel.MainViewModel;

/**
 * Insert description of class here
 * <br><br>
 * Created by lauriescheepers on 2018/01/01.
 */

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter> implements IMainView{
    @Override
    public void setBundle(Bundle bundle) {
        // No bundle on the activity
    }

    @Override
    public void onChildFragmentAttached(BaseFragment childFragment) {
        // No child fragments - this activity will only have one fragment
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // TODO check what to initialize
    }

    @Override
    protected boolean isMvpvm() {
        return true;
    }

    @Nullable
    @Override
    protected Class<MainPresenter> getPresenterClass() {
        return MainPresenter.class;
    }

    @Nullable
    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected void bindToActivity(ActivityMainBinding binding, MainViewModel viewModel, MainPresenter presenter) {
        binding.setPresenter(presenter);
        binding.setViewModel(viewModel);
    }

    @Override
    public void onDataLoaded() {
        // TODO what to do here, what DB data do we want
    }

    @Override
    public void onFragmentAttached(BaseFragment fragment) {
        // TODO what to do once a fragment is attached
    }

    @Override
    public void onFragmentDetached(BaseFragment fragment) {
        // TODO what to do once a fragment is attached
    }

    @Override
    public void onFragmentResumed(BaseFragment fragment) {
        // TODO what to do once a fragment is resumed
    }
}
