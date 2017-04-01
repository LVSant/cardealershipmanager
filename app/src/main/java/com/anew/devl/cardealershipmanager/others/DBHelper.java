/**
 * Created by devl on 4/1/17.
 */
package com.anew.devl.cardealershipmanager.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
    public static class VeiculoDBHelper implements BaseColumns {
        public static final String TABLE_NAME = "veiculo";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_MARCA = "marca";
        public static final String COLUMN_NAME_ADICIONADO = "adicionado";
        public static final String COLUMN_NAME_PRECO = "preco";
        public static final String COLUMN_NAME_COMBUSTIVEL = "combustivel";

    }


    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + VeiculoDBHelper.TABLE_NAME + " (" +
                    VeiculoDBHelper._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    VeiculoDBHelper.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    VeiculoDBHelper.COLUMN_NAME_MARCA + TEXT_TYPE + COMMA_SEP +
                    VeiculoDBHelper.COLUMN_NAME_ADICIONADO + DATE_TYPE + COMMA_SEP +
                    VeiculoDBHelper.COLUMN_NAME_COMBUSTIVEL + TEXT_TYPE + COMMA_SEP +
                    VeiculoDBHelper.COLUMN_NAME_PRECO + DOUBLE_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + VeiculoDBHelper.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VeiculoDB.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * @input the 'preco' value that comes as an input
     * @return a double variable with the value inputted as a String
     * Ex.:
     * Format input "R$ 99.932,94" and returns double 99932.94
     * Format input "R$ 999,12" and returns double 999.12
     *
     * */
    public static double formatPrecoToSQLiteDouble(String input) {

        input = input.replaceAll("\\.", ""). //remove "."
                replaceAll("[A-Za-z]", "").  //remove letras (A-Z)
                replaceAll("\\$", "").       //remove "$"
                replaceAll(",", ".");        //substitui "," por "." para padrão do SQLite
        return Double.parseDouble(input.trim());
    }
}

