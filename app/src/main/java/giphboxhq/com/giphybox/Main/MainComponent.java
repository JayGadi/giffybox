package giphboxhq.com.giphybox.Main;

import dagger.Component;
import giphboxhq.com.giphybox.net.NetComponent;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */

@UiScope
@Component(dependencies = {NetComponent.class}, modules = MainPresenterModule.class)
public interface MainComponent {
    void inject(ExploreFragment exploreFragment);
    void inject(GifViewAdapter gifViewAdapter);
    void inject(MainActivity mainActivity);
    void inject(SavedFragment savedFragment);
    void inject(TrendingFragment trendingFragment);
}
