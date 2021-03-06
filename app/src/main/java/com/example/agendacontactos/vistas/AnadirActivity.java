package com.example.agendacontactos.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agendacontactos.R;
import com.example.agendacontactos.controladores.ContactosBD;
import com.example.agendacontactos.modelos.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AnadirActivity extends AppCompatActivity {

    final int REQUEST_IMAGE_CAPTURE = 100;

    Uri imageUri, postStorage;

    EditText etNombre,etTelefono,etDireccion,etEmail,etWeb;
    ImageView ivFoto;
    FloatingActionButton fbAnadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        etNombre = findViewById(R.id.etNombreAContacto);
        etTelefono = findViewById(R.id.etTelefonoAContacto);
        etDireccion = findViewById(R.id.etDireccionAContacto);
        etEmail = findViewById(R.id.etEmailAContacto);
        etWeb = findViewById(R.id.etWebAContacto);
        ivFoto = findViewById(R.id.ivAnadirContacto);
        fbAnadir = findViewById(R.id.fbAnadir);

        setup();
    }

    private void setup() {
        fbAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactosBD cbd = new ContactosBD(getApplicationContext());
                if(etNombre.getText().toString().equals("") || (etTelefono.getText().toString().equals("")&&etEmail.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Los campos mínimos (Nombre y (Telefono o Email)) no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                }else {
                    Contacto newContacto = new Contacto();
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
                        newContacto.setFoto("https://upload.wikimedia.org/wikipedia/commons/b/b7/Google_Contacts_logo.png");
                    }
                    long correcto;
                    correcto=cbd.anadirContacto(newContacto);
                    if (correcto!=-1){
                        finish();
                        Toast.makeText(getApplicationContext(), "Contacto "+newContacto.getNombre()+" creado correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Fallo al crear el Contacto prueba de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ivFoto.setOnClickListener(new View.OnClickListener() {
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

            Toast.makeText(getApplicationContext(),"Imagen no seleccionada",Toast.LENGTH_SHORT).show();

        }

    }


    private void putImage(Uri imageUri) {

        Glide.with(getApplicationContext()).load(imageUri).into(ivFoto);

    }
}