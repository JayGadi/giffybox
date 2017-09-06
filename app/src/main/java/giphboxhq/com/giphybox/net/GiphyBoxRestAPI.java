package giphboxhq.com.giphybox.net;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Owner on 2017-09-06.
 */

public interface GiphyBoxRestAPI {

    @GET("v1/gifs/trending")
    Observable<String> getTrendingGifs();
}
