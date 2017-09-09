package giphboxhq.com.giphybox.net;

import android.content.Context;
import android.util.Log;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

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

    public <T> List<T> getListFromDb(Class<T> klass, String key){
        DB db = getDatabase();
        synchronized (db){
            T[] o = null;
            try {
                if (!db.exists(key))
                    return null;

                o = db.getObjectArray(key, klass);
            }catch (SnappydbException e){
                Log.e(TAG, "getListFromDb: ", e);
            }

            if (o == null){
                return null;
            }
            ArrayList<T> results = new ArrayList<>();
            for(int i = 0; i < o.length; i++){
                results.add(o[i]);
            }
            return results;
        }
    }

    public <T> T saveToList(T o, String key, Class<T> klass){
        List<T> results = getListFromDb(klass, key);
        if(results == null){
            results = new ArrayList<>();
            results.add(o);
        }else if(results.size() == 0){
            results.add(o);
        }else{
            int index = results.indexOf(o);
            if(index == -1){
                results.add(o);
            }else{
                results.set(index, o);
            }
        }
        saveToDb(results.toArray(), key);
        return o;
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

    public void removeFromDb(String key){
        DB db = getDatabase();
        synchronized (db){
            try{
                db.del(key);
            } catch (SnappydbException e){
                Log.e(TAG, "saveToDb: Could not write to db", e );
            }
        }
    }




}
