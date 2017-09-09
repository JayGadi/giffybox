package giphboxhq.com.giphybox;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import giphboxhq.com.giphybox.net.DbHelper;
import giphboxhq.com.giphybox.scopes.UserScope;

/**
 * Created by Owner on 2017-09-06.
 */

@Module
public class AppModule {
    GiphyBoxApplication giphyBoxApplication;

    public AppModule(GiphyBoxApplication giphyBoxApplication) {
        this.giphyBoxApplication = giphyBoxApplication;
    }

    @Provides
    @Singleton
    Context providesApplicationContext(){
        return giphyBoxApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    GiphyBoxApplication providesApplication(){
        return giphyBoxApplication;
    }

    @Singleton
    @Provides
    DbHelper providesDbHelper(Context context){
        return new DbHelper(context);
    }

}
