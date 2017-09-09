package giphboxhq.com.giphybox.Main;

import java.util.List;

import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-08.
 */

public interface ControversialView {
    void loadRatedGifs(List<Gif> savedGifs);
}
