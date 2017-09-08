package giphboxhq.com.giphybox.Main;

import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-07.
 */

public interface MainView {
    void launchLoginActivity();
    void launchGifInfoActivity(Gif gif);
}
