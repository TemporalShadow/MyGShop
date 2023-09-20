package com.example.MyGShop.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    //Sentencia SQL para crear la tabla de Usuarios

    String sqlCreate = "CREATE TABLE Categorias (categoria_id INTEGER PRIMARY KEY, categoria_nombre NUMERIC)";
    String sqlCreate5= "CREATE TABLE Relacion_UsersGames (relacion_id INTEGER PRIMARY KEY, games_id TEXT NOT NULL, users_id TEXT NOT NULL)";
    String sqlCreate4 = "CREATE TABLE Users (user_id INTEGER PRIMARY KEY, user_name NUMERIC NOT NULL, user_pass NUMERIC NOT NULL, user_nick NUMERIC NOT NULL, user_email NUMERIC NOT NULL, user_phone NUMERIC NOT NULL, user_birth NUMERIC NOT NULL, user_profile TEXT NOT NULL, user_conectado TEXT DEFAULT 0)";
    String sqlCreate2 = "CREATE TABLE Games (games_id INTEGER PRIMARY KEY,games_nombre NUMERIC NOT NULL,games_description NUMERIC , games_fechaCreacion DATE, games_image NUMERIC,games_price TEXT)";
    String sqlCreate3 = "CREATE TABLE Relacion_CategoriasGames (relacion_id INTEGER PRIMARY KEY, games_id TEXT NOT NULL,categorias_id TEXT NOT NULL)";
    public SQLiteHelper(Context contexto, String nombre,
                        CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        db.execSQL(sqlCreate4);
        db.execSQL(sqlCreate5);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int
            versionNueva) {
//NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
// eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
// Sin embargo lo normal será que haya que migrar datos de la tabla antigua
// a la nueva, por lo que este método debería ser más elaborado.
//Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Categorias");
        db.execSQL("DROP TABLE IF EXISTS Games");
        db.execSQL("DROP TABLE IF EXISTS Relacion_CategoriasGames");
        db.execSQL("DROP TABLE IF EXISTS Relacion_UsersGames");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL(sqlCreate);
        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        db.execSQL(sqlCreate4);
        db.execSQL(sqlCreate5);
    }
}
