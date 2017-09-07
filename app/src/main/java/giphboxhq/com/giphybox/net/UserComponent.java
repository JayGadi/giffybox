package giphboxhq.com.giphybox.net;

import dagger.Component;
import giphboxhq.com.giphybox.Login.LoginActivity;
import giphboxhq.com.giphybox.Main.MainActivity;
import giphboxhq.com.giphybox.scopes.UserScope;

/**
 * Created by Owner on 2017-09-07.
 */

@UserScope
@Component(modules = UserModule.class, dependencies = NetModule.class)
public interface UserComponent {
    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
}
