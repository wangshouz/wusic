package com.wangsz.libs.utils;

import com.wangsz.wusic.application.App;

import java.util.Properties;

/**
 * author: wangsz
 * date: On 2018/6/19 0019
 * 获取.properties文件内容
 */
public class PropertiesUtil {

    public static String load(String key) {

        String value = null;
        Properties properties = new Properties();
        try {
            properties.load(App.getInstance().getAssets().open("appkey.properties"));
            value = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
