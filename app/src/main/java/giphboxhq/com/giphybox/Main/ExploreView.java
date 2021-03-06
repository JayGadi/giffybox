package giphboxhq.com.giphybox.Main;

import java.util.List;

import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-07.
 */

public interface ExploreView {
    void showLoading();
    void hideLoading();
    void showGifs(List<Gif> gifs);
    void showSearch(List<Gif> gifs);
    void recreateActivity();
    void resetScrollListener();
}
