package giphboxhq.com.giphybox.net.models;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Owner on 2017-09-06.
 */

public class User {
    public String id;
    public String username;

    public HashSet<Gif> savedGifs;
    public HashSet<Gif> downvotedGifs;
    public HashSet<Gif> upvotedGifs;

    public User() {
        savedGifs = new HashSet<>();
        downvotedGifs = new HashSet<>();
        upvotedGifs = new HashSet<>();
    }
}
