package laurcode.com.infiniteimagescroller.main.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import laurcode.com.infiniteimagescroller.R;
import laurcode.com.infiniteimagescroller.base.BaseActivity;
import laurcode.com.infiniteimagescroller.base.BaseFragment;
import laurcode.com.infiniteimagescroller.base.BasePresenter;
import laurcode.com.infiniteimagescroller.base.BaseViewModel;

/**
 * The Main Activity of this app. This is the activity that contains the infinite scrolling of images retrieved from 500px API
 * <br><br>
 * Created by lauriescheepers on 2017/11/06.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        // Add the main fragment
//        MainFragment mainFragment = new MainFragment();
//
//        addFragment(mainFragment);
    }

    @Override
    protected boolean isMvpvm() {
        return false;
    }

    @Nullable
    @Override
    protected Class getPresenterClass() {
        // MainActivity doesn't have a presenter. It only handles the toolbar and the fragment.
        // The main fragment however, does have one
        return null;
    }

    @Nullable
    @Override
    protected Class getViewModelClass() {
        // MainActivity doesn't have a view model. It only handles the toolbar and the fragment.
        // The main fragment however, does have one.
        return null;
    }

    @Override
    protected void bindToActivity(ViewDataBinding binding, BaseViewModel viewModel, BasePresenter presenter) {
        // Main activity isn't MVPVM, so ignore
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
        // Nothing needed here
    }

    @Override
    public void onFragmentDetached(BaseFragment fragment) {
        // Nothing needed here
    }

    @Override
    public void onFragmentResumed(BaseFragment fragment) {
        // Nothing needed here
    }

    @Override
    public void onChildFragmentAttached(BaseFragment childFragment) {
        // TODO do we want to do something here?
    }
}
