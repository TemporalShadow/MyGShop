package com.example.MyGShop.ui.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.MyGShop.Sql.SQLiteHelper;
import com.example.MyGShop.ui.shop.Juego;

import java.util.ArrayList;
import java.util.List;


public class JuegosPropiosRepository {
    private List<Juego> games = new ArrayList<>();
    private SQLiteHelper sqlh ;

    protected JuegosPropiosRepository(Context cont){
        sqlh = new SQLiteHelper(cont,"users",null ,1);
    }

    protected void saveRelacion(Juego game){

        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null){
            db.execSQL("INSERT INTO Games (games_nombre,games_description, games_fechaCreacion, ganes_image)"+
                    "VALUES('"+game.getName()+"','"+game.getDescripcion()+"','"+game.getFechaCreacion()+"','"+game.getImage()+"')");
        }
        db.close();
    }

    public void delGame(int posicion){

    }
    public List<Juego> getGames(String nombre){
        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null){
            Cursor x = db.rawQuery("SELECT * FROM Users WHERE user_nick=='"+nombre+"'",null);
            //System.out.println("id..:"+x.getString(1));
            if(x.moveToFirst()){
                System.out.println("id..:"+x.getString(1));
                int id_nombre= Integer.parseInt(x.getString(0));
                x.close();
                Cursor c = db.rawQuery("SELECT games_id FROM Relacion_UsersGames WHERE users_id=="+id_nombre, null);
                if(c.moveToFirst()){
                    do {
                        //System.out.println("a");
                        Cursor y = db.rawQuery("SELECT * FROM Games WHERE games_id=="+c.getString(0)+"",null);
                        if(y.moveToFirst())
                        try{
                            System.out.println("b");
                            Integer.parseInt(c.getString(5));
                            games.add(new Juego(Integer.parseInt(y.getString(0)), y.getString(1), y.getString(2), y.getString(4), null, Integer.parseInt(y.getString(5))));

                        }
                        catch (Exception exception) {
                            System.out.println("c");
                            games.add(new Juego(Integer.parseInt(y.getString(0)), y.getString(1), y.getString(2), y.getString(4), null, 0));

                        }
                        y.close();
                    }while (c.moveToNext());
                }
                c.close();
            }

        }
        db.close();
        return games;
    }
}
