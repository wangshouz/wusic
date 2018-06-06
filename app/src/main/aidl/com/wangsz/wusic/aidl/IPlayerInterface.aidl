// IPlayerInterface.aidl
package com.wangsz.wusic.aidl;

import com.wangsz.wusic.aidl.Song;

// Declare any non-default types here with import statements
interface IPlayerInterface {
    void action(int action,in Song song);
}
