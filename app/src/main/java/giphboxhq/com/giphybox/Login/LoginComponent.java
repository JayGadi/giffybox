package giphboxhq.com.giphybox.Login;

import dagger.Component;
import dagger.Module;
import giphboxhq.com.giphybox.net.UserComponent;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */


@UiScope
@Component(modules = LoginModule.class, dependencies = UserComponent.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
