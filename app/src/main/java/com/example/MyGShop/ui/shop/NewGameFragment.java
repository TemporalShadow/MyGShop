package com.example.MyGShop.ui.shop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.MyGShop.DownloadImageTask;
import com.example.proyectonavigationbar.R;
import com.example.proyectonavigationbar.databinding.FragmentNewGameBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
public class NewGameFragment extends Fragment {

    private static final int SELECT_PICTURE = 100;
    private FragmentNewGameBinding binding;
    private View root;

    private JuegosRepository repository;
    String Url;

    public NewGameFragment() {
        // Required empty public constructor
    }
    public NewGameFragment(JuegosRepository rep) {
        repository=rep;
        // Required empty public constructor
    }

    public static NewGameFragment newInstance() {
        NewGameFragment fragment = new NewGameFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewGameBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        Url= "https://cdn.akamai.steamstatic.com/steam/apps/752480/header.jpg?t=1643711030";

        EditText button1 = root.findViewById(R.id.gameImageData);
        button1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ImageView imageView = root.findViewById(R.id.gameImageView);
                try {
                    if(charSequence.length()!=0)
                        Url= charSequence.toString();
                    new DownloadImageTask((ImageView) imageView)
                            .execute(Url);
                    //openImageChooser();
                    //imageView.setImageDrawable(LoadImageFromWebOperations(editText.getText().toString()));
                } catch (Exception e) {
                    Url= "https://cdn.akamai.steamstatic.com/steam/apps/752480/header.jpg?t=1643711030";
                    try{
                        new DownloadImageTask((ImageView) imageView)
                                .execute(Url);
                    }catch (Exception ex){}
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Button button2 = root.findViewById(R.id.addNewGame);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText imagen=root.findViewById(R.id.gameImageData);
                ImageView imageView = root.findViewById(R.id.gameImageView);
                EditText nombre = root.findViewById(R.id.gameNameEdit);
                EditText desc = root.findViewById(R.id.gameDescEdit);
                EditText precio = root.findViewById(R.id.gamePriceEdit);
                if(!nombre.getText().toString().isEmpty()) {
                    try {

                        Bitmap bmp=null;
                        try {
                            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            //System.out.println(new Juego(nombre.getText().toString(), desc.getText().toString(), Url, null));
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            bmp.recycle();
                        }catch (Exception e){}
                        JuegosRepository.saveGame(new Juego(nombre.getText().toString(),desc.getText().toString(),Url,null,Integer.parseInt(precio.getText().toString())));

                        FragmentManager fm = getParentFragmentManager();
                        /*getActivity().finish();
                        startActivity(getActivity().getIntent());*/
                        /*int index = getParentFragmentManager().getBackStackEntryCount() - 1;
                        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                        String tag = backEntry.getName();
                        System.out.println(tag);*/
                        /*System.out.println("2:"+getParentFragmentManager().getBackStackEntryCount());

                        ShopFragment f = (ShopFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_shop);
                        System.out.println("a: "+f);

                        getParentFragmentManager().beginTransaction().add(R.id.shopFragment, f, Integer.toString(1));

                        if (f != null);
                        f.updateListView();
                        ((Spinner)ShopFragment.root.findViewById(R.id.ordenarSpinner)).setSelection(0);
*/
                        fm.popBackStack ();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else
                    Snackbar.make(view, "Introduzca el nombre como minimo", Snackbar.LENGTH_LONG)
                            .setAction("Pass", null).show();
            }
        });
        return root;
    }


    private int getCallerFragment(){
        FragmentManager fm = getParentFragmentManager();

        for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){

            Log.i(TAG, "Found fragment: " + fm.getBackStackEntryAt(entry).getId());
            Log.i(TAG, "count: " + fm.getBackStackEntryCount());
        }
        int count = getParentFragmentManager().getBackStackEntryCount();
        return fm.getBackStackEntryAt(count - 2).getId();
    }


    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    // Get the path from the Uri

                    ImageView imageView = root.findViewById(R.id.gameImageView);
                    imageView.setImageURI(null);
                    imageView.setImageURI(selectedImageUri);

                }
            }
        }
    }

    /*public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }*/
}