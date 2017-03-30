package com.anew.devl.cardealershipmanager.POJO;

/**
 * Created by devl on 3/29/17.
 */

public class Modelo {
    private String name;

    private Long idModelo;
    private String key;

    public Modelo(String name, Long idModelo, String key) {
        this.name = name;

        this.idModelo = idModelo;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) {
        this.idModelo = idModelo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
