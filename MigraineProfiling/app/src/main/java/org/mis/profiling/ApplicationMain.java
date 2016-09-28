package org.mis.profiling;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import org.mis.profiling.models.dao.DaoMaster;
import org.mis.profiling.models.dao.DaoSession;
import org.mis.profiling.models.dao.UserDao;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mis on 3/22/2016.
 */
public class ApplicationMain extends Application {
    private static final String SESSION_MANAGER = "SESSION_MANAGER";
    private static final String APP_FIRST_RUN = "APP_FIRST_RUN";
    private static final String APP_USER_NAME = "APP_USER_NAME";

    private SharedPreferences userSessionManager;

    private SQLiteDatabase db;

    private static DaoMaster _daoMaster;
    private static DaoSession _daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Ubuntu-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public boolean isAppFirstRun(){
        userSessionManager = getApplicationContext().getSharedPreferences(SESSION_MANAGER, Context.MODE_PRIVATE);
        return userSessionManager.getBoolean(APP_FIRST_RUN, true);
    }

    public void setAppFirstRun(boolean status){
        userSessionManager = getApplicationContext().getSharedPreferences(SESSION_MANAGER, Context.MODE_PRIVATE);
        userSessionManager.edit().putBoolean(APP_FIRST_RUN, status).commit();
    }

    public void setAppUsername(String name){
        userSessionManager = getApplicationContext().getSharedPreferences(SESSION_MANAGER, Context.MODE_PRIVATE);
        userSessionManager.edit().putString(APP_USER_NAME, name).commit();
    }

    public String getAppUserName(){
        userSessionManager = getApplicationContext().getSharedPreferences(SESSION_MANAGER, Context.MODE_PRIVATE);
        return userSessionManager.getString(APP_USER_NAME, "");
    }

    public DaoMaster getDaoMaster() {
        return _daoMaster;
    }

    public DaoSession getDaoSession() {
        return _daoSession;
    }

    protected void setUpApplicationDB(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "migraine-db", null);
        db = helper.getWritableDatabase();
        _daoMaster = new DaoMaster(db);
        _daoSession = _daoMaster.newSession();

    }
}
