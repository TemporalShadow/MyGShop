package com.example.MyGShop.Options;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.MyGShop.Sql.SQLiteHelper;
import com.example.proyectonavigationbar.R;
import com.example.proyectonavigationbar.databinding.FragmentCategoriesBinding;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment {


    private SQLiteHelper sql;
    private FragmentCategoriesBinding binding;
    private View root;
    public CategoriesFragment() {
        // Required empty public constructor
    }
    public static CategoriesFragment newInstance(String param1, String param2) {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sql= new SQLiteHelper(getContext(),"users",null ,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        ActualizarTabla();
        EditText cat = root.findViewById(R.id.categoriesEdit);
        Button add = root.findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cat.getText().toString().isEmpty()){
                    SQLiteDatabase db = sql.getWritableDatabase();
                    if(db!=null){
                        Cursor c = db.rawQuery("SELECT * FROM Categorias WHERE categoria_nombre=='"+cat.getText().toString().toLowerCase()+"'",null);
                        if(!c.moveToFirst()) {
                            db.execSQL("INSERT INTO Categorias(categoria_nombre)" +
                                    "VALUES ('" + cat.getText().toString().toLowerCase() + "')");
                            ActualizarTabla();
                        }
                        else{
                            Snackbar.make(view, "Ya existe esta categoria", Snackbar.LENGTH_LONG)
                                    .setAction("User", null).show();
                        }
                        c.close();
                    }
                    db.close();
                    cat.setText("");
                }
                else{
                    Snackbar.make(view, "Introduzca el nombre de la categoria a introducir", Snackbar.LENGTH_LONG)
                            .setAction("User", null).show();
                }
            }
        });
        Button buscar = root.findViewById(R.id.searchButton);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cat.getText().toString().isEmpty()){
                    BuscarTabla();
                }
                else
                Snackbar.make(view, "Introduzca el nombre de la categoria a buscar", Snackbar.LENGTH_LONG)
                        .setAction("User", null).show();
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    public void BuscarTabla(){


        EditText cat = root.findViewById(R.id.categoriesEdit);
        SQLiteDatabase db = sql.getWritableDatabase();
//Si hemos abierto correctamente la base de datos
        if (db != null) {

            Cursor c = db.rawQuery("SELECT * FROM Categorias WHERE categoria_nombre=='"+cat.getText().toString().toLowerCase()+"'", null);
            //Log.e("TAG-BD","ENTRO BD");
            if (c.moveToFirst()) {
                int veces=0;

                TableLayout tl=(TableLayout)root.findViewById(R.id.tabla);
                tl.removeViews(1, Math.max(0, tl.getChildCount() - 1));
                do {
                    Cursor y = db.rawQuery("SELECT count(categorias_id) FROM Relacion_CategoriasGames WHERE categorias_id=="+c.getString(0),null);
                    if(y.moveToFirst()){
                        veces=Integer.parseInt(y.getString(0));
                    }
                    y.close();
                    TableRow tr1 = new TableRow(getContext());
                    tr1.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    // Passing values
                    TextView textview1 = new TextView(getContext());
                    TextView textview2 = new TextView(getContext());
                    String column1 = c.getString(1);
                    textview1.setText(column1);
                    String column2 = Integer.toString(veces);
                    textview2.setText(column2);
                    tr1.addView(textview1);
                    tr1.addView(textview2);

                    tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    // Do something Here with values
                } while (c.moveToNext());
            }else{
                Snackbar.make(getView(), "No se encontro la categoria", Snackbar.LENGTH_LONG)
                        .setAction("User", null).show();
            }
            c.close();
            //Cerramos la base de datos
            db.close();
        }

    }

    public void ActualizarTabla(){


        SQLiteDatabase db = sql.getWritableDatabase();
//Si hemos abierto correctamente la base de datos
        if (db != null) {

            Cursor c = db.rawQuery("SELECT * FROM Categorias", null);
            //Log.e("TAG-BD","ENTRO BD");
            if (c.moveToFirst()) {
                int veces=0;
                TableLayout tl=(TableLayout)root.findViewById(R.id.tabla);
                tl.removeViews(1, Math.max(0, tl.getChildCount() - 1));
                do {
                    Cursor y = db.rawQuery("SELECT count(*) FROM Relacion_CategoriasGames WHERE categorias_id=="+c.getString(0),null);
                    if(y.moveToFirst()){
                        veces=Integer.parseInt(y.getString(0));
                    }
                    y.close();
                    TableRow tr1 = new TableRow(getContext());
                    tr1.setLayoutParams(new TableRow.LayoutParams( TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    // Passing values
                    TextView textview1 = new TextView(getContext());
                    TextView textview2 = new TextView(getContext());
                    String column1 = c.getString(1);
                    textview1.setText(column1);
                    String column2 = Integer.toString(veces);
                    textview2.setText(column2);
                    tr1.addView(textview1);
                    tr1.addView(textview2);

                    tl.addView(tr1, new TableLayout.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                    // Do something Here with values


                } while (c.moveToNext());
            }
            c.close();
            //Cerramos la base de datos
            db.close();

        }

    }
}