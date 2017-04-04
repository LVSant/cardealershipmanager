package com.anew.devl.cardealershipmanager.POJO;

import java.util.Date;

/**
 * Created by devl on 3/30/17.
 */

public class VeiculoShowPojo {


    private String name;
    private String combustivel;
    private String referencia;
    private String fipe_codigo;
    private String preco;

    public VeiculoShowPojo(String name, String combustivel, String referencia, String fipe_codigo, String preco) {
        this.name = name;
        this.combustivel = combustivel;
        this.referencia = referencia;
        this.fipe_codigo = fipe_codigo;
        this.preco = preco;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFipe_codigo() {
        return fipe_codigo;
    }

    public void setFipe_codigo(String fipe_codigo) {
        this.fipe_codigo = fipe_codigo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
