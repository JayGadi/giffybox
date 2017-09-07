package giphboxhq.com.giphybox.net;

import giphboxhq.com.giphybox.net.models.User;

/**
 * Created by Owner on 2017-09-07.
 */

public class UserRepository {

    DbHelper dbHelper;

    public UserRepository(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private User saveUser(User user){
        return dbHelper.saveToDb(user, user.username);
    }

    public boolean isAuthenticated(User user){
        return user.isAuthenticated;
    }

    public void setAuthentication(User user, boolean auth){
        user.isAuthenticated = auth;
    }
    public User loadUser(User user){
        if(dbHelper.getFromDb(User.class, user.username) == null){
            return saveUser(user);
        }else{
            return dbHelper.getFromDb(User.class, user.username);
        }
    }
}