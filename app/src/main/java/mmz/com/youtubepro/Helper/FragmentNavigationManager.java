package mmz.com.youtubepro.Helper;

import android.support.v4.app.FragmentManager;

import mmz.com.youtubepro.Interface.NavigationManager;
import mmz.com.youtubepro.MainActivity;

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;
    public static FragmentNavigationManager getmInstance(MainActivity mainActivity){
        if(mInstance==null){
            mInstance=new FragmentNavigationManager();
        }
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity) {
        this.mainActivity=mainActivity;
        mFragmentManager=mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

    }
}
