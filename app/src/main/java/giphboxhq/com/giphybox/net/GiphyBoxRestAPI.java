package giphboxhq.com.giphybox.net;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Owner on 2017-09-06.
 */

public interface GiphyBoxRestAPI {

    @GET("/v1/gifs/trending?api_key="+ GiphyBoxApplication.GIPHY_API_KEY)
    Observable<Data> getTrendingGifs();

    @GET("/v1/gifs/trending?api_key="+ GiphyBoxApplication.GIPHY_API_KEY)
    Call<ResponseBody> getTrendingGifsAsJson();


}
