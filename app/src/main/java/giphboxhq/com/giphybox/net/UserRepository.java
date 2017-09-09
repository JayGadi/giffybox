package giphboxhq.com.giphybox.net;

import javax.inject.Inject;

import giphboxhq.com.giphybox.net.models.User;

/**
 * Created by Owner on 2017-09-07.
 */

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static final String AUTH_USER_KEY = "auth_user_key";
    private DbHelper dbHelper;

    @Inject
    public UserRepository(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public User saveUser(User user){
        return dbHelper.saveToDb(user, user.username);
    }

    public void setAuthenticatedUser(User user){
        dbHelper.saveToDb(user, AUTH_USER_KEY);
    }

    public void removeAuthenticatedUser(){
        dbHelper.removeFromDb(AUTH_USER_KEY);
    }

    public User getAuthenticatedUser(){
        return dbHelper.getFromDb(User.class, AUTH_USER_KEY);
    }

    public User loadUser(User user){
        if(dbHelper.getFromDb(User.class, user.username) == null){
            return saveUser(user);
        }else{
            return dbHelper.getFromDb(User.class, user.username);
        }
    }
}