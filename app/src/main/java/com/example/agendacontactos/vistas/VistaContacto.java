package com.example.agendacontactos.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.agendacontactos.MainActivity;
import com.example.agendacontactos.R;
import com.example.agendacontactos.controladores.ContactosBD;
import com.example.agendacontactos.modelos.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VistaContacto extends AppCompatActivity {

    static int id;

    static Context context;
    static TextView tvNombre;
    static TextView tvTelefonoLLamar;
    static TextView tvEmail;
    static TextView tvWhatsMensaje;
    static TextView tvWeb;
    static TextView tvLocation;
    static ImageView ivVistaContacto;
    Button bBorrar;
    static Button bLlamar;
    static Button bMensaje;
    static LinearLayout llLlamar;
    static LinearLayout llEmail;
    static LinearLayout llWha;
    static LinearLayout llWeb;
    static Contacto contacto;
    View separadorEmail, separadorWha;
    FloatingActionButton fbEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_contacto);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");

        context= getApplicationContext();

        tvNombre = findViewById(R.id.tvNombreContacto);
        tvTelefonoLLamar = findViewById(R.id.tvTelefono);
        tvEmail = findViewById(R.id.tvEmail);
        tvWhatsMensaje = findViewById(R.id.tvWha);
        ivVistaContacto = findViewById(R.id.ivVistaContacto);
        bBorrar = findViewById(R.id.bBorrar);
        bLlamar = findViewById(R.id.bLlamar);
        bMensaje = findViewById(R.id.bMensaje);
        llLlamar = findViewById(R.id.llLlamar);
        llEmail = findViewById(R.id.llMail);
        llWha = findViewById(R.id.llWha);
        separadorEmail = findViewById(R.id.separadorEmail);
        separadorWha = findViewById(R.id.separadorWha);
        tvWeb = findViewById(R.id.tvWeb);
        llWeb = findViewById(R.id.llWeb);
        tvLocation = findViewById(R.id.tvLocation);
        fbEditar = findViewById(R.id.fbEditar);

        cargarVista();

        setup();
    }

    private void setup() {
        fbEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditarContacto.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        bMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",contacto.getTelefono(),null));
                startActivity(intent);
            }
        });
        bLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = contacto.getTelefono();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        llLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = contacto.getTelefono();

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        llEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+contacto.getEmail()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        llWha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String uri = "whatsapp://send?phone="+"34"+contacto.getTelefono()+"&text="+"";
                intent.setData(Uri.parse(uri));

                try {

                    startActivity(intent);

                } catch (android.content.ActivityNotFoundException ex) {

                    ex.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Imposible establecer la conexión por Whatsapp con el contacto",Toast.LENGTH_SHORT).show();

                }
            }
        });
        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactosBD cbd = new ContactosBD(getApplicationContext());
                cbd.borrarContacto(contacto.getId());
                MainActivity.recargarRecycler();
                finish();
            }
        });
        llWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                Uri uri = Uri.parse("https://"+contacto.getWeb());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "La página no es accesible o segura", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void cargarVista() {
        ContactosBD cbd = new ContactosBD(context);
        contacto = cbd.getContacto(id);

        tvNombre.setText(contacto.getNombre());
        tvTelefonoLLamar.setText("Llamar a "+contacto.getTelefono());
        tvEmail.setText(contacto.getEmail());
        tvWhatsMensaje.setText("Enviar Whatsapp a "+contacto.getNombre());
        tvWeb.setText(contacto.getWeb());
        tvLocation.setText(contacto.getDireccion());

        Glide.with(context).load(contacto.getFoto()).into(ivVistaContacto);

        if (contacto.getTelefono()==null){
            tvTelefonoLLamar.setText("Este contacto no tiene Teléfono");
            tvWhatsMensaje.setText("Este contacto no tiene Teléfono");
            llLlamar.setEnabled(false);
            llWha.setEnabled(false);
            bLlamar.setEnabled(false);
            bMensaje.setEnabled(false);
        }else{
            llLlamar.setEnabled(true);
            llWha.setEnabled(true);
            bLlamar.setEnabled(true);
            bMensaje.setEnabled(true);
        }
        if(contacto.getEmail()==null){
            tvEmail.setText("Este contacto no tiene Email");
            llEmail.setEnabled(false);
        }else{
            llEmail.setEnabled(true);
        }
        if(contacto.getWeb()==null){
            tvWeb.setText("Este contacto no tiene Web");
            llWeb.setEnabled(false);
        }else{
            llWeb.setEnabled(true);
        }
        if(contacto.getDireccion()==null){
            tvLocation.setText("Este contacto no tiene Dirección");
        }
    }

    @Override
    protected void onResume() {
        cargarVista();
        super.onResume();
    }
}