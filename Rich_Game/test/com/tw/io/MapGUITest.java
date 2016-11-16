package com.tw.io;

import com.tw.core.map.GameMapFactory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by pzzheng on 11/15/16.
 */
public class MapGUITest {
    @Test
    public void uiTest() {
        MapGUI.flush(GameMapFactory.defaultMap());
    }
}