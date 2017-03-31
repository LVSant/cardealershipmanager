package com.anew.devl.cardealershipmanager.fipeclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anew.devl.cardealershipmanager.POJO.Marca;
import com.anew.devl.cardealershipmanager.POJO.VeiculoAno;
import com.anew.devl.cardealershipmanager.R;

import java.util.List;

public class AnoVeiculoAdapter extends BaseAdapter {

    Context context;
    List<VeiculoAno> data;
    private static LayoutInflater inflater = null;

    public AnoVeiculoAdapter(Context context, List<VeiculoAno> veiculosAno) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = veiculosAno;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public VeiculoAno getItem(int position) {
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
            vi = inflater.inflate(R.layout.item_anoveiculo, null);
        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(data.get(position).getName());

        TextView text2 = (TextView) vi.findViewById(R.id.textsmall);
        text2.setText(data.get(position).getVeiculo());
        return vi;
    }


}