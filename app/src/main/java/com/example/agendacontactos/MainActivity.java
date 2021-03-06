package com.example.agendacontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.agendacontactos.controladores.ContactosAdapter;
import com.example.agendacontactos.controladores.ContactosBD;
import com.example.agendacontactos.modelos.Contacto;
import com.example.agendacontactos.vistas.AnadirActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar tbAgenda;
    static RecyclerView rvContactos;
    static ArrayList<Contacto> listaContactos;
    private static EditText etBuscador;
    static ContactosBD cbd;
    static Context context;
    static boolean borrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        cbd = new ContactosBD(getApplicationContext());

        listaContactos = new ArrayList<>();

        etBuscador = findViewById(R.id.etBuscador);
        tbAgenda = findViewById(R.id.tbAgenda);
        rvContactos = findViewById(R.id.rvContactos);

        recargarRecycler();

        setup();
    }

    private void setup() {
        tbAgenda.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if(i == R.id.bAdd){
                    Intent intent = new Intent(getApplicationContext(), AnadirActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!borrado) {
                    if (etBuscador.getText().toString().equals("")) {
                        recargarRecycler();
                    }else{
                        recargarRecyclerBuscador();
                    }
                }
                borrado = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onResume() {
        recargarRecycler();
        super.onResume();
    }

    public static void recargarRecycler() {
        listaContactos.clear();
        borrado= true;
        etBuscador.setText("");
        listaContactos = cbd.cargarContactos();
        rvContactos.setLayoutManager(new LinearLayoutManager(context));
        rvContactos.setAdapter(new ContactosAdapter(listaContactos));
    }

    public static void recargarRecyclerBuscador() {
        listaContactos.clear();
        listaContactos = cbd.cargarContactosBuscador(etBuscador.getText().toString());
        rvContactos.setLayoutManager(new LinearLayoutManager(context));
        rvContactos.setAdapter(new ContactosAdapter(listaContactos));
    }
}