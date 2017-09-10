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
    private ControversialView controversialView;

    @Inject
    public MainPresenter(UserRepository userRepository, GifRepository repo, ExploreView exploreView, MainView mainView, SavedView savedView, TrendingView trendingView, ControversialView controversialView) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.exploreView = exploreView;
        this.mainView = mainView;
        this.savedView = savedView;
        this.trendingView = trendingView;
        this.controversialView = controversialView;
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

    public void loadFirstTrendingGifsPage(){
        exploreView.showLoading();
        exploreView.resetScrollListener();
        repo.getTrendingGifsWithOffset(0, 10).subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e );
            }

            @Override
            public void onNext(Data data) {
                Log.d(TAG, "onNext: Loading First Trending Gifs Page");
                exploreView.hideLoading();
                exploreView.showGifs(data.data);
            }
        });
    }

    public void loadNextTrendingGifsPage(int offset){
        exploreView.showLoading();
        repo.getTrendingGifsWithOffset(offset, 10).subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(Data data) {
                Log.d(TAG, "onNext: Loading Next Trending Gifs Page");
                exploreView.hideLoading();
                exploreView.showGifs(data.data);
            }
        });
    }

    public void loadFirstSearchPage(String tags){
        String searchTerms = tags.replaceAll(" ", "+");
        exploreView.resetScrollListener();
        exploreView.showLoading();
        repo.searchGifsWithOffset(searchTerms, 0, 10).subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(Data data) {
                Log.d(TAG, "onNext: Loading First Search Page" );
                exploreView.hideLoading();
                exploreView.showGifs(data.data);
            }
        });
    }

    public void loadNextSearchPage(String tags, int offset){
        String searchTerms = tags.replaceAll(" ", "+");
        exploreView.showLoading();
        repo.searchGifsWithOffset(searchTerms, offset, 10).subscribe(new Subscriber<Data>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onNext(Data data) {
                Log.d(TAG, "onNext: Loading Next Search Page" );
                exploreView.hideLoading();
                exploreView.showGifs(data.data);
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

    public void getUpvotedGifs(){
        trendingView.loadRatedGifs(repo.getUpvotedGifs());
    }

    public void getDownvotedGifs(){
        controversialView.loadRatedGifs(repo.getDownvotedGifs());
    }
    public void onGifSelected(Gif gif){
        mainView.launchGifInfoActivity(gif);
    }

    public void logout(){
        userRepository.removeAuthenticatedUser();
        exploreView.recreateActivity();
    }

    public boolean isAuthenticated(){
        return userRepository.getAuthenticatedUser() != null;
    }
}
