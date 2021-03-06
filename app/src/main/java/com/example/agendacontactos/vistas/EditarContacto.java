package com.example.agendacontactos.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agendacontactos.MainActivity;
import com.example.agendacontactos.R;
import com.example.agendacontactos.controladores.ContactosBD;
import com.example.agendacontactos.modelos.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarContacto extends AppCompatActivity {

    //TODO FOTO VISTA

    final int REQUEST_IMAGE_CAPTURE = 100;
    Uri imageUri, postStorage;

    String UriAnterior;
    ImageView ivEditarContacto;
    EditText etNombre,etTelefono,etEmail,etDireccion,etWeb;
    FloatingActionButton fbEditar;
    Contacto contacto;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contacto);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");

        ivEditarContacto = findViewById(R.id.ivEditarContacto);
        etNombre = findViewById(R.id.etNombreEContacto);
        etTelefono = findViewById(R.id.etTelefonoEContacto);
        etEmail = findViewById(R.id.etEmailEContacto);
        etDireccion = findViewById(R.id.etDireccionEContacto);
        etWeb = findViewById(R.id.etWebEContacto);
        fbEditar = findViewById(R.id.fbEditar);

        cargarVistaEditar();

        setup();
    }

    private void cargarVistaEditar() {

        ContactosBD cbd = new ContactosBD(getApplicationContext());
        contacto = cbd.getContacto(id);

        UriAnterior = contacto.getFoto();

        etNombre.setText(contacto.getNombre());
        etTelefono.setText(contacto.getTelefono());
        etEmail.setText(contacto.getEmail());
        etDireccion.setText(contacto.getDireccion());
        etWeb.setText(contacto.getWeb());
        Glide.with(getApplicationContext()).load(contacto.getFoto()).into(ivEditarContacto);

    }

    private void setup() {
        fbEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombre.getText().toString().equals("") || (etTelefono.getText().toString().equals("")&&etEmail.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Los campos mínimos (Nombre y (Telefono o Email)) no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                }else {
                    Contacto newContacto = new Contacto();
                    newContacto.setId(id);
                    newContacto.setNombre(etNombre.getText().toString());
                    if(!etTelefono.getText().toString().equals("")){
                        newContacto.setTelefono(etTelefono.getText().toString());
                    }
                    if (!etDireccion.getText().toString().equals("")) {
                        newContacto.setDireccion(etDireccion.getText().toString());
                    }
                    if (!etEmail.getText().toString().equals("")){
                        newContacto.setEmail(etEmail.getText().toString());
                    }
                    if(!etWeb.getText().toString().equals("")){
                        newContacto.setWeb(etWeb.getText().toString());
                    }
                    if(imageUri!=null){
                        newContacto.setFoto(imageUri.toString());
                    }else{
                        newContacto.setFoto(UriAnterior);
                    }
                    long correcto;
                    ContactosBD cbd = new ContactosBD(getApplicationContext());
                    MainActivity.recargarRecycler();
                    correcto=cbd.modificaContacto(newContacto);
                    if (correcto!=0){
                        Toast.makeText(getApplicationContext(), "Contacto "+contacto.getNombre()+" modificado", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Fallo al editar el Contacto prueba de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ivEditarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escogerFoto();
            }
        });
    }
    private void escogerFoto() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        try {

            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

        } catch(ActivityNotFoundException e){
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            assert data != null;

            if (postStorage == null){
                postStorage = Uri.EMPTY;
            }

            imageUri = data.getData();
            postStorage = imageUri;
            putImage(imageUri);

        } else {

            Toast.makeText(getApplicationContext(),"Imagen no seleccionada",Toast.LENGTH_LONG).show();

        }

    }


    private void putImage(Uri imageUri) {

        Glide.with(getApplicationContext()).load(imageUri).into(ivEditarContacto);

    }
}