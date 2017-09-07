package giphboxhq.com.giphybox.Main;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;
import giphboxhq.com.giphybox.net.GifRepository;

/**
 * Created by Owner on 2017-09-07.
 */

public class MainPresenter implements BasePresenter {

    private GifRepository repo;
    private ExploreView exploreView;
    private GifViewAdapter gifViewAdapter;
    private MainView mainView;
    private SavedView savedView;
    private TrendingView trendingView;

    @Inject
    public MainPresenter(GifRepository repo, ExploreView exploreView, GifViewAdapter gifViewAdapter, MainView mainView, SavedView savedView, TrendingView trendingView) {
        this.repo = repo;
        this.exploreView = exploreView;
        this.gifViewAdapter = gifViewAdapter;
        this.mainView = mainView;
        this.savedView = savedView;
        this.trendingView = trendingView;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
