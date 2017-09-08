package giphboxhq.com.giphybox.GifInfo;

import android.graphics.Color;

import giphboxhq.com.giphybox.net.models.Gif;

/**
 * Created by Owner on 2017-09-07.
 */

public interface GifInfoView {

    void showLoading();
    void hideLoading();
    void loadGif(Gif gif);
    void showToast(String message);
    void setDownvoteButtonSelected();
    void setDownvoteButtonUnSelected();
    void setUpvoteButtonSelected();
    void setUpvoteButtonUnselected();
    void setSaveButtonSelected();
    void setSaveButtonUnselected();
}
