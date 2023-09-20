package com.example.MyGShop.ui.shop;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.MyGShop.Sql.SQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class JuegosRepository {
    private final List<Juego> games = new ArrayList<>();
    private static SQLiteHelper sqlh ;

    protected JuegosRepository(Context cont){
        sqlh = new SQLiteHelper(cont,"users",null ,1);
    }

    public static void saveGame(Juego game){
        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null){
            db.execSQL("INSERT INTO Games (games_nombre,games_description, games_fechaCreacion, games_image, games_price)"+
                    "VALUES('"+game.getName()+"','"+game.getDescripcion()+"','"+game.getFechaCreacion()+"','"+game.getImage()+"',"+game.getPrecio()+")");
            db.close();
        }

    }
    public void removeGame(int id){
        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null){
                Cursor c = db.rawQuery("SELECT * FROM Games where Games.games_id=='"+id+"'", null);
            if(c.moveToFirst()){
                //do {
                Cursor d= db.rawQuery("SELECT * FROM Relacion_CategoriasGames WHERE games_id=='"+id+"'",null);
                if (d.moveToFirst())
                do{
                     try {
                         db.execSQL("DELETE FROM Relacion_CategoriasGames WHERE games_id=='"+id+"'");
                     }catch (Exception e){e.printStackTrace();}
                }while (d.moveToNext());
                try {
                    //System.out.println(posicion);
                    db.execSQL("DELETE FROM Games WHERE Games.games_id=="+id+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                d.close();
                //}while (c.moveToNext());
            }
            c.close();
            db.close();
        }
        getGames();
    }

    public List<Juego> getGames(){
        games.clear();
        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null){
            Cursor c = db.rawQuery("SELECT * FROM Games", null);
            if(c.moveToFirst()){
                do {
                    try {
                        try{
                            Integer.parseInt(c.getString(5));
                            games.add(new Juego(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(4), null, Integer.parseInt(c.getString(5))));

                        }
                        catch (Exception exception) {
                            games.add(new Juego(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(4), null, 0));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (c.moveToNext());
            }
            c.close();
            db.close();
        }

        return games;
    }

    public List<Juego> OrdenarPorNombre(){
        Collections.sort(games, new Comparator<Juego>() {
            public int compare(Juego obj1, Juego obj2) {
                return obj1.getName().compareTo(obj2.getName());
            }
        });
        return games;
    }

    public List<Juego> OrdenarFecha(){
        return getGames();
    }

    public List<Juego> OrdenarFechaRevers(){
        List <Juego> fechas=getGames();
        Collections.reverse(fechas);
        return fechas;
    }
    public List<Juego> BuscarJuego(String busc){
        List<Juego> busqueda= new ArrayList<>();
        SQLiteDatabase db = sqlh.getWritableDatabase();
        if(db != null){
            Cursor c = db.rawQuery("SELECT * FROM Games WHERE instr(games_nombre,'"+busc+"')>0", null);
            if(c.moveToFirst()){
                do {
                    try {
                        try{
                            busqueda.add(new Juego(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(4), null, Integer.parseInt(c.getString(5))));

                        }
                        catch (Exception exception) {
                            busqueda.add(new Juego(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(4), null, 0));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (c.moveToNext());
            }
            c.close();
            db.close();
        }

        return busqueda;
    }

    public  List<Juego> OrdenarPorNombreRevers(){
        OrdenarPorNombre();
        Collections.reverse(games);
        return games;
    }
}
