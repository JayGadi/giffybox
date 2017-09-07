package giphboxhq.com.giphybox.net;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Owner on 2017-09-06.
 */

public class GifRepository {

    private GiphyBoxRestAPI restApi;


    public GifRepository(GiphyBoxRestAPI restApi) {
        this.restApi = restApi;
    }

    public Observable<Data> getTrendingGifs(){
        return restApi.getTrendingGifs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

//    public Observable<JSONArray> getTrendingGifsAsJson(){
//        return restApi.getTrendingGifsAsJson()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
}
