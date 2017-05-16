package com.anew.devl.cardealershipmanager.fipeclient.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class VeiculoGaragemAdapter extends BaseAdapter {

    Context context;
    List<Veiculo> data;
    private static LayoutInflater inflater = null;


    public VeiculoGaragemAdapter(Context context, List<Veiculo> Veiculos) {
        // TODO Auto-generated constructor stub
        super();
        this.context = context;
        this.data = Veiculos;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Veiculo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.item_garagem, null);

        TextView textName = (TextView) vi.findViewById(R.id.textname);
        textName.setTypeface(null, Typeface.BOLD);
        textName.setText(data.get(position).getName());

        TextView textMarca = (TextView) vi.findViewById(R.id.textmarca);
        textMarca.setTypeface(null, Typeface.BOLD);
        textMarca.setText(data.get(position).getMarca());

//        TextView textPreco = (TextView) vi.findViewById(R.id.textpreco);
//        textPreco.setTypeface(null, Typeface.BOLD);
//        NumberFormat nf = NumberFormat.getCurrencyInstance();
//        textPreco.setText(nf.format(data.get(position).getPreco()));

//        TextView textCombustivel = (TextView) vi.findViewById(R.id.textCombustivel);
//        textCombustivel.setTypeface(null, Typeface.BOLD);
//        textCombustivel.setText(data.get(position).getCombustivel());

        TextView textAdicionado = (TextView) vi.findViewById(R.id.textadicionado);
        textAdicionado.setTypeface(null, Typeface.BOLD);
        String dataAdicionado = data.get(position).getAdicionado();

        try {
            //approach para transformar data SQLite para padrão comum no Brasil (dias/meses/anos)
            SimpleDateFormat spfDB = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat spfShow = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDB = spfDB.parse(dataAdicionado);

            textAdicionado.setText(spfShow.format(dataDB));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return vi;
    }


}