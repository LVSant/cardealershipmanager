package com.anew.devl.cardealershipmanager.POJO;

/**
 * Created by devl on 3/29/17.
 */

public class Modelo {
    private String name;
    private Long idMarca;
    private Long idModelo;

    public Modelo(String name, Long idMarca, Long idModelo) {
        this.name = name;
        this.idMarca = idMarca;
        this.idModelo = idModelo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Long idMarca) {
        this.idMarca = idMarca;
    }

    public Long getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) {
        this.idModelo = idModelo;
    }
}
