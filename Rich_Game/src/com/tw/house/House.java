package com.tw.house;

import com.tw.asest.AssistancePower;
import com.tw.map.Place;

/**
 * Created by pzzheng on 11/13/16.
 */
public interface House extends Place{
    AssistancePower getItemByIndex(int index_startFrom1);
}
