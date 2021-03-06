package com.example.agendacontactos.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agendacontactos.modelos.Contacto;

import java.util.ArrayList;

public class ContactosBD extends SQLiteOpenHelper {

    public static final String NAME = "BD_AGENDA.db";
    private static final String NOMBRE_TABLA = "Contactos";
    public static int version = 3;

    private static final String ins = "CREATE TABLE Contactos (id INT PRIMARY KEY, nombre VARCHAR(100), " +
            "telefono VARCHAR(20), direccion VARCHAR(100), email VARCHAR(100), web VARCHAR(100), " +
            "foto VARCHAR(100))";

    public ContactosBD(Context context) {
        super(context, NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_TABLA);
        onCreate(db);
    }

    public long anadirContacto(Contacto contacto){
        SQLiteDatabase db = getWritableDatabase();
        long id = -1;

        if(db != null){
            ContentValues cv = new ContentValues();
            cv.put("id", cargarUltimoId());
            cv.put("nombre",contacto.getNombre());
            cv.put("telefono",contacto.getTelefono());
            cv.put("direccion",contacto.getDireccion());
            cv.put("email",contacto.getEmail());
            cv.put("web",contacto.getWeb());
            cv.put("foto",contacto.getFoto());
            id = db.insert("Contactos",null,cv);
        }
        db.close();
        return id;
    }

    public long borrarContacto(int id){
        long nLineas = 0;

        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            nLineas = db.delete("Contactos","id=" + id,null);
        }
        db.close();
        return nLineas;
    }

    public long modificaContacto(Contacto contacto) {
        SQLiteDatabase db = getWritableDatabase();
        long nLineas = 0;

        if(db != null){
            ContentValues cv = new ContentValues();
            cv.put("id", contacto.getId());
            cv.put("nombre",contacto.getNombre());
            cv.put("telefono",contacto.getTelefono());
            cv.put("direccion",contacto.getDireccion());
            cv.put("email",contacto.getEmail());
            cv.put("web",contacto.getWeb());
            cv.put("foto",contacto.getFoto());
            nLineas = db.update("Contactos", cv, "id="+contacto.getId(), null);
        }
        db.close();
        return nLineas;
    }
    
    public ArrayList<Contacto> cargarContactos(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
        Cursor cursor = db.rawQuery("SELECT * FROM Contactos ORDER BY nombre ASC , id ASC",null);
        while (cursor.moveToNext()){
            Contacto newContacto = new Contacto();
            newContacto.setId(cursor.getInt(0));
            newContacto.setNombre(cursor.getString(1));
            newContacto.setTelefono(cursor.getString(2));
            newContacto.setDireccion(cursor.getString(3));
            newContacto.setEmail(cursor.getString(4));
            newContacto.setWeb(cursor.getString(5));
            newContacto.setFoto(cursor.getString(6));

            listaContactos.add(newContacto);

        }
        return listaContactos;
    }

    public ArrayList<Contacto> cargarContactosBuscador(String string){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
        Cursor cursor = db.rawQuery("SELECT * FROM Contactos WHERE upper(nombre) LIKE '%"+string.toUpperCase()+"%' ORDER BY nombre ASC , id ASC",null);
        while (cursor.moveToNext()){
            Contacto newContacto = new Contacto();
            newContacto.setId(cursor.getInt(0));
            newContacto.setNombre(cursor.getString(1));
            newContacto.setTelefono(cursor.getString(2));
            newContacto.setDireccion(cursor.getString(3));
            newContacto.setEmail(cursor.getString(4));
            newContacto.setWeb(cursor.getString(5));
            newContacto.setFoto(cursor.getString(6));

            listaContactos.add(newContacto);

        }
        return listaContactos;
    }

    public Contacto getContacto(int id){
        SQLiteDatabase db = getReadableDatabase();
        Contacto newContacto = new Contacto();
        Cursor cursor = db.rawQuery("SELECT * FROM Contactos WHERE id = "+id,null);
        cursor.moveToFirst();

        newContacto.setId(cursor.getInt(0));
        newContacto.setNombre(cursor.getString(1));
        newContacto.setTelefono(cursor.getString(2));
        newContacto.setDireccion(cursor.getString(3));
        newContacto.setEmail(cursor.getString(4));
        newContacto.setWeb(cursor.getString(5));
        newContacto.setFoto(cursor.getString(6));

        return newContacto;
    }

    public int cargarUltimoId(){
        int newid;
        SQLiteDatabase db = getReadableDatabase();
        Cursor newcursor = db.rawQuery("SELECT MAX(id) FROM Contactos ORDER BY id ASC",null);
        newcursor.moveToFirst();
        newid = newcursor.getInt(0);

        return newid+1;
    }
}
