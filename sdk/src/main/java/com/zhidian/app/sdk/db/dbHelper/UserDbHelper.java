package com.zhidian.app.sdk.db.dbHelper;

import android.content.Context;
import com.zhidian.app.sdk.db.dao.DaoMaster;
import com.zhidian.app.sdk.db.dao.DaoSession;
import com.zhidian.app.sdk.db.dao.UserDao;
import com.zhidian.app.sdk.db.entity.User;

/**
 * Created by mocf on 2017/7/19.
 */
public class UserDbHelper {

    public String user_db_file = "%d.dat";
    private static UserDbHelper instance;
    private DaoSession session;
    private String dbFile;
    private UserDbHelper(){};
    public static UserDbHelper getInstance(){
        if(instance == null){
            instance = new UserDbHelper();
        }
        return instance;
    }

    public void init(Context mContext,long userId){
        if(mContext == null){
            throw new IllegalStateException("Cannot get the service context");
        }
        this.dbFile = String.format(user_db_file,userId);
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(mContext,dbFile,null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        session = daoMaster.newSession();
    }

    public String getDbFile(){
        return  dbFile;
    }

    /***************************LoginUser************************************/


    /***************************User****************************************/
    public void saveUser(User user){
        session.getUserDao().insertOrReplace(user);
    }

    public User getUserById(long userId){
        return session.getUserDao().queryBuilder()
                .where(UserDao.Properties.UserId.eq(userId))
                .unique();
    }
}
