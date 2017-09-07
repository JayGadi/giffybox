package giphboxhq.com.giphybox.Main;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.models.Data;
import rx.Subscriber;

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

    public void loadTrendingGifs(){
        repo.getTrendingGifs().subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Data data) {

            }
        });
    }
}
