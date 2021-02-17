package edu.eci.arep.services.impl;

import edu.eci.arep.services.CacheService;

import java.util.HashMap;

public class CacheServiceImpl implements CacheService {
    HashMap<String, String > cache = new HashMap<>();
    @Override
    public String returnKey(String city) {
        return this.cache.get(city);
    }

    @Override
    public void saveKey(String city, String key) {
        this.cache.put(city, key);
    }
}
