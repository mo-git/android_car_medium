package com.zhidian.dao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {

    private static String entityPath = "com.zhidian.app.sdk.db.entity";
    public static void main(String[] args){
        int userDbVersion = 1;

        Schema userSchema = new Schema(userDbVersion,"com.zhidian.app.sdk.db.dao");
        userSchema.enableKeepSectionsByDefault();

        /************************添加entity*****************************/

        addLoginUser(userSchema);
        addUser(userSchema);

        /*****************************************************/
        String path = "../sdk/src/main/java/";
        try {
            new DaoGenerator().generateAll(userSchema, path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void addLoginUser(Schema schema) {
        Entity loginUser =  schema.addEntity("LoginUser");
        loginUser.implementsSerializable();
        loginUser.setTableName("login_user");
        loginUser.setClassNameDao("LoginUserDao");
        loginUser.setJavaPackage(entityPath);

        loginUser.addIdProperty().autoincrement();
        loginUser.addStringProperty("toker").notNull();
        loginUser.addLongProperty("userId").unique().notNull();
        loginUser.addStringProperty("userName").unique().notNull();
        loginUser.addStringProperty("password").notNull();
        loginUser.addStringProperty("relaveName");
        loginUser.addIntProperty("status").notNull();//0退出  1 登陆
        loginUser.addLongProperty("lastLoginTime");
        loginUser.setHasKeepSections(true);
    }

    private static void addUser(Schema schema) {
        Entity user =  schema.addEntity("User");
        user.implementsSerializable();
        user.setTableName("user");
        user.setClassNameDao("UserDao");
        user.setJavaPackage(entityPath);

        user.addIdProperty().autoincrement();
        user.addLongProperty("userId").unique().notNull();
        user.addStringProperty("username").notNull();
        user.addIntProperty("type").notNull();//类型  0 提问者,1 导师
        user.addStringProperty("mobile");//电话
        user.addStringProperty("schoolName");//学校
        user.addStringProperty("specialtyName");//专业
        user.addLongProperty("registerTime");//注册时间

        user.setHasKeepSections(true);
    }
}
