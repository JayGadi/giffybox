package giphboxhq.com.giphybox.GifInfo;

import dagger.Component;
import giphboxhq.com.giphybox.net.NetComponent;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */

@UiScope
@Component(dependencies = NetComponent.class, modules = GifInfoModule.class)
public interface GifInfoComponent {
    void inject(GifInfoActivity gifInfoActivity);
}
