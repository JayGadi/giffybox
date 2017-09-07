package giphboxhq.com.giphybox.GifInfo;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;

/**
 * Created by Owner on 2017-09-07.
 */

public class GifInfoPresenter implements BasePresenter {

    private GifInfoView view;

    @Inject
    public GifInfoPresenter(GifInfoView view) {
        this.view = view;
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

    public void loadGifFromIntent(String url){
        view.showLoading();
        view.loadGif(url);

    }

    public void onDownvoteGifSelected(){
        view.showToast("Gif downvoted");
    }

    public void onUpvoteGifSelected(){
        view.showToast("Gif upvoted");
    }

    public void onSaveGifSelected(){
        view.showToast("Gif added to favourites");
    }
}
