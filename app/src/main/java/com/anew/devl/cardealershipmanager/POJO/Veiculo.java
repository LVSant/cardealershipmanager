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
    private String anoModelo;
    private boolean isCar;


    public Veiculo(long id, String name, String marca, String combustivel, Double preco,
                   String adicionado, String fipe_codigo, String anoModelo, boolean isCar) {
        this.id = id;
        this.name = name;
        this.marca = marca;
        this.combustivel = combustivel;
        this.preco = preco;
        this.adicionado = adicionado;
        this.fipe_codigo = fipe_codigo;
        this.anoModelo = anoModelo;
        this.isCar = isCar;
    }


    public Veiculo(String name, String marca, String combustivel, Double preco,
                   String adicionado, String fipe_codigo, String anoModelo, boolean isCar) {

        this.name = name;
        this.marca = marca;
        this.combustivel = combustivel;
        this.preco = preco;
        this.adicionado = adicionado;
        this.fipe_codigo = fipe_codigo;
        this.anoModelo = anoModelo;
        this.isCar = isCar;
    }

    public boolean getIsCar() {
        return isCar;
    }

    public void setIsCar(boolean car) {
        isCar = car;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setAdicionado(String adicionado) {
        this.adicionado = adicionado;
    }

    public void setFipe_codigo(String fipe_codigo) {
        this.fipe_codigo = fipe_codigo;
    }

    public String getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(String anoModelo) {
        this.anoModelo = anoModelo;
    }

    public String getFipe_codigo() {
        return fipe_codigo;
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


    public String getCombustivel() {
        return combustivel;
    }


    public Double getPreco() {
        return preco;
    }


    public String getAdicionado() {
        return adicionado;
    }


}
