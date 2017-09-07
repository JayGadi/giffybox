package giphboxhq.com.giphybox.net.models;

import java.util.List;

/**
 * Created by Owner on 2017-09-06.
 */

public class Data {
    public List<Gif> data;
    public Meta meta;
    public Pagination pagination;

    class Meta {
        public int status;
        public String msg;
        public String response_id;
    }

    class Pagination {
        public int total_count;
        public int count;
        public int offset;
    }
}
