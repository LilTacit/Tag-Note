package com.mobile.kiril.tagnote.themes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collections {
    private Map<String, Collection> collections;
    public static String DEFAULT_ID = "collection_default";
    public static String PURCHASED_ID = "collection_purchased";

    public Collections() {
        collections = new HashMap<>();

        collections.put("collection_common", new Collection("collection_common", "Common collection", "Common", "2.99"));
        collections.put("collection_dark", new Collection("collection_dark", "Dark collection", "Dark", "2.99"));
        collections.put("collection_juicy", new Collection("collection_juicy", "Juicy collection", "Juicy", "2.99"));
        collections.put("collection_minimalistic", new Collection("collection_minimalistic", "Minimalistic collection", "Minimalistic", "2.99"));
        collections.put(DEFAULT_ID, new Collection(DEFAULT_ID, "Default collection", "Default", "2.99"));
        collections.put(PURCHASED_ID, new Collection(PURCHASED_ID, "Purchased collection", "Purchased", "2.99"));
    }

    public Map<String, Collection> getCollections(){
        return collections;
    }

    public Collection getCollectionById(String id) {
        return collections.get(id);
    }
}
