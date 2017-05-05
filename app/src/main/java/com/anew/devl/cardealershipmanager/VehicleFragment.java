package com.anew.devl.cardealershipmanager;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.anew.devl.cardealershipmanager.POJO.Veiculo;
import com.anew.devl.cardealershipmanager.fipeclient.adapter.VeiculoGaragemAdapter;
import com.anew.devl.cardealershipmanager.others.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by devl on 4/23/17.
 */

public class VehicleFragment extends ListFragment {

    boolean mDualPane;
    int mCurCheckPosition = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Search in the database for Vehicles in Garage
        List<Veiculo> veiculos = new GaragemQueries().populateList(getActivity());
        setListAdapter(new VeiculoGaragemAdapter(getActivity(), veiculos));

        View detailsFrame = getActivity().findViewById(R.id.details);

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


    public static class VehicleDetailsFragment extends AppCompatActivity {


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_veiculo_show);


            /*
            Approach para usar VeiculoShowActivity layout na mesma tela!
            * */
            Button buttonCadastrar = (Button) findViewById(R.id.buttonCadastrar);
            buttonCadastrar.setEnabled(false);
            buttonCadastrar.setVisibility(View.GONE);

            Button buttonCancelar = (Button) findViewById(R.id.buttonCancelar);
            buttonCancelar.setText("Excluir");
            buttonCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EXCLUIR SAPORRA
                }
            });


            Intent in = getIntent();
            Veiculo veiculo = (Veiculo) in.getSerializableExtra("item");


            TextView textMarca = (TextView)findViewById(R.id.textMarca);
            textMarca.setText(veiculo.getMarca());


            TextView textNome = (TextView)findViewById(R.id.textName);
            textNome.setText(veiculo.getName());

            TextView textPreco = (TextView)findViewById(R.id.textPreco);
            textPreco.setText("R$ "+DBHelper.formatPrecoToSQLiteDouble(veiculo.getPreco().toString()));


            TextView textCombustivel = (TextView)findViewById(R.id.textCombustivel);
            textCombustivel .setText(veiculo.getCombustivel());


            TextView textReferencia = (TextView)findViewById(R.id.textReferencia);
            textReferencia.setText(veiculo.getAdicionado());

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

            TextView textCodigoFipe = (TextView)findViewById(R.id.textCodFipe);
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


            configuraActivityShowVeiculo(inflate);


            return inflate;
        }
    }

    /*
    * Approach para usar veiculoShowActivity na listagem de garagem, usando inflate!
    * */
    private static void configuraActivityShowVeiculo(View inflate) {

        if (inflate != null) {
            Button buttonCadastrar = (Button) inflate.findViewById(R.id.buttonCadastrar);
            buttonCadastrar.setEnabled(false);
            buttonCadastrar.setVisibility(View.GONE);

            Button buttonCancelar = (Button) inflate.findViewById(R.id.buttonCancelar);
            buttonCancelar.setText("Excluir");
            buttonCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EXCLUIR SAPOORRA
                }
            });
        }
    }
}