package com.example.MyGShop.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.proyectonavigationbar.R;
import com.example.proyectonavigationbar.databinding.FragmentShopBinding;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;

public class ShopFragment extends Fragment {

    protected static View root;
    private ShopAdapter[] shopAdapter;
    private FragmentShopBinding binding;
    private JuegosRepository rep;
    private ListView lista;

    public static ShopFragment newInstance() {
        ShopFragment fragment = new ShopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Gets parámetros
        }

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShopBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        setRetainInstance(true);


        lista = (ListView) root.findViewById(R.id.listaShop);

        shopAdapter = new ShopAdapter[1];
        rep= new JuegosRepository(getContext());
        shopAdapter[0] = new ShopAdapter(getActivity(),rep.getGames());
        lista.setAdapter(shopAdapter[0]);
        lista.setLongClickable(true);
        lista.setClickable(true);
        Spinner ordenarSpinner = root.findViewById(R.id.ordenarSpinner);
        ordenarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position){
                    case 0:
                        shopAdapter[0] = new ShopAdapter(getActivity(),rep.OrdenarPorNombre());
                        break;
                    case 1:
                        shopAdapter[0] = new ShopAdapter(getActivity(),rep.OrdenarPorNombreRevers());
                        break;
                    case 2:
                        shopAdapter[0] = new ShopAdapter(getActivity(),rep.OrdenarFecha());
                        break;
                    case 3:
                        shopAdapter[0] = new ShopAdapter(getActivity(),rep.OrdenarFechaRevers());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + position);
                }
                lista.setAdapter(shopAdapter[0]);
                shopAdapter[0].notifyDataSetChanged();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //   .setAction("Action", null).show();
                Fragment fragment= new NewGameFragment(rep);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                shopAdapter[0].notifyDataSetChanged();
                fragmentTransaction.addToBackStack("fragA");
                fragmentTransaction.replace(R.id.shopFragment, fragment,"NewG");
                fragmentTransaction.commit();
                //rep.saveGame(new Juego("pp","Un gran juego", R.drawable.fondo_oscurito,null));
                ordenarSpinner.setSelection(0);
                /*lista.setAdapter(shopAdapter[0]);
                shopAdapter[0].notifyDataSetChanged();*/
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment= new GameFragment(shopAdapter[0].getList().get(i));
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                shopAdapter[0].notifyDataSetChanged();
                fragmentTransaction.addToBackStack("fragA");
                fragmentTransaction.replace(R.id.shopFragment, fragment);
                fragmentTransaction.commit();
                ordenarSpinner.setSelection(0);
            }
        });

        lista.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position, long id) {
                        Snackbar.make(view, "Juego Borrado", Snackbar.LENGTH_LONG)
                           .setAction("Borrado", null).show();

                        rep.removeGame(shopAdapter[0].getList().get(position).getId());
                        updateListView();
                        ordenarSpinner.setSelection(0);
                        //lista.setAdapter(shopAdapter[0]);*
                        return true;
                    }
                });
        /*final TextView textView = binding.textSlideshow;
        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/




        EditText editTextBuscar =root.findViewById(R.id.editSearch);
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=0){
                    shopAdapter[0] = new ShopAdapter(getActivity(),rep.BuscarJuego(charSequence.toString()));
                    lista.setAdapter(shopAdapter[0]);
                    shopAdapter[0].notifyDataSetChanged();
                }
                else{
                    shopAdapter[0] = new ShopAdapter(getActivity(),rep.getGames());
                    lista.setAdapter(shopAdapter[0]);
                    shopAdapter[0].notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        LayoutInflater layoutInflater = getLayoutInflater();
        View ll= layoutInflater.inflate(R.layout.fragment_new_game, null);
        Button btnAdd = (Button) ll.findViewById(R.id.addNewGame);
        //System.out.println(btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListView();
               // System.out.println("llego");
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume(){
        super.onResume();
        Spinner ordenarSpinner = root.findViewById(R.id.ordenarSpinner);
        ordenarSpinner.setSelection(0);
        //update your fragment
    }

    public void updateListView() {
        shopAdapter[0] = new ShopAdapter(getActivity(),rep.getGames());
        lista.setAdapter(shopAdapter[0]);
        shopAdapter[0].notifyDataSetChanged();
    }



}