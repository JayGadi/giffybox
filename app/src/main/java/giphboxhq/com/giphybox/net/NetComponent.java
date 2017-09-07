package giphboxhq.com.giphybox.net;

import javax.inject.Singleton;

import dagger.Component;
import giphboxhq.com.giphybox.AppModule;
import giphboxhq.com.giphybox.ExploreFragment;
import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.MainActivity;

/**
 * Created by Owner on 2017-09-06.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    GiphyBoxRestAPI restApi();
    GifRepository gifRepository();
    void inject(MainActivity mainActivity);
    void inject(ExploreFragment exploreFragment);
}
