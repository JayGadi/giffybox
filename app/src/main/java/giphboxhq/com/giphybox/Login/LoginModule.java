package giphboxhq.com.giphybox.Login;

import dagger.Module;
import dagger.Provides;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */

@Module
public class LoginModule{

    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @UiScope
    public LoginView providesView() {
        return view;
    }
}
