package session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;
    public Session(Context ctx){
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }
    public void setId(Long id){
        prefs.edit().putLong("id", id).commit();
    }
    public long getId(){
        return prefs.getLong("id",0);
    }
}
