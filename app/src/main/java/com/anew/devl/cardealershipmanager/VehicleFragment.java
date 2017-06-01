package com.anew.devl.cardealershipmanager;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.fipeclient.FipeSelectCarActivity;
import com.anew.devl.cardealershipmanager.fipeclient.FipeSelectMotoActivity;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.VeiculoGaragemAdapter;
import com.anew.devl.cardealershipmanager.others.DBHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by devl on 4/23/17.
 */

public class VehicleFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;
    static ListView lv;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Search in the database for Vehicles in Garage
        GaragemQueries.DBAsyncTask dbAsyncTask = new GaragemQueries.DBAsyncTask();
        dbAsyncTask.execute(getActivity().getBaseContext());

        List<Veiculo> veiculos = null;
        try {
            veiculos = dbAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (veiculos != null && !veiculos.isEmpty()) {


            View detailsFrame = getActivity().findViewById(R.id.details);
            lv = getListView();


            setListAdapter(new VeiculoGaragemAdapter(getActivity().getBaseContext(), veiculos));

            mDualPane = detailsFrame != null
                    && detailsFrame.getVisibility() == View.VISIBLE;


            if (savedInstanceState != null) {
                // Restore last state for checked position.
                mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
            }

            if (mDualPane) {
                Veiculo item = (Veiculo) getListView().getAdapter().getItem(mCurCheckPosition);

                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                showDetails(item, mCurCheckPosition);
            } else {

                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                getListView().setItemChecked(mCurCheckPosition, true);
            }
        } else {

            setListAdapter(new VeiculoGaragemAdapter(getActivity().getBaseContext(), new ArrayList<Veiculo>()));
            new AlertDialog.Builder(getActivity())
                    .setTitle("Não há veiculos cadastrados")
                    .setMessage("Que tipo de veículo deseja cadastrar?")
                    .setPositiveButton("Carros", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent();
                            intent.setClass(getActivity(), FipeSelectCarActivity.class);
                            intent.putExtra(FipeSelectCarActivity.PASSO_BUSCA, "1");
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("Motos", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setClass(getActivity(), FipeSelectMotoActivity.class);
                            intent.putExtra(FipeSelectMotoActivity.PASSO_BUSCA, "1");
                            startActivity(intent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        Veiculo item = (Veiculo) l.getAdapter().getItem(position);
        showDetails(item, position);
    }


    void showDetails(Veiculo item, int position) {
        mCurCheckPosition = position;


        if (mDualPane) {
            getListView().setItemChecked(position, true);

            DetailsFragment details = (DetailsFragment) getFragmentManager()
                    .findFragmentById(R.id.details);

            if (details == null || details.getVeiculoSelecionado() != item) {
                details = DetailsFragment.newInstance(item);


                android.app.FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                ft.replace(R.id.details, details);
                ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {


            Intent intent = new Intent();
            intent.setClass(getActivity(), VehicleDetailsFragment.class);
            intent.putExtra("item", item);
            startActivity(intent);
        }
    }

    //2 activities
    public static class VehicleDetailsFragment extends AppCompatActivity {


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_veiculo_show);

            setTitle("Garagem");

            /*
            Approach para usar VeiculoShowActivity layout na mesma tela!
            * */
            Button buttonCadastrar = (Button) findViewById(R.id.buttonCadastrar);

            buttonCadastrar.setText("Voltar");
            buttonCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Button buttonCancelar = (Button) findViewById(R.id.buttonCancelar);
            buttonCancelar.setText("Excluir");
            buttonCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = getIntent();
                    Veiculo veiculo = (Veiculo) in.getSerializableExtra("item");


                    DBHelper helper = new DBHelper(v.getContext());
                    helper.dropId(helper.getWritableDatabase(), veiculo.getId());


                    lv.deferNotifyDataSetChanged();
                    GaragemQueries.DBAsyncTask dbAsyncTask = new GaragemQueries.DBAsyncTask();
                    dbAsyncTask.execute(v.getContext());

                    List<Veiculo> veiculos = null;
                    try {
                        veiculos = dbAsyncTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    lv.setAdapter(new VeiculoGaragemAdapter(v.getContext(), veiculos));

                    finish();
                }
            });


            Intent in = getIntent();
            Veiculo veiculo = (Veiculo) in.getSerializableExtra("item");


            TextView textMarca = (TextView) findViewById(R.id.textMarca);
            textMarca.setText(veiculo.getMarca());


            TextView textNome = (TextView) findViewById(R.id.textName);
            textNome.setText(veiculo.getName());

            TextView textPreco = (TextView) findViewById(R.id.textPreco);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            textPreco.setText("R" + nf.format(veiculo.getPreco()));


            TextView textCombustivel = (TextView) findViewById(R.id.textCombustivel);
            textCombustivel.setText(veiculo.getCombustivel());

            TextView textAnoModelo = (TextView) findViewById(R.id.textAnoModelo);
            textAnoModelo.setText(veiculo.getAnoModelo());

            TextView textReferencia = (TextView) findViewById(R.id.textReferencia);
            textReferencia.setText(veiculo.getAdicionado());

            TextView textVeiculo = (TextView) findViewById(R.id.textVeiculo);
            textVeiculo.setText(veiculo.getIsCar() ? "Carros e Utilitários Pequenos" : "Motos e Motonetas");

            String dataAdicionado = veiculo.getAdicionado();
            try {
                //approach para transformar data SQLite para padrão comum no Brasil (dias/meses/anos)
                SimpleDateFormat spfDB = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat spfShow = new SimpleDateFormat("dd/MM/yyyy");
                Date dataDB = spfDB.parse(dataAdicionado);

                textReferencia.setText(spfShow.format(dataDB));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView textCodigoFipe = (TextView) findViewById(R.id.textCodFipe);
            textCodigoFipe.setText(veiculo.getFipe_codigo());

        }

    }

    public static class DetailsFragment extends Fragment {

        public static DetailsFragment newInstance(Veiculo item) {
            DetailsFragment f = new DetailsFragment();

            Bundle args = new Bundle();
            args.putSerializable("item", item);
            f.setArguments(args);

            return f;
        }

        public Veiculo getVeiculoSelecionado() {
            return (Veiculo) getArguments().get("item");
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View inflate = inflater.inflate(R.layout.activity_veiculo_show, null);

            Veiculo veiculo = getVeiculoSelecionado();
            configuraActivityShowVeiculo(inflate);

            TextView textMarca = (TextView) inflate.findViewById(R.id.textMarca);
            textMarca.setText(veiculo.getMarca());


            TextView textNome = (TextView) inflate.findViewById(R.id.textName);
            textNome.setText(veiculo.getName());

            NumberFormat brl = NumberFormat.getCurrencyInstance();
            TextView textPreco = (TextView) inflate.findViewById(R.id.textPreco);
            textPreco.setText(brl.format(veiculo.getPreco()));


            TextView textCombustivel = (TextView) inflate.findViewById(R.id.textCombustivel);
            textCombustivel.setText(veiculo.getCombustivel());


            TextView textReferencia = (TextView) inflate.findViewById(R.id.textReferencia);
            textReferencia.setText(veiculo.getAdicionado());

            TextView textAnoModelo = (TextView) inflate.findViewById(R.id.textAnoModelo);
            textAnoModelo.setText(veiculo.getAnoModelo());

            TextView textVeiculo = (TextView) inflate.findViewById(R.id.textVeiculo);
            textVeiculo.setText(veiculo.getIsCar() ? "Carros e Utilitários Pequenos" : "Motos e Motonetas");

            String dataAdicionado = veiculo.getAdicionado();
            try {
                //approach para transformar data SQLite para padrão comum no Brasil (dias/meses/anos)
                SimpleDateFormat spfDB = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat spfShow = new SimpleDateFormat("dd/MM/yyyy");
                Date dataDB = spfDB.parse(dataAdicionado);

                textReferencia.setText(spfShow.format(dataDB));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView textCodigoFipe = (TextView) inflate.findViewById(R.id.textCodFipe);
            textCodigoFipe.setText(veiculo.getFipe_codigo());


            return inflate;
        }
    }

    /*
    * Approach para usar veiculoShowActivity na listagem de garagem, usando inflate!
    * */
    private static void configuraActivityShowVeiculo(final View inflate) {

        if (inflate != null) {
            Button buttonCadastrar = (Button) inflate.findViewById(R.id.buttonCadastrar);
            buttonCadastrar.setEnabled(false);
            buttonCadastrar.setVisibility(View.GONE);

            Button buttonCancelar = (Button) inflate.findViewById(R.id.buttonCancelar);
            buttonCancelar.setText("Excluir");

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub

                    Log.v("long clicked", "pos: " + pos);
                    final long itemId = lv.getAdapter().getItemId(pos);

                    new AlertDialog.Builder(inflate.getContext())
                            .setTitle("Excluir veículo")
                            .setMessage("Tem certeza que deseja excluir este veículo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DBHelper helper = new DBHelper(inflate.getContext());
                                    helper.dropId(helper.getWritableDatabase(), itemId);

                                    refreshUIafterDelete(inflate);


                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                    return true;
                }
            });

        }
    }


    private static void refreshUIafterDelete(View view) {

        lv.deferNotifyDataSetChanged();
        GaragemQueries.DBAsyncTask dbAsyncTask = new GaragemQueries.DBAsyncTask();
        dbAsyncTask.execute(view.getContext());

        List<Veiculo> veiculos = null;
        try {
            veiculos = dbAsyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        lv.setAdapter(new VeiculoGaragemAdapter(view.getContext(), veiculos));

        TextView textMarca = (TextView) view.findViewById(R.id.textMarca);
        textMarca.setText("");

        TextView textNome = (TextView) view.findViewById(R.id.textName);
        textNome.setText("");

        TextView textPreco = (TextView) view.findViewById(R.id.textPreco);
        textPreco.setText("");

        TextView textCombustivel = (TextView) view.findViewById(R.id.textCombustivel);
        textCombustivel.setText("");

        TextView textReferencia = (TextView) view.findViewById(R.id.textReferencia);
        textReferencia.setText("");

        TextView textCodigoFipe = (TextView) view.findViewById(R.id.textCodFipe);
        textCodigoFipe.setText("");

        TextView textAnoModelo = (TextView) view.findViewById(R.id.textAnoModelo);
        textAnoModelo.setText("");

        TextView textVeiculo = (TextView) view.findViewById(R.id.textVeiculo);
        textVeiculo.setText("");

        Toast.makeText(view.getContext(), "Veículo deletado com sucesso", Toast.LENGTH_SHORT);
    }

    public void onBack(MenuItem view) {
        getActivity().finish();
    }
}