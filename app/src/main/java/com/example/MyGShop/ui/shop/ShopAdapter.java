package com.example.MyGShop.ui.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.MyGShop.DownloadImageTask;
import com.example.proyectonavigationbar.R;

import java.util.List;

public class ShopAdapter extends ArrayAdapter<Juego> {
    private List<Juego> objects;
    public ShopAdapter(Context context, List<Juego> objects) {
        super(context, 0, objects);
        this.objects=objects;
    }

    public List<Juego> getList(){return objects;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // ¿Existe el view actual?
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.shop_item,
                    parent,
                    false);
        }

        // Referencias UI.
        ImageView avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView title = (TextView) convertView.findViewById(R.id.tv_title);

        // Lead actual.
        Juego game = getItem(position);

        // Setup.
        new DownloadImageTask((ImageView) avatar)
                .execute(game.getImage());
        //Glide.with(getContext()).load(game.getImage()).into(avatar);
        name.setText(game.getName());
        title.setText(game.getDescripcion());

        return convertView;
    }
}
