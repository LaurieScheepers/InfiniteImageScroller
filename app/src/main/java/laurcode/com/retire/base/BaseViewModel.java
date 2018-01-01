package laurcode.com.retire.base;

import android.databinding.BaseObservable;

/**
 * The base view model class. All other view model classes must inherit from this one.
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public abstract class BaseViewModel<P extends BasePresenter, V extends BaseView> extends BaseObservable {
    P presenter;
    V view;

    public BaseViewModel() {
    }

    public BaseViewModel(P presenter) {
        this.presenter = presenter;
    }

    public P getPresenter() {
        return presenter;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    public void bindView(V view) {
        this.view = view;
    }
}
