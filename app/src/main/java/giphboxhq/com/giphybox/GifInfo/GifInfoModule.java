package giphboxhq.com.giphybox.GifInfo;

import dagger.Module;
import dagger.Provides;
import giphboxhq.com.giphybox.scopes.UiScope;

/**
 * Created by Owner on 2017-09-07.
 */

@Module
public class GifInfoModule {

    private final GifInfoView view;

    public GifInfoModule(GifInfoView view) {
        this.view = view;
    }

    @Provides
    @UiScope
    GifInfoView providesView(){
        return view;
    }
}
