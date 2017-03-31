package com.anew.devl.cardealershipmanager.POJO;

/**
 *
 * All attributes matches exactly the same parameter from the JSON.
 *
 * Example
 * URL: GET http://fipeapi.appspot.com/api/1/carros/veiculo/21/4828.json
 *
 [{
 "fipe_marca": "Fiat",
 "fipe_codigo": "2014-1",
 "name": "2014 Gasolina",
 "marca": "FIAT",
 "key": "2014-1",
 "veiculo": "Palio 1.0 ECONOMY Fire Flex 8V 4p",
 "id": "2014-1"
 },
 {
 "fipe_marca": "Fiat",
 "fipe_codigo": "2013-1",
 "name": "2013 Gasolina",
 "marca": "FIAT",
 "key": "2013-1",
 "veiculo": "Palio 1.0 ECONOMY Fire Flex 8V 4p",
 "id": "2013-1"
 }]
 *
 * Created by devl on 3/30/17.
 */

public class VeiculoAno {
    private String name;
    private String marca;
    private String key;
    private Long id;
    private String veiculo;

    public VeiculoAno(String name, String marca, String key, Long id, String veiculo) {
        this.name = name;
        this.marca = marca;
        this.key = key;
        this.id = id;
        this.veiculo = veiculo;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }
}
