package com.anew.devl.cardealershipmanager.POJO;

import java.util.Date;

/**
 * Created by devl on 3/30/17.
 */

public class Veiculo {

    private long id;
    private String name;
    private String marca;
    private String combustivel;
    private Double preco;
    private String adicionado;

    public Veiculo(long id, String name, String marca, String combustivel, Double preco, String adicionado) {
        this.id = id;
        this.name = name;
        this.marca = marca;
        this.combustivel = combustivel;
        this.preco = preco;
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

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getAdicionado() {
        return adicionado;
    }

    public void setAdicionado(String adicionado) {
        this.adicionado = adicionado;
    }
}
