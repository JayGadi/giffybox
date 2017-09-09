package giphboxhq.com.giphybox.net;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import giphboxhq.com.giphybox.AppModule;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.Main.ExploreFragment;
import giphboxhq.com.giphybox.Main.MainActivity;

/**
 * Created by Owner on 2017-09-06.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    GiphyBoxApplication application();
    Context context();
    GiphyBoxRestAPI restApi();
    GifRepository gifRepository();
    DbHelper dbHelper();
}
