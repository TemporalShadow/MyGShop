package com.example.MyGShop.ui.shop;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.example.MyGShop.DownloadImageTask;
import com.example.proyectonavigationbar.R;
import com.example.MyGShop.Sql.SQLiteHelper;
import com.example.proyectonavigationbar.databinding.FragmentGameBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    View root;
    Juego game;

    private SQLiteHelper sql ;


    public GameFragment() {
        // Required empty public constructor
    }
    public GameFragment(Juego game) {
        this.game=game;
        // Required empty public constructor
    }

    public static GameFragment newInstance(String param1, String param2) {
        return new GameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sql=new SQLiteHelper(getContext(),"users",null ,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        ImageView imageView= (ImageView) root.findViewById(R.id.gameImageView);
        new DownloadImageTask((ImageView) imageView)
                .execute(game.getImage());
        TextView precio = root.findViewById(R.id.gamePriceTag);
        if(game.getPrecio()>=0)
        precio.setText("Precio: "+game.getPrecio()+"€");
        else precio.setText(0);
        // Inflate the layout for this fragment
        TextView nombre = root.findViewById(R.id.gameNameTag);
        nombre.setText(game.getName());
        TextView desc = root.findViewById(R.id.gameDescTag);

        Spinner spinner = (Spinner)root.findViewById(R.id.gameCategoriasSpinner);
        List<String> list = new ArrayList<>();
        list.add("Seleccionar");

        SQLiteDatabase db = sql.getWritableDatabase();
        if(db!=null){
            Cursor c = db.rawQuery("SELECT * FROM Categorias",null);
            if(c.moveToFirst()){
                do {
                    list.add(c.getString(1));
                }while (c.moveToNext());
            }
            c.close();
        }

        db.close();


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        //spinnerAdapter.add("DELHI");
        //spinnerAdapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                if(!selectedItem.equalsIgnoreCase("Seleccionar")){
                    SQLiteDatabase db = sql.getWritableDatabase();
                    if(db!=null){
                        Cursor c = db.rawQuery("SELECT * FROM Categorias where categoria_nombre=='"+selectedItem+"'",null);
                        if(c.moveToFirst()){
                            Cursor x = db.rawQuery("SELECT * FROM Relacion_CategoriasGames WHERE games_id=='"+game.getId()+"' AND categorias_id=='"+c.getString(0)+"'",null);
                                    if(x.moveToFirst()){
                                        Snackbar.make(view, "La categoria ya fue asignada a este juego", Snackbar.LENGTH_LONG)
                                                .setAction("User", null).show();
                                    }
                                    else{
                                        db.execSQL("INSERT INTO Relacion_CategoriasGames(games_id, categorias_id)" +
                                                " VALUES("+game.getId()+","+Integer.parseInt(c.getString(0))+")");
                                    }
                                    x.close();
                        }
                        c.close();
                    }

                    db.close();
                    showCategories();
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        if(!game.getDescripcion().isEmpty())
            desc.setText(game.getDescripcion());
        else
            desc.setText("No se ha proporcionado una descripcion");

        Button comprar= root.findViewById(R.id.buttonBuy);
        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationView navigation=getActivity().findViewById(R.id.nav_view);
                //navigation.header
                View headerLayout = navigation.getHeaderView(0);
                TextView nombre = (TextView) headerLayout.findViewById(R.id.nombre);
                //System.out.println(nombre.getText().toString());
                if((nombre.getText().toString().isEmpty()||nombre.getText().toString().equals(getResources().getString(R.string.Disconnected)))){
                    Snackbar.make(view, "No puedes comprar si no estas conectado", Snackbar.LENGTH_LONG)
                            .setAction("User", null).show();
                }
                else{
                    SQLiteDatabase db = sql.getWritableDatabase();
                    if(db!=null){
                        int id_nombre;
                        Cursor c = db.rawQuery("SELECT user_id FROM Users where Users.user_nick=='"+nombre.getText().toString()+"'", null);
                        if(c.moveToFirst()){
                            id_nombre=Integer.parseInt(c.getString(0));
                            Cursor d = db.rawQuery("SELECT * FROM Relacion_UsersGames WHERE games_id=="+game.getId()+" AND users_id=="+id_nombre,null);
                            if(!d.moveToFirst()) {
                                db.execSQL("INSERT INTO Relacion_UsersGames (games_id, users_id)" +
                                        "VALUES(" + game.getId() + "," + id_nombre + ")");
                                System.out.println("llego");
                                Snackbar.make(view, "Has comprado el juego", Snackbar.LENGTH_LONG)
                                        .setAction("User", null).show();
                            }
                            else
                                Snackbar.make(view, "Ya tienes comprado el juego", Snackbar.LENGTH_LONG)
                                        .setAction("User", null).show();
                            d.close();
                        }
                        c.close();

                    }
                }
            }
        });
        showCategories();
        return root;
    }

    private void showCategories() {
        LinearLayout catLayout = root.findViewById(R.id.categoriasGameLayout);
        catLayout.removeAllViews();
        SQLiteDatabase db = sql.getWritableDatabase();
        if(db!=null){
            Cursor c = db.rawQuery("SELECT * FROM Relacion_CategoriasGames where games_id=='"+game.getId()+"'",null);
            if(c.moveToFirst()){
                do {
                    Cursor x = db.rawQuery("SELECT categoria_nombre FROM Categorias WHERE categoria_id=='"+c.getString(2)+"'",null);
                    if(x.moveToFirst()) {
                        TextView tx = new TextView(getContext());
                        tx.setText(x.getString(0));
                        tx.setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.cruz_clara, 0, 0, 0);
                        //eliminar categoria al pulsar la imagen
                        /*tx.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final int DRAWABLE_LEFT = 0;
                                final int DRAWABLE_TOP = 1;
                                final int DRAWABLE_RIGHT = 2;
                                final int DRAWABLE_BOTTOM = 3;

                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                    if(event.getRawX() >= (tx.getRight() - tx.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                        // your action here

                                        Snackbar.make(getView(), "Categoria eliminada", Snackbar.LENGTH_LONG)
                                                .setAction("User", null).show();
                                        return true;
                                    }
                                }
                                return false;
                            }
                        });*/
                        catLayout.addView(tx);
                    }
                }while (c.moveToNext());
            }
            c.close();
        }
        db.close();

    }

}