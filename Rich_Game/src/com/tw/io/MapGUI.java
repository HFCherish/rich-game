package com.tw.io;

import com.tw.core.giftHouse.GiftHouse;
import com.tw.core.map.*;
import com.tw.core.player.Player;
import com.tw.core.toolHouse.ToolHouse;

import java.util.List;

/**
 * Created by pzzheng on 11/15/16.
 */
public class MapGUI {

    public static void flush(GameMap map) {
        int width = map.getWidth();
        int height = map.getHeight();
        String[][] mapUI = new String[height][width];
        for( int i=0; i<height; i++ ) {
            for( int j=0; j<width; j++ ) {
                mapUI[i][j] = " ";
            }
        }

        int count = 0;
        List<Place> places = map.getPlaces();
        for (int i = 0; i < width; i++) {
            mapUI[0][i] = convert(places.get(count++));
        }
        for( int i = 1; i<height; i++ ) {
            mapUI[i][width-1] = convert(places.get(count++));
        }
        for( int i = width-2; i>=0; i--) {
            mapUI[height-1][i] = convert(places.get(count++));
        }
        for( int i = height-2; i>0; i--) {
            mapUI[i][0] = convert(places.get(count++));
        }

        for( int i=0; i<height; i++ ) {
            for(int j=0; j<width; j++ ) {
                System.out.print(mapUI[i][j]);
            }
            System.out.println();
        }
    }

    private static String convert(Place place) {
        if (place instanceof Starting) return "S";
        if (place instanceof Estate) {
            Player owner = ((Estate) place).getOwner();
            if( owner != null) {
                return owner.getId();
            }
            return "0";
        }
        if( place instanceof Hospital) return "H";
        if( place instanceof ToolHouse) return "T";
        if( place instanceof GiftHouse) return "G";
        if( place instanceof Prison) return "P";
        if( place instanceof MagicHouse) return "M";
        if( place instanceof MineralEstate) return "$";
        if( place instanceof BombPlace) return "*";
        if( place instanceof BlockPlace) return "|";
        return " ";
    }
}
