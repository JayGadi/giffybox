package giphboxhq.com.giphybox.Main;

import java.util.List;

import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-07.
 */

public interface TrendingView {
    void loadRatedGifs(List<Gif> savedGifs);
}
