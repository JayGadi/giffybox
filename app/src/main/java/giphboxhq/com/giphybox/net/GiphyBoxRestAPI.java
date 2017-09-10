package giphboxhq.com.giphybox.net;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import giphboxhq.com.giphybox.GiphyBoxApplication;
import giphboxhq.com.giphybox.net.models.Data;
import giphboxhq.com.giphybox.net.models.Gif;

import giphboxhq.com.giphybox.net.models.SingleGifResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Owner on 2017-09-06.
 */

public interface GiphyBoxRestAPI {

    @GET("/v1/gifs/trending?api_key="+ GiphyBoxApplication.GIPHY_API_KEY)
    Observable<Data> getTrendingGifs();

    @GET("/v1/gifs/search")
    Observable<Data> searchGifs(
            @Query("q") String tags,
            @Query("api_key") String apiKey
    );

    @GET("/v1/gifs/{gif_id}")
    Observable<SingleGifResponse> getGifById(
            @Path("gif_id") String gifId,
            @Query("api_key") String apiKey
    );

    @GET("/v1/gifs/trending")
    Observable<Data> getTrendingGifsWithOffset(
            @Query("api_key") String apiKey,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("v1/gifs/search")
    Observable<Data> searchGifsWithOffset(
            @Query("api_key") String apiKey,
            @Query("q") String tags,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

}
