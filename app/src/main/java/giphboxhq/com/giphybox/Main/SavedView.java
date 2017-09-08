package giphboxhq.com.giphybox.Main;

import java.util.List;

import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-07.
 */

public interface SavedView {
//    void showLoading();
//    void hideLoading();
    void loadSavedGifs(List<Gif> savedGifs);
}
