package com.zonebecreations.a2021codefiest.directionsLib;

import com.zonebecreations.a2021codefiest.pojo.MapDistance;
import com.zonebecreations.a2021codefiest.pojo.MapTimeDuration;

/**
 * Created by Vishal on 10/20/2018.
 */

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
    void onTaskDoneDistanse(MapDistance mapDistance);
    void onTaskDoneTimeDuration(MapTimeDuration mapTimeDuration);
}
