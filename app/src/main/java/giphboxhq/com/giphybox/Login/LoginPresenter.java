package giphboxhq.com.giphybox.Login;

import android.os.Handler;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import giphboxhq.com.giphybox.BasePresenter;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.net.models.User;


/**
 * Created by Owner on 2017-09-07.
 */

public class LoginPresenter implements BasePresenter{
    private static final String TAG = "LoginPresenter";
    private LoginView loginView;
    private UserRepository userRepository;

    @Inject
    public LoginPresenter(LoginView loginView, UserRepository userRepository) {
        this.loginView = loginView;
        this.userRepository = userRepository;
    }

    @Override
    public void onCreate() {
        checkAuthentication();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void onLoginSelected(String username){
        User user = new User();
        user.username = username;

        user = userRepository.loadUser(user);
        userRepository.setAuthenticatedUser(user);
        loginView.goToMainActivity();

    }

    private void checkAuthentication(){
        if(userRepository.getAuthenticatedUser() == null){
            loginView.showForm();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loginView.goToMainActivity();
                }
            },1000);
        }
    }
}
