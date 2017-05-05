package com.anew.devl.cardealershipmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GaragemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_garagem);

    }

//    private void init() {
//
//        idsExcluir = new ArrayList<>();
//
//        final Button bex = (Button) findViewById(R.id.btnExcluir);
//        bex.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                btnExcluir();
//            }
//        });
//
//        final ListView listview = (ListView) findViewById(R.id.listviewgaragem);
//        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        listview.setItemsCanFocus(false);
//
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //View v = listview.getChildAt(i);
//                CheckedTextView checkExcluir = (CheckedTextView) view.findViewById(R.id.checkExcluir);
//
//                if (checkExcluir.isChecked()) {
//
//                    Veiculo item = (Veiculo) adapterView.getItemAtPosition(i);
//                    idsExcluir.remove(item.getId());
//
//
//                    checkExcluir.setChecked(false);
//
//
//                } else {
//
//
//                    Veiculo item = (Veiculo) adapterView.getItemAtPosition(i);
//                    idsExcluir.add(item.getId());
//
//
//                    checkExcluir.setCheckMarkDrawable(R.mipmap.ic_delete);
//                    checkExcluir.setChecked(true);
//                }
//            }
//        });
//    }
//
//    public void btnExcluir() {
//        if (idsExcluir != null && !idsExcluir.isEmpty()) {
//            DBHelper helper = new DBHelper(getBaseContext());
//            helper.dropIds(helper.getWritableDatabase(), idsExcluir);
//
//            //atualiza listview
//            btnReadGaragem(null);
//
//            //avisa da exclusão
//            Toast.makeText(getBaseContext(),
//                    "Veículo(s) deletados com sucesso!",
//                    Toast.LENGTH_LONG).show();
//        }
//    }

//    public void btnReadGaragem(View view) {
//        populateList();
//    }





    public void onBack(View view) {
        this.finish();
    }
}
