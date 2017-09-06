package giphboxhq.com.giphybox;

import android.app.Application;

import giphboxhq.com.giphybox.net.NetComponent;
import giphboxhq.com.giphybox.net.NetModule;

/**
 * Created by Owner on 2017-09-06.
 */

public class GiphyBoxApplication extends Application {
    private static final String TAG = "GiphyBoxApplication";
    public static final String BASE_URL = "api.giphy.com";

    private NetComponent netComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        netComponent = DaggerNetComponent.builder()
//                .appModule(new AppModule(this))
//                .netModule(new NetModule())
//                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
