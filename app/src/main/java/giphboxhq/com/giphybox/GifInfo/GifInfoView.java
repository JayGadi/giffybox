package giphboxhq.com.giphybox.GifInfo;

/**
 * Created by Owner on 2017-09-07.
 */

public interface GifInfoView {

    void showLoading();
    void hideLoading();
    void loadGif(String url);
    void showToast(String message);
}
