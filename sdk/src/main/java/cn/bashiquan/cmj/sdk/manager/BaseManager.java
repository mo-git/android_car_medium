package cn.bashiquan.cmj.sdk.manager;

import android.content.Context;

/**
 * Created by mocf on 2017/7/14.
 */
public interface BaseManager {
    void init(Context mContext);
    void setClassName(String className);// 防止event 在多个位置接受,如果需要在多个位置接受在CoreService中可不设置
}
