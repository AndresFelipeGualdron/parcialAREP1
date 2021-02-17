package edu.eci.arep.services;

import java.util.HashMap;

public interface CacheService {

    String returnKey(String city);
    void saveKey(String city, String key);
}
