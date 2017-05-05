package com.anew.devl.cardealershipmanager.POJO;

import java.io.Serializable;

/**
 * Created by devl on 3/30/17.
 */

public class Veiculo implements Serializable {

    private long id;
    private String name;
    private String marca;
    private String combustivel;
    private Double preco;
    private String adicionado;
    private String fipe_codigo;

    public Veiculo(long id, String name, String marca, String combustivel, Double preco, String adicionado, String fipe_codigo) {
        this.id = id;
        this.name = name;
        this.marca = marca;
        this.combustivel = combustivel;
        this.preco = preco;
        this.adicionado = adicionado;
        this.fipe_codigo = fipe_codigo;
    }

    public String getFipe_codigo() {
        return fipe_codigo;
    }

    public void setFipe_codigo(String fipe_codigo) {
        this.fipe_codigo = fipe_codigo;
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
