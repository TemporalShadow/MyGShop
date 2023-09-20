package com.example.MyGShop.ui.home;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.MyGShop.Sql.SQLiteHelper;
import com.example.proyectonavigationbar.R;
import com.example.proyectonavigationbar.databinding.ActivityMainBinding;
import com.example.proyectonavigationbar.databinding.FragmentLogedUserBinding;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogedUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogedUserFragment extends Fragment {

    private String name;
    private User user;
    private SQLiteHelper sqlh ;
    private FragmentLogedUserBinding binding;

    public LogedUserFragment() {
        // Required empty public constructor
    }

    public LogedUserFragment(String name){
        this.name=name;
    }
    public static LogedUserFragment newInstance(String param1, String param2) {
        LogedUserFragment fragment = new LogedUserFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqlh = new SQLiteHelper(getContext(),"users",null ,1);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogedUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Inflate the layout for this fragment


        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null) {
            Cursor c = db.rawQuery("SELECT * FROM Users where user_name=='" + name + "'", null);
            if(c.moveToFirst()){
                //db.execSQL("UPDATE Users SET user_conectado=0");
                user= new User(Integer.parseInt(c.getString(0)),c.getString(1),c.getString(2),c.getString(3),c.getString(4),Integer.parseInt(c.getString(5)),c.getString(6),Integer.parseInt(c.getString(7)));
                //db.execSQL("UPDATE Users SET user_conectado=1 where user_name='"+user.getUser_name()+"'");
            }
            c.close();
        }
        db.close();

        Button btnlogout = root.findViewById(R.id.logoutbnt);

        NavigationView navigation=getActivity().findViewById(R.id.nav_view);
        View headerLayout = navigation.getHeaderView(0);
        TextView nombre = (TextView) headerLayout.findViewById(R.id.nombre);
        TextView correo = headerLayout.findViewById(R.id.correo);
        ImageView imagen = headerLayout.findViewById(R.id.imagen);
        nombre.setText(user.getUser_nick());
        correo.setText(user.getUser_email());
        imagen.setImageResource(user.getUser_profile());
        ImageView avatar = root.findViewById(R.id.imagenAvatar);
        avatar.setImageResource(user.getUser_profile());


        ////////
        /*ActivityMainBinding bindingActivity=ActivityMainBinding.inflate(getLayoutInflater());
        DrawerLayout drawer = bindingActivity.drawerLayout;
        NavigationView navigationView = bindingActivity.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_loged, R.id.nav_library, R.id.nav_shop, R.id.nav_settings, R.id.nav_categories)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
*/


        ////////

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombre.setText(R.string.Disconnected);
                correo.setText("");
                imagen.setImageResource(R.drawable.icono);
                //
                SQLiteDatabase db = sqlh.getWritableDatabase();
                if(db != null) {
                    db.execSQL("UPDATE Users SET user_conectado=0");
                }
                getActivity().onBackPressed();
            }
        });

        ((TextView)root.findViewById(R.id.nick)).setText(user.getUser_nick());
        ((TextView)root.findViewById(R.id.textUser)).setText(user.getUser_name());
        ((TextView)root.findViewById(R.id.textPhone)).setText(Integer.toString(user.getUser_phone()));
        ((TextView)root.findViewById(R.id.textEmail)).setText(user.getUser_email());
        System.out.println(user.getUser_birth());
        ((TextView)root.findViewById(R.id.textBirth)).setText(user.getUser_birth());


        return root;
    }
}