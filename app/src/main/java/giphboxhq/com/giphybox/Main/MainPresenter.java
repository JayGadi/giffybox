package giphboxhq.com.giphybox.Main;

import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;
import giphboxhq.com.giphybox.net.GifRepository;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;
import giphboxhq.com.giphybox.net.models.SingleGifResponse;
import giphboxhq.com.giphybox.net.models.User;
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
        exploreView.showLoading();
        repo.getTrendingGifs().subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Data data) {
                exploreView.hideLoading();
                exploreView.showGifs(data.data);
            }
        });
    }

    public void loadSearch(String tags){
        String searchTerms = tags.replaceAll(" ", "+");
        exploreView.showLoading();
        repo.searchGifs(searchTerms).subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Data data) {
                exploreView.hideLoading();
                exploreView.showSearch(data.data);
            }
        });
    }

    public void loadGifById(String id){
        repo.getGifById(id).subscribe(new Subscriber<SingleGifResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(SingleGifResponse data) {

            }
        });
    }

    public void loadSavedGifs(){
        ArrayList<Gif> gifs = new ArrayList<>();
        for(Gif gif: userRepository.getAuthenticatedUser().savedGifs){
            gifs.add(gif);
        }
        savedView.loadSavedGifs(gifs);
    }

    public void onGifSelected(Gif gif){
        mainView.launchGifInfoActivity(gif);
    }
}
