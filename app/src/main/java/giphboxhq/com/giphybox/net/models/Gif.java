package giphboxhq.com.giphybox.net.models;

import java.util.Map;

/**
 * Created by Owner on 2017-09-06.
 */

public class Gif {
    public String type;
    public String id;
    public String url;

    public Map<String, GiphyImage> images;

    public class GiphyImage {
        public String url;
        public int width;
        public int height;
        public int size;
        public String mp4;
        public int mp4_size;
        public String webp;
        public int webp_size;
    }
}
