package giphboxhq.com.giphybox.Main;

import android.util.Log;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.net.models.Data;
import rx.Subscriber;

/**
 * Created by Owner on 2017-09-07.
 */

public class MainPresenter implements BasePresenter {
    private static final String TAG = "MainPresenter";

    private GifRepository repo;
    private UserRepository userRepository;
    private ExploreView exploreView;
    private MainView mainView;
    private SavedView savedView;
    private TrendingView trendingView;

    @Inject
    public MainPresenter(UserRepository userRepository, GifRepository repo, ExploreView exploreView, MainView mainView, SavedView savedView, TrendingView trendingView) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.exploreView = exploreView;
        this.mainView = mainView;
        this.savedView = savedView;
        this.trendingView = trendingView;
    }

    @Override
    public void onCreate() {
        mainView.launchLoginActivity();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
        if(userRepository.getAuthenticatedUser() == null){
            mainView.launchLoginActivity();
        }
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
                exploreView.showGifs(data.data);
            }
        });
    }
}
