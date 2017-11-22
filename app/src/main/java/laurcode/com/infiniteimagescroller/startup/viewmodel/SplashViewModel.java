package laurcode.com.infiniteimagescroller.startup.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.DrawableRes;

import laurcode.com.infiniteimagescroller.BR;

/**
 * The view model for the splash activity
 * <p>
 * Created by lauriescheepers on 2017/11/17.
 */

public class SplashViewModel extends BaseObservable {

    @Bindable
    public @DrawableRes int evolutionImageResId;

    @Bindable
    public String blackFridayUrl;

    @Bindable
    public int getEvolutionImageResId() {
        return evolutionImageResId;
    }

    public void setEvolutionImageResId(@DrawableRes int evolutionImageResId) {
        this.evolutionImageResId = evolutionImageResId;
        notifyPropertyChanged(BR.evolutionImageResId);
    }

    @Bindable
    public String getBlackFridayUrl() {
        return blackFridayUrl;
    }

    public void setBlackFridayUrl(String blackFridayUrl) {
        this.blackFridayUrl = blackFridayUrl;
        notifyPropertyChanged(BR.blackFridayUrl);
    }
}
