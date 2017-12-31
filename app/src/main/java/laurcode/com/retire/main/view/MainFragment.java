package laurcode.com.retire.main.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import laurcode.com.retire.R;
import laurcode.com.retire.base.BaseFragment;
import laurcode.com.retire.databinding.FragmentMainBinding;
import laurcode.com.retire.main.presenter.MainPresenter;
import laurcode.com.retire.main.viewmodel.MainViewModel;
import timber.log.Timber;

/**
 * The main fragment that shows the pictures in a never-ending feed
 * <p>
 * Created by lauriescheepers on 2017/11/23.
 */

public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel, MainPresenter> implements IMainView {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected boolean isMvpvm() {
        return true;    // The main fragment is the only UI element that truly follows the MVPVM architecture
    }

    @Override
    protected void init(View view, Bundle savedInstanceState) {
        // Nothing needed in init()
    }

    @Override
    public void onEndOfListReached() {
        // TODO increment current page and load more photos from API
    }

    @Override
    public void showPacmanProgress() {

    }

    @Override
    public void hidePacmanProgress() {

    }

    @Override
    protected void onBundleReceived(@NonNull Bundle bundle) {
        // No bundle in the fragment
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
    protected void bindToFragment(FragmentMainBinding binding, MainViewModel viewModel, MainPresenter presenter) {
        binding.setViewModel(viewModel);
        binding.setPresenter(presenter);
    }

    @Override
    public void onChildFragmentAttached(BaseFragment fragment) {
        // Ignore - no nested child fragments
    }

    @Override
    public void onDataLoaded() {
        // Good we have the data
        Timber.d("Data loaded from Realm - now we show the things!");
    }
}
