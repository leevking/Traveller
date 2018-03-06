package com.levking.ivan.traveller.fragments.dummy;

import android.support.annotation.NonNull;

import com.mukesh.countrypicker.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static  List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static int COUNT = 0;
//
//
//    static {
//        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i, Country.getCountryByName("Russia"), Country.getCountryByName("Austria"),10000000,150000000));
//        }
//      //  addItem(createDummyItem(2,Country.getCountryByName("Russia"), Country.getCountryByName("Denmark"),10000000,150000000));
//    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(COUNT), item);
    }

    public static void removeItem(String id){
        //TODO: выяснить причину уменьшения айди на 1 мне не удалось, потому костыль
        id = String.valueOf(Integer.valueOf(id)+1);
        ITEMS.remove(ITEM_MAP.get(id));
        ITEM_MAP.remove(id);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem implements Comparable<DummyItem>{
        public  String id;
        public  String content;
        public  String depCountryName;
        public  String destCountryName;
        public  long depDate;
        public  long destDate;
        public  String details;
        public  Country depCountry;
        public  Country destCountry;
        public  boolean state;

        public DummyItem(Country depCountry, Country destCountry, String details, long depDate, long destDate, boolean state) {
            this.id = String.valueOf(COUNT);
            COUNT++;
            this.depCountryName = depCountry.getCode();
            this.destCountryName = destCountry.getCode();
            this.content = depCountry.getName() + " " + destCountry.getName();
            this.details = details;
            this.depCountry = depCountry;
            this.destCountry = destCountry;
            this.depDate = depDate;
            this.destDate = destDate;
            this.state = state;
        }

        @Override
        public String toString() {
            return content;
        }

        @Override
        public int compareTo(DummyItem dummy) {
            if (this.state && !dummy.state)return 1;
            else if (this.state == dummy.state) {
                if (this.depDate > dummy.depDate) return 1;
                else return -1;
            } else return -1;

        }
    }
}
