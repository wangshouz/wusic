package com.wangsz.wusic.db;

import com.elvishew.xlog.XLog;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * author: wangsz
 * date: On 2018/6/20 0020
 */
public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        XLog.d("oldVersion = " + oldVersion + ",newVersion = " + newVersion);
        //************************************************//
        // Migrate from version 0 to version 1
        if (oldVersion == 0) {

            oldVersion++;
        }
    }
}
