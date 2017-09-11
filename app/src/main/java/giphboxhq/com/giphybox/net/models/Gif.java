package giphboxhq.com.giphybox.net.models;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 2017-09-06.
 */

public class Gif implements Comparable<Gif>{
    public String type;
    public String id;
    public String url;
    public List<String> tags;
    public int ratingCount;

    public Map<String, GiphyImage> images;

    public static class GiphyImage {
        public String url;
        public int width;
        public int height;
        public int size;
        public String mp4;
        public int mp4_size;
        public String webp;
        public int webp_size;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Gif gif = (Gif) obj;
        return id.equals(gif.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int compareTo(@NonNull Gif o) {
        return this.ratingCount > o.ratingCount ? -1: 1;
    }
}
