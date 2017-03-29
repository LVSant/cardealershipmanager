package com.anew.devl.cardealershipmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anew.devl.cardealershipmanager.POJO.Modelo;

import java.util.List;

public class ModeloAdapter extends BaseAdapter {

    Context context;
    List<Modelo> data;
    private static LayoutInflater inflater = null;

    public ModeloAdapter(Context context, List<Modelo> Modelos) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = Modelos;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Modelo getItem(int position) {


        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return data.get(position).getIdModelo();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.item_modelo, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(data.get(position).getName());
        return vi;
    }


}