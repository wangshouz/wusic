package com.wangsz.wusic.db;

import com.wangsz.greendao.gen.DaoMaster;
import com.wangsz.greendao.gen.DaoSession;
import com.wangsz.wusic.application.App;

/**
 * author: wangsz
 * date: On 2018/6/21 0021
 */
public class GreenDaoManager {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static volatile GreenDaoManager mInstance = null;

    private GreenDaoManager() {
        if (mInstance == null) {
            MySQLiteOpenHelper openHelper = new MySQLiteOpenHelper(App.getInstance(), DBConstant.DB_NAME,null);
            mDaoMaster = new DaoMaster(openHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
