package giphboxhq.com.giphybox.net;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


import javax.inject.Inject;

import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;
import giphboxhq.com.giphybox.net.models.SingleGifResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Owner on 2017-09-06.
 */

public class GifRepository {

    private GiphyBoxRestAPI restApi;
    private DbHelper dbHelper;

    @Inject
    public GifRepository(GiphyBoxRestAPI restApi, DbHelper dbHelper) {
        this.restApi = restApi;
        this.dbHelper = dbHelper;
    }

    public Observable<Data> getTrendingGifs(){
        return restApi.getTrendingGifs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Data> searchGifs(String tags){
        return restApi.searchGifs(tags, GiphyBoxApplication.GIPHY_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SingleGifResponse> getGifById(String gifId){
        return restApi.getGifById(gifId, GiphyBoxApplication.GIPHY_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void saveDownvotedGif(Gif gif){
        dbHelper.saveToList(gif, GiphyBoxApplication.DOWNVOTE_GIFS_KEY, Gif.class);
    }

    public void saveUpvotedGif(Gif gif){
        dbHelper.saveToList(gif, GiphyBoxApplication.UPVOTE_GIFS_KEY, Gif.class);
    }

    public List<Gif> getUpvotedGifs(){
        return dbHelper.getListFromDb(Gif.class, GiphyBoxApplication.UPVOTE_GIFS_KEY);
    }

    public List<Gif> getDownvitedGifs(Gif gif){
        return dbHelper.getListFromDb(Gif.class, GiphyBoxApplication.DOWNVOTE_GIFS_KEY);
    }


}
