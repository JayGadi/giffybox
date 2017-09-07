package giphboxhq.com.giphybox.net.models;

import java.util.ArrayList;

/**
 * Created by Owner on 2017-09-06.
 */

public class User {
    public String id;
    public String username;
    public boolean isAuthenticated;

    public ArrayList<Gif> savedGifs;
    public ArrayList<Gif> ratedGifs;
}
