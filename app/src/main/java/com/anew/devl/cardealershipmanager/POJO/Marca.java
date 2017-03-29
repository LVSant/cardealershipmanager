package com.anew.devl.cardealershipmanager.POJO;

/**
 * Created by devl on 3/23/17.
 */

public class Marca {

    private String name;
    private Integer id;
    private String key;

    public Marca(String name, Integer id, String key) {
        this.name = name;
        this.id = id;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
