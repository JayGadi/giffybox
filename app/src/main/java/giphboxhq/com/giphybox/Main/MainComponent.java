package giphboxhq.com.giphybox.Main;

import dagger.Component;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.GiphyBoxRestAPI;
import giphboxhq.com.giphybox.net.NetComponent;
import giphboxhq.com.giphybox.net.UserComponent;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */

@UiScope
@Component(dependencies = {UserComponent.class}, modules = MainPresenterModule.class)
public interface MainComponent {
    void inject(ExploreFragment exploreFragment);
    void inject(MainActivity mainActivity);
    void inject(SavedFragment savedFragment);
    void inject(TrendingFragment trendingFragment);
}
