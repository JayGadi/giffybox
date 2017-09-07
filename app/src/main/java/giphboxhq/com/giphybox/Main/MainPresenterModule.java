package giphboxhq.com.giphybox.Main;

import dagger.Module;
import dagger.Provides;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */

@Module
public class MainPresenterModule {

    private ExploreView exploreView;
    private MainView mainView;
    private SavedView savedView;
    private TrendingView trendingView;

    public MainPresenterModule(ExploreView exploreView, MainView mainView, SavedView savedView, TrendingView trendingView) {
        this.exploreView = exploreView;
        this.mainView = mainView;
        this.savedView = savedView;
        this.trendingView = trendingView;
    }

    @UiScope
    @Provides
    public ExploreView providesExploreView() {
        return exploreView;
    }

    @UiScope
    @Provides
    public MainView providesMainView() {
        return mainView;
    }

    @UiScope
    @Provides
    public SavedView providesSavedView() {
        return savedView;
    }

    @UiScope
    @Provides
    public TrendingView providesTrendingView() {
        return trendingView;
    }
}
