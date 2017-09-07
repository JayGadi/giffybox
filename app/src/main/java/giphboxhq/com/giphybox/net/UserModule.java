package giphboxhq.com.giphybox.net;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import giphboxhq.com.giphybox.net.DbHelper;
import giphboxhq.com.giphybox.net.UserRepository;
import giphboxhq.com.giphybox.scopes.UserScope;

/**
 * Created by Owner on 2017-09-07.
 */

@Module
public class UserModule {

    @UserScope
    @Provides
    DbHelper providesDbHelper(Context context){
        return new DbHelper(context);
    }

    @UserScope
    @Provides
    UserRepository providesUserRepository(DbHelper dbHelper){
        return new UserRepository(dbHelper);
    }
}
