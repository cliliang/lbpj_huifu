package com.baluobo.common.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.baluobo.app.AppContext;
import com.baluobo.user.model.User;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/10.
 */
public class LuoBoDBM {
    private static SQLiteDatabase database;
    private static LuoBoDBM dbmanager;

    private LuoBoDBM(Context context) {
        AppContext appContext = (AppContext) context.getApplicationContext();
        database = appContext.getDatabase();
    }

    public static LuoBoDBM getInstance(Context context) {
        if (dbmanager == null) {
            dbmanager = new LuoBoDBM(context);
        }
        return dbmanager;
    }

    public boolean insertUser(User user) {
        if (user == null) {
            return false;
        }
        database.delete(User.USER_TABLE, null, null);
        long rowId = database.insert(User.USER_TABLE, null, user.toValues());
        return rowId > -1;
    }

    public User getUser() {
        Cursor cursor = null;
        try {
            cursor = database.query(User.USER_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                return User.fromCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public boolean logout() {
        int rowId = database.delete(User.USER_TABLE, null, null);
        return rowId > 0;
    }

    public User queryUser(int userId) {
        String selection = User.TABLE_USER_ID + "=?";
        String[] args = new String[]{String.valueOf(userId)};
        Cursor cursor = null;
        try {
            cursor = database.query(User.USER_TABLE, null, selection, args, null, null, null);
            if (cursor != null && cursor.moveToNext()) {
                return User.fromCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public boolean updateUserInfo(User newUser) {
        User user = queryUser(newUser.getUserId());
        if (user != null) {
            String selection = User.TABLE_USER_ID + "=?";
            String[] args = new String[]{String.valueOf(newUser.getUserId())};
            int rows = database.update(User.USER_TABLE, newUser.toValues(), selection, args);
            return rows > 0;
        }
        return false;
    }


    public boolean updateUserGesture(User user, String gesture) {
        if (user == null) {
            return false;
        }
        String selection = User.TABLE_USER_ID + "=?";
        String[] args = new String[]{String.valueOf(user.getUserId())};
        ContentValues values = new ContentValues();
        values.put(User.TABLE_GESTURE, gesture);
        int row = database.update(User.USER_TABLE, values, selection, args);
        return row > 0;
    }

    public void updateUserToken(int uid, String newToken) {
        if (TextUtils.isEmpty(newToken)) {
            return;
        }
        String selection = User.TABLE_USER_ID + "=?";
        String[] args = new String[]{String.valueOf(uid)};
        ContentValues values = new ContentValues();
        values.put(User.TABLE_TOKEN, newToken);
        database.update(User.USER_TABLE, values, selection, args);
    }

}
