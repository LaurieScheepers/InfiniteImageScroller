package laurcode.com.infiniteimagescroller.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;

/**
 * Base interface for a class that wants to act as a View in the MVP pattern. It contains some general
 * methods that can be applied to any activity or fragment.
 *
 * <p/>
 * Created by laurie on 2017/09/13.
 */

public interface BaseView {

    Context getContext();

    void setBundle(Bundle bundle);

    void onDataLoaded();

    void showError(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    void onChildFragmentAttached(BaseFragment childFragment);
}
