package com.example.MyGShop.ui.library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.proyectonavigationbar.R;
import com.example.proyectonavigationbar.databinding.FragmentLibraryBinding;
import com.google.android.material.navigation.NavigationView;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        ListView lista = (ListView) root.findViewById(R.id.listaShop);

        NavigationView navigation=getActivity().findViewById(R.id.nav_view);
        //navigation.header
        View headerLayout = navigation.getHeaderView(0);
        TextView nombre = (TextView) headerLayout.findViewById(R.id.nombre);
        final LibraryAdapter libAdapter;
        JuegosPropiosRepository rep= new JuegosPropiosRepository(getContext());
        libAdapter = new LibraryAdapter(getActivity(),rep.getGames(nombre.getText().toString()));
        lista.setAdapter(libAdapter);

        return root;
    }
    protected String conectado () {
        View inflatedView2 = getLayoutInflater().inflate(R.layout.nav_header_main, null);
        TextView nombre = inflatedView2.findViewById(R.id.nombre);

        return nombre.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}