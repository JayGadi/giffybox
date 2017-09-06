package giphboxhq.com.giphybox.net;

import javax.inject.Singleton;

import dagger.Component;
import giphboxhq.com.giphybox.AppModule;
import giphboxhq.com.giphybox.GiphyBoxApplication;

/**
 * Created by Owner on 2017-09-06.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(GiphyBoxApplication giphyBoxApplication);
}
