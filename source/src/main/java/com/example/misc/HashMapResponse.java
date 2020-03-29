package com.example.misc;

import java.util.HashMap;

public class HashMapResponse extends HashMap<String,Object> {
    public HashMap<String,Object> getMap(){ return this; }
    public HashMapResponse putVal(String v1,Object v2){
        super.put(v1,v2);
        return this;
    }
}