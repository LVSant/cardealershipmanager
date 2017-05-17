package com.anew.devl.cardealershipmanager.fipeclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.POJO.Marca;
import com.anew.devl.cardealershipmanager.POJO.Modelo;
import com.anew.devl.cardealershipmanager.POJO.VeiculoAno;
import com.anew.devl.cardealershipmanager.R;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.AnoVeiculoAdapter;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.MarcaAdapter;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.ModeloAdapter;
import com.anew.devl.cardealershipmanager.others.HttpHandler;
import com.anew.devl.cardealershipmanager.others.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FipeSelectCarActivity extends AppCompatActivity {
    public final static String MARCA_ID = "cardealershipmanager.marcaid";
    public final static String MARCA_NAME = "cardealershipmanager.marcaname";
    public final static String MODELO_ID = "cardealershipmanager.modeloid";
    public final static String MODELO_NAME = "cardealershipmanager.modeloname";
    public final static String ANO_ID = "cardealershipmanager.anoid";
    public final static String PASSO_BUSCA = "cardealershipmanager.passobusca";
    public final static String URL_FIPE_JSON_MARCAS = "http://fipeapi.appspot.com/api/1/carros/marcas.json";

    Long marcaIdValue;
    String modeloIdValue;
    String anoIdValue;
    String marcaName;
    String modeloName;


    MarcaAdapter adapterMarca;
    ModeloAdapter adapterModelo;
    AnoVeiculoAdapter adapterAnoVeiculo;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_fipe);

        if (!Utils.isOnline(getBaseContext())) {
            Toast.makeText(getApplicationContext(),
                    "Não há conexão com a Internet", Toast.LENGTH_LONG).show();
        } else {

            Intent in = getIntent();
            switch (in.getStringExtra(PASSO_BUSCA)) {
                case "1":
                    initBuscaMarca();
                    break;
                case "2":
                    initBuscaModelo(in);
                    break;
                case "3":
                    initBuscaAno(in);
                    break;
                default:
                    initBuscaMarca();
                    break;
            }
        }

    }

    private void initBuscaMarca() {
        setTitle("Busca FIPE - Marcas");

        TextView textSelecione = (TextView) findViewById(R.id.textSelecione);
        textSelecione.setText("Selecione o Fabricante");

        TextView textLista = (TextView) findViewById(R.id.textList);
        textLista.setText("Fabricantes");

        Button buttonMarca = (Button) findViewById(R.id.btnUpdate);
        buttonMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new readMarcas().execute(URL_FIPE_JSON_MARCAS);
            }
        });

        configureOnClickMarcaItem();
        buttonMarca.callOnClick();
    }

    private void initBuscaModelo(Intent in) {
        setTitle("Busca FIPE - Modelos");

        TextView textSelecione = (TextView) findViewById(R.id.textSelecione);
        textSelecione.setText("Selecione o Modelo do Veículo");

        TextView textLista = (TextView) findViewById(R.id.textList);
        textLista.setText("Modelos");

        marcaIdValue = in.getLongExtra(MARCA_ID, 0l);

        Button buttonModelo = (Button) findViewById(R.id.btnUpdate);

        final String urlBuscaModelo = "http://fipeapi.appspot.com/api/1/carros/veiculos/" + marcaIdValue + ".json";
        buttonModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new readModelos().execute(urlBuscaModelo);
            }
        });

        configureOnClickModeloItem(marcaIdValue);
        buttonModelo.callOnClick();
    }

    private void initBuscaAno(Intent in) {
        setTitle("Busca FIPE - Ano Veículo");

        TextView textSelecione = (TextView) findViewById(R.id.textSelecione);
        textSelecione.setText("Selecione o Ano do Veículo");

        TextView textLista = (TextView) findViewById(R.id.textList);
        textLista.setText("Versões");

        marcaIdValue = in.getLongExtra(MARCA_ID, 0l);
        modeloIdValue = in.getStringExtra(MODELO_ID);

        Button buttonAno = (Button) findViewById(R.id.btnUpdate);
        final String urlBuscaAno = "http://fipeapi.appspot.com/api/1/carros/veiculo/" + marcaIdValue + "/" + modeloIdValue + ".json";

        buttonAno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new readAnos().execute(urlBuscaAno);
            }
        });

        configureOnClickAnosItem(marcaIdValue, modeloIdValue);
        ;
        buttonAno.callOnClick();

    }


    /*
     * Métodos referente a busca de Marca
     * na ordem:
     *
     * 1 - configureOnClickMarcaItem
     *  Ações que serão tomadas ao clicar na marca selecionada
     * 2 - readMarcas
     *  Thread que vai buscar os dados JSON na URL
     * 3 - populateListMarca
     *  Coloca os items e o adapter na ListView
     */
    private void configureOnClickMarcaItem() {
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), FipeSelectCarActivity.class);
                long marcaSelecionadaID = adapterMarca.getItem(position).getId();

                intent.putExtra(PASSO_BUSCA, "2");
                intent.putExtra(MARCA_ID, marcaSelecionadaID);
                intent.putExtra(MARCA_NAME, adapterMarca.getItem(position).getName());
                startActivity(intent);
            }
        });

    }

    private class readMarcas extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return new HttpHandler().makeServiceCall(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {

                JSONArray marcasItem = new JSONArray(result);
                populateListMarca(marcasItem);
            } catch (Exception e) {
                Log.d("readMarcas", e.getLocalizedMessage());
            }
        }
    }

    private void populateListMarca(JSONArray marcasItem) {

        ArrayList<Marca> marcas = new ArrayList<>();

        try {
            for (int i = 0; i < marcasItem.length(); i++) {
                JSONObject jsonMarca = marcasItem.optJSONObject(i);

                String name = (String) jsonMarca.get("fipe_name");
                Integer id = (Integer) jsonMarca.get("id");
                String key = (String) jsonMarca.get("key");

                Marca marca = new Marca(name, id, key);

                marcas.add(marca);
            }
        } catch (JSONException jsonex) {
            Log.d("populateListMarca", jsonex.getLocalizedMessage());
        }


        this.adapterMarca = new MarcaAdapter(this, marcas);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapterMarca);

    }

    /*
       * Métodos referente a busca de Modelo na ordem:
       *
       * 1 - configureOnClickModeloItem
       *  Ações que serão tomadas ao clicar no modelo selecionado
       * 2 - readModelos
       *  Thread que vai buscar os dados JSON na URL
       * 3 - populateListModelo
       *  Coloca os items e o adapter na ListView
       */

    private void configureOnClickModeloItem(final Long marcaId) {
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), FipeSelectCarActivity.class);

                String idModelo = adapterModelo.getItem(position).getKey();
                String modelo = adapterModelo.getItem(position).getName();

                intent.putExtra(MODELO_ID, idModelo);
                intent.putExtra(MARCA_ID, marcaId);
                intent.putExtra(MODELO_NAME, modelo);
                intent.putExtra(PASSO_BUSCA, "3");

                startActivity(intent);
            }
        });

    }

    private void populateListModelo(JSONArray modelosItems) {

        ArrayList<Modelo> modelos = new ArrayList<>();

        try {
            for (int i = 0; i < modelosItems.length(); i++) {
                JSONObject jsonModelo = modelosItems.optJSONObject(i);

                String name = (String) jsonModelo.get("fipe_name");

                Object idjson = jsonModelo.get("id");
                String id;
                if (idjson instanceof String) {
                    id = idjson.toString();
                } else {
                    id = Integer.parseInt(idjson.toString()) + "";

                }
                Modelo modelo = new Modelo(name, id);


                modelos.add(modelo);
            }
        } catch (JSONException jsonex) {
            Log.d("populateListModelo", jsonex.getLocalizedMessage());
        }


        this.adapterModelo = new ModeloAdapter(this, modelos);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapterModelo);

    }

    private class readModelos extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return new HttpHandler().makeServiceCall(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                JSONArray modelosItem = new JSONArray(result);
                populateListModelo(modelosItem);
            } catch (Exception e) {
                Log.d("readModelo", e.getLocalizedMessage());
            }
        }
    }

    /*
      * Métodos referente a busca de Ano na ordem:
      *
      * 1 - configureOnClickAnoItem
      *  Ações que serão tomadas ao clicar no ano selecionado
      * 2 - readANos
      *  Thread que vai buscar os dados JSON na URL
      * 3 - populateListAno
      *  Coloca os items e o adapter na ListView
      */
    private void configureOnClickAnosItem(final Long marcaId, final String modeloId) {
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), VeiculoShowActivity.class);


                intent.putExtra(ANO_ID, adapterAnoVeiculo.getItem(position).getKey());
                intent.putExtra(MODELO_ID, modeloId );
                intent.putExtra(MARCA_ID, marcaId);
                intent.putExtra(MARCA_NAME, adapterAnoVeiculo.getItem(position).getMarca());

                startActivity(intent);
            }
        });

    }

    private class readAnos extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return new HttpHandler().makeServiceCall(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {

                JSONArray marcasItem = new JSONArray(result);
                populateListAnos(marcasItem);
            } catch (Exception e) {
                Log.d("readAnos", e.getLocalizedMessage());
            }
        }
    }

    private void populateListAnos(JSONArray veiculoAnosJSonArray) {

        ArrayList<VeiculoAno> veiculoAnos = new ArrayList<>();

        try {
            for (int i = 0; i < veiculoAnosJSonArray.length(); i++) {
                JSONObject jsonVeiculoAno = veiculoAnosJSonArray.optJSONObject(i);

                String name = (String) jsonVeiculoAno.get("name");
                //String id = (String) jsonVeiculoAno.get("id");
                String key = (String) jsonVeiculoAno.get("key");
                String marca = (String) jsonVeiculoAno.get("fipe_marca");
                String veiculo = (String) jsonVeiculoAno.get("veiculo");

                VeiculoAno vAno = new VeiculoAno(name, marca, key, 0l, veiculo);

                veiculoAnos.add(vAno);
            }
        } catch (JSONException jsonex) {
            Log.d("readAnos", jsonex.getLocalizedMessage());
        }


        this.adapterAnoVeiculo = new AnoVeiculoAdapter(this, veiculoAnos);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapterAnoVeiculo);

    }

    public void onBack(MenuItem view) {
        this.finish();
    }

}

