package com.baluobo.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class LuoBoDatabaseOpenHelper extends SQLiteOpenHelper {
    private static LuoBoDatabaseOpenHelper instance;
    private final static String DATABASE_NAME = "luobopiaoju.db";
    private final static int DATABASE_VERSION = 5;

    private LuoBoDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static LuoBoDatabaseOpenHelper getInstance(Context context){
        if (instance == null){
            instance = new LuoBoDatabaseOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.genUserDatabaseTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2){
            db.execSQL(User.updateDB1To2());
        }else if (oldVersion == 2 && newVersion == 3){
            db.execSQL(User.updateDB2To3dot1());
            db.execSQL(User.updateDB2To3dot2());
            db.execSQL(User.updateDB2To3dot3());
            db.execSQL(User.updateDB2To3dot4());
        }else if (oldVersion == 3 && newVersion == 4){
            db.execSQL(User.updateDB3To4Dot1());
            db.execSQL(User.updateDB3To4Dot2());
        } else if (oldVersion == 4 && newVersion == 5) {
            db.execSQL(User.updateDB4To5Dot1());
            db.execSQL(User.updateDB4To5Dot2());
            db.execSQL(User.updateDB4To5Dot3());
        } else {
            Log.i("chen", "update db from " + oldVersion + " to " + newVersion + " fail");
        }
    }
}
