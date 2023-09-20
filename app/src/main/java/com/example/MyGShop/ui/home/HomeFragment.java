package com.example.MyGShop.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.MyGShop.Sql.SQLiteHelper;
import com.example.proyectonavigationbar.R;
import com.example.proyectonavigationbar.databinding.FragmentHomeBinding;
import com.google.android.material.snackbar.Snackbar;

public class HomeFragment extends Fragment {

    private SQLiteHelper sqlh ;
    private View root;
    private FragmentHomeBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sqlh = new SQLiteHelper(getContext(),"users",null ,1);



        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();


        //final TextView textView = binding.textHome;
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        EditText user= root.findViewById(R.id.editUser);
        EditText pass= root.findViewById(R.id.editPass);
        Button btnLogin= root.findViewById(R.id.loginbnt);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(user.getText().toString().isEmpty()||pass.getText().toString().isEmpty())) {
                    SQLiteDatabase db = sqlh.getWritableDatabase();
                    if(db != null){
                        Cursor c = db.rawQuery("SELECT * FROM Users where user_name=='"+user.getText().toString()+"'", null);
                        if(c.moveToFirst()){
                            if(c.getString(2).equalsIgnoreCase(pass.getText().toString())){
                                Fragment frag = new LogedUserFragment(user.getText().toString());
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.loginlayout, frag);

                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                user.setText("");
                                pass.setText("");
//                                ActivityMainBinding b2 = ActivityMainBinding.inflate(getLayoutInflater());
//
//                                AppBarConfiguration mAppBarConfiguration;
//                                DrawerLayout drawer = b2.drawerLayout;
//                                NavigationView navigationView = b2.navView;
//                                // Passing each menu ID as a set of Ids because each
//                                // menu should be considered as top level destinations.
//                                mAppBarConfiguration = new AppBarConfiguration.Builder(
//                                        R.id.nav_loged, R.id.nav_library, R.id.nav_shop)
//                                        .setOpenableLayout(drawer)
//                                        .build();
//                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
//                                NavigationUI.setupActionBarWithNavController(, navController, mAppBarConfiguration);
//                                NavigationUI.setupWithNavController(navigationView, navController);
                                ft.commit();
                            }else{
                                Snackbar.make(view, "La contraseña del usuario es incorrecta", Snackbar.LENGTH_LONG)
                                        .setAction("Pass", null).show();
                            }
                        }else{
                            Snackbar.make(view, "El nombre de usuario no existe", Snackbar.LENGTH_LONG)
                                    .setAction("User", null).show();
                        }
                    }

                }
                else{
                    Snackbar.make(view, "Por favor introduzca los datos", Snackbar.LENGTH_LONG)
                       .setAction("Data", null).show();
                }
            }
        });
        Button btnregister = root.findViewById(R.id.registerbnt);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment frag = new RegisterLoginFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.loginlayout, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                user.setText("");
                pass.setText("");
                ft.commit();
            }
        });
        /*SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Users", null);
            if (c.moveToFirst()) {
                do {
                    System.out.println("user:"+c.getString(0)+" , conectado:"+c.getString(8));
                }while (c.moveToNext());
            }
            c.close();
        }
        if(db != null) {
            Cursor c = db.rawQuery("SELECT user_name FROM Users where user_conectado=='" + 1 + "'", null);
            System.out.println("si");
            if (c.moveToFirst()) {
                System.out.println("no");
                Fragment frag = new LogedUserFragment(c.getString(0));
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.loginlayout, frag);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                c.close();
                db.close();
                ft.commit();
            }
            c.close();
        }
        db.close();
*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}