package cn.bashiquan.cmj.sdk.event.HomeManagerEvent;

import cn.bashiquan.cmj.sdk.event.BaseEvent;

/**
 * Created by mo on 2018/9/27.
 */

public class LocationEvent extends BaseEvent {

    public String cityName;
    public LocationEvent(String cityName){
        this.cityName = cityName;

    }

    public String getCityName() {
        return cityName;
    }
}
