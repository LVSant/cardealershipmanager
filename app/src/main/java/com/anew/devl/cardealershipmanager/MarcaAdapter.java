package com.anew.devl.cardealershipmanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anew.devl.cardealershipmanager.POJO.Marca;

import java.util.List;

public class MarcaAdapter extends BaseAdapter {

    Context context;
    List<Marca> data;
    private static LayoutInflater inflater = null;

    public MarcaAdapter(Context context, List<Marca> marcas) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = marcas;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Marca getItem(int position) {
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
            vi = inflater.inflate(R.layout.item_marca, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(data.get(position).getName());
        return vi;
    }


}