package giphboxhq.com.giphybox;

import android.app.Application;

import giphboxhq.com.giphybox.net.DaggerNetComponent;
import giphboxhq.com.giphybox.net.DaggerUserComponent;
import giphboxhq.com.giphybox.net.NetComponent;
import giphboxhq.com.giphybox.net.NetModule;
import giphboxhq.com.giphybox.net.UserComponent;
import giphboxhq.com.giphybox.net.UserModule;

/**
 * Created by Owner on 2017-09-06.
 */

public class GiphyBoxApplication extends Application {
    private static final String TAG = "GiphyBoxApplication";
    public static final String BASE_URL = "http://api.giphy.com";
    public static final String GIPHY_API_KEY = "f5a33bce64694d2e8642880bdb0b1169";

    public static final int LOGIN_REQUEST_CODE = 1;

    private NetComponent netComponent;
    private UserComponent userComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

        userComponent = DaggerUserComponent.builder()
                .netComponent(getNetComponent())
                .userModule(new UserModule())
                .build();
    }

    public UserComponent getUserComponent() {
        return userComponent;
    }
    public NetComponent getNetComponent() {
        return netComponent;
    }
}
