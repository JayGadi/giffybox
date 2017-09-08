package giphboxhq.com.giphybox.net.models;

/**
 * Created by Owner on 2017-09-08.
 */

public class SingleGifResponse {
    public Gif data;
    public Meta meta;

    private static class Meta {
        public int status;
        public String msg;
        public String response_id;
    }
}
