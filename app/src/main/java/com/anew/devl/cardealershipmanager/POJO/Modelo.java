package com.anew.devl.cardealershipmanager.POJO;

/**
 * Created by devl on 3/29/17.
 */

public class Modelo {
    private String name;


    private String key;

    public Modelo(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
