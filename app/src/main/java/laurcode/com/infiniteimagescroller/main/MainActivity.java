package laurcode.com.infiniteimagescroller.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import laurcode.com.infiniteimagescroller.R;
import laurcode.com.infiniteimagescroller.databinding.ActivityMainBinding;
import laurcode.com.infiniteimagescroller.main.viewmodel.MainViewModel;

/**
 * The Main Activity of this app. This is the activity that contains the infinite scrolling of images retrieved from 500px API
 * <br><br>
 * Created by lauriescheepers on 2017/11/06.
 */
public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mainViewModel = new MainViewModel();

        MainApplication.registerViewModel(MainActivity.class, mainViewModel);

        binding.setViewModel(mainViewModel);

        ButterKnife.bind(this);
    }
}
