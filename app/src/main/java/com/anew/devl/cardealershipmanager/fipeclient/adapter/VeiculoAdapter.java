package com.anew.devl.cardealershipmanager.fipeclient.adapter;

import java.util.Date;

/**
 * Created by devl on 3/30/17.
 */

public class VeiculoAdapter {

    private long id;
    private String name;
    private String marca;
    private Double valor;
    private Date adicionado;

    public VeiculoAdapter(String name, String marca, Double valor, Date adicionado) {
        this.name = name;
        this.marca = marca;
        this.valor = valor;
        this.adicionado = adicionado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getAdicionado() {
        return adicionado;
    }

    public void setAdicionado(Date adicionado) {
        this.adicionado = adicionado;
    }
}
