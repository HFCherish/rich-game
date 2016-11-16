package com.tw.core.map;

import com.tw.core.giftHouse.Fund;
import com.tw.core.giftHouse.GiftHouse;
import com.tw.core.giftHouse.LuckyGod;
import com.tw.core.giftHouse.PointCard;
import com.tw.core.toolHouse.ToolHouse;
import com.tw.core.toolHouse.ToolType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzzheng on 11/15/16.
 */
public class GameMapFactory {
        public static int estateCount = 1;
    public static GameMap defaultMap() {
        List<Place> places = new ArrayList<>();
        places.add(new Starting());
        addEstates(places, 13, 200);
        places.add(new Hospital());
        addEstates(places, 13, 200);
        places.add(new ToolHouse(ToolType.Block, ToolType.RobotDull, ToolType.Bomb));
        addEstates(places, 6, 500);
        places.add(new GiftHouse(new Fund(2000), new PointCard(200), new LuckyGod()));
        addEstates(places, 13, 300);
        places.add(new Prison());
        addEstates(places, 13, 300);
        places.add(new MagicHouse());
        places.add(new MineralEstate(20));
        places.add(new MineralEstate(80));
        places.add(new MineralEstate(100));
        places.add(new MineralEstate(40));
        places.add(new MineralEstate(80));
        places.add(new MineralEstate(60));

        return new GameMapImpl(29, 8, places.toArray(new Place[places.size()]));
    }

    private static void addEstates(List<Place> places, int count, int emptyPrice) {
        for(int i = 0; i< count; i++ ) {
            places.add(new Estate(emptyPrice, estateCount++));
        }
    }
}
