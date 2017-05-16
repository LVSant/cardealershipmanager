package com.anew.devl.cardealershipmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.others.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devl on 5/4/17.
 */

public class GaragemQueries {


    static class DBAsyncTask extends AsyncTask<Context, Void, List<Veiculo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(List<Veiculo> veiculos) {
            super.onPostExecute(veiculos);
        }


        @Override
        protected List<Veiculo> doInBackground(Context... params) {
            Context context = params[0];
            List<Veiculo> veiculos = new ArrayList<>();
            DBHelper helper = new DBHelper(context);
            SQLiteDatabase db = helper.getReadableDatabase();

            String[] projection = {
                    DBHelper.VeiculoDBHelper._ID,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_NAME,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_MARCA,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_PRECO,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_COMBUSTIVEL,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_FIPE_CODIGO,
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_ANO_MODELO
            };

            //String selection =  DBHelper.VeiculoDBHelper;
            String sortByAdd =
                    DBHelper.VeiculoDBHelper.COLUMN_NAME_ADICIONADO + " DESC";

            Cursor c = db.query(
                    DBHelper.VeiculoDBHelper.TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    null,                                     // The columns for the WHERE clause
                    null,                                     // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortByAdd                                 // The sort order
            );


            if (c.moveToFirst()) {
                do {
                    //id, name, marca, preco, adicionado
                    long id = c.getLong(c.getColumnIndexOrThrow("_id"));
                    String name = c.getString(c.getColumnIndexOrThrow("name"));
                    String marca = c.getString(c.getColumnIndexOrThrow("marca"));
                    String combustivel = c.getString(c.getColumnIndexOrThrow("combustivel"));
                    Double preco = c.getDouble(c.getColumnIndexOrThrow("preco"));
                    String adicionado = c.getString(c.getColumnIndexOrThrow("adicionado"));
                    String fipe_codigo = c.getString(c.getColumnIndexOrThrow("fipe_codigo"));
                    String anoModelo = c.getString(c.getColumnIndexOrThrow("anomodelo"));


                    veiculos.add(new Veiculo(id, name, marca, combustivel, preco, adicionado,
                            fipe_codigo, anoModelo));


                } while (c.moveToNext());
            }

            return veiculos;
        }
    }

}
