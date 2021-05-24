package com.example.skbaop.data;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class ApiRestrictions {
    private ConcurrentHashMap<String, Integer> restrictions;

    public ApiRestrictions(ConcurrentHashMap<String, Integer> restrictions){
        this.restrictions = restrictions;
    }
}
