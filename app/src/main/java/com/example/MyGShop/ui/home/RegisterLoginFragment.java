package com.example.MyGShop.ui.home;

import android.app.DatePickerDialog;
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
import com.example.proyectonavigationbar.databinding.FragmentRegisterLoginBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class RegisterLoginFragment extends Fragment {
    FragmentRegisterLoginBinding binding;

    User userObj;
    private SQLiteHelper sqlh ;

    View root;

    public RegisterLoginFragment() {
        // Required empty public constructor
    }

    public static RegisterLoginFragment newInstance(String param1, String param2) {
        RegisterLoginFragment fragment = new RegisterLoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqlh = new SQLiteHelper(getContext(),"users",null ,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterLoginBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        Integer[] ids = new Integer[]{R.drawable.bibliotecaclara, R.drawable.personaclara};
        Spinner images = root.findViewById(R.id.imageSpinner);
        SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(getContext(),
                ids);

        images.setAdapter(adapter);
        Button btnregistrar= root.findViewById(R.id.registerbnt);
        EditText user= root.findViewById(R.id.editUser);
        EditText pass= root.findViewById(R.id.editPass);
        TextView error = root.findViewById(R.id.Errores);

        TextView fechaNac = root.findViewById(R.id.editBirth);
        fechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int yy;
                int mm;
                int dd;
                if(fechaNac.getText().toString().isEmpty()) {
                    yy = calendario.get(Calendar.YEAR);
                    mm = calendario.get(Calendar.MONTH);
                    dd = calendario.get(Calendar.DAY_OF_MONTH);
                }else {
                    String fecha = fechaNac.getText().toString();
                    dd=Integer.parseInt(fecha.split("-")[0]);
                    mm=Integer.parseInt(fecha.split("-")[1])-1;
                    yy=Integer.parseInt(fecha.split("-")[2]);
                }


                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String fecha =  String.valueOf(dayOfMonth)+"-"+String.valueOf(monthOfYear+1)
                                +"-"+String.valueOf(year);
                        fechaNac.setText(fecha);

                    }
                }, yy, mm, dd);

                datePicker.show();
            }
        });

        EditText correo = root.findViewById(R.id.editEmail);
        EditText confirmarPass= root.findViewById(R.id.editPassCheck);
        EditText telefono = root.findViewById(R.id.editNumber);
        EditText apodo = root.findViewById(R.id.editApodo);

        btnregistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    error.setText("");
                    error.setVisibility(View.VISIBLE);
                    if(user.getText().toString().isEmpty()){
                        error.setText("•Introduzca un nombre de usuario. Sera el nombre para conectarte");
                    }else
                    if(!user.getText().toString().matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")){
                        error.setText(error.getText()+"\n•Nombre de usuario incorrecto.Debe tener entre 8 y 20 caracteres.\n No empezar ni terminar por . o _. Tampoco se permiten caracteres especiales. Y por ultimo no se permiten la repeticion seguida de 2 o mas . o _.");
                    }
                    if(apodo.getText().toString().isEmpty()){
                        error.setText(error.getText()+"\n•Introduzca un apodo para el usuario. Sera el nombre visible");
                    }
                    if(pass.getText().toString().isEmpty()){
                        error.setText(error.getText()+"\n•Introduzca la contraseña");
                    }
                    if(confirmarPass.getText().toString().isEmpty()){
                        error.setText(error.getText()+"\n•Vuelva a introducir la contraseña.");
                    }
                    if(correo.getText().toString().isEmpty()){
                        error.setText(error.getText()+"\n•Introduzca un correo.");
                    }
                    if(fechaNac.getText().toString().isEmpty()){
                        error.setText(error.getText()+"\n•Introduzca una fecha de nacimiento.");
                    }
                    if(telefono.getText().toString().isEmpty()){
                        error.setText(error.getText()+"\n•Introduzca un numero de telefono.");
                    }
                    if(error.getText().toString().isEmpty())
                        error.setVisibility(View.INVISIBLE);
                if(!(user.getText().toString().isEmpty()||pass.getText().toString().isEmpty())) {
                    SQLiteDatabase db = sqlh.getWritableDatabase();
                    if(db != null){
                        Cursor c = db.rawQuery("SELECT * FROM Users where user_name=='"+user.getText().toString()+"'", null);
                        if(c.moveToFirst()){
                            Snackbar.make(view, "El usuario ya existe", Snackbar.LENGTH_LONG)
                                    .setAction("User", null).show();
                            c.close();
                        }else{
                            userObj = new User(user.getText().toString(), pass.getText().toString(), apodo.getText().toString(), correo.getText().toString(), Integer.parseInt(telefono.getText().toString()), fechaNac.getText().toString(),  ids[images.getSelectedItemPosition()]);
                            c.close();
                            db.execSQL("INSERT INTO Users ( user_name , user_pass , user_nick, user_email, user_phone, user_birth, user_profile) "+
                                    "VALUES('"+userObj.getUser_name()+"','"+userObj.getUser_pass()+"','"+userObj.getUser_nick()+"','"+userObj.getUser_email()+"','"+userObj.getUser_phone()+"','"+userObj.getUser_birth()+"','"+userObj.getUser_profile()+"')");
                            db.close();
                            requireActivity().onBackPressed();

                            /*FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.registerloyout, frag);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack(null);
                            ft.commit();*/
                        }
                    }
                    db.close();

                }
                else{
                    Snackbar.make(view, "Por favor introduca los datos", Snackbar.LENGTH_LONG)
                            .setAction("Data", null).show();
                }


            }

        });
        // Inflate the layout for this fragment
        return root;
    }


}