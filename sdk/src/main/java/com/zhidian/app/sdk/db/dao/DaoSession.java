package com.zhidian.app.sdk.db.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.zhidian.app.sdk.db.entity.LoginUser;
import com.zhidian.app.sdk.db.entity.User;

import com.zhidian.app.sdk.db.dao.LoginUserDao;
import com.zhidian.app.sdk.db.dao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig loginUserDaoConfig;
    private final DaoConfig userDaoConfig;

    private final LoginUserDao loginUserDao;
    private final UserDao userDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        loginUserDaoConfig = daoConfigMap.get(LoginUserDao.class).clone();
        loginUserDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        loginUserDao = new LoginUserDao(loginUserDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(LoginUser.class, loginUserDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        loginUserDaoConfig.getIdentityScope().clear();
        userDaoConfig.getIdentityScope().clear();
    }

    public LoginUserDao getLoginUserDao() {
        return loginUserDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}