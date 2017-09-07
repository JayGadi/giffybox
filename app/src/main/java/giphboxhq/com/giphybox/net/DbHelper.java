package giphboxhq.com.giphybox.net;

import android.content.Context;
import android.util.Log;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import javax.inject.Inject;

/**
 * Created by Owner on 2017-09-07.
 */

public class DbHelper {
    private static final String TAG = "DbHelper";

    private Context context;
    private DB snappyDb;

    @Inject
    public DbHelper(Context context) {
        this.context = context;
    }

    public DB getDatabase(){
        try{
            if(snappyDb == null || !snappyDb.isOpen()){
                snappyDb = DBFactory.open(context);
            }
        }catch (SnappydbException e){
            Log.e(TAG, "getDatabase: SnappyDb will not open", e );
        }

        return snappyDb;
    }

    public <T> T getFromDb(Class<T> objectClass, String key){
        DB db = getDatabase();
        synchronized (db){
            T o = null;
            try{
                if(!db.exists(key)){
                    return null;
                }
                o = db.getObject(key, objectClass);
            } catch (SnappydbException e){
                Log.e(TAG, "getObjectFromDb: Could not read db", e );
            }
        return o;
        }
    }

    public <T> T saveToDb(T o, String key){
        DB db = getDatabase();
        synchronized (db){
            try{
                db.put(key, o);
                return o;
            } catch (SnappydbException e){
                Log.e(TAG, "saveToDb: Could not write to db", e );
                return null;
            }
        }
    }


}
