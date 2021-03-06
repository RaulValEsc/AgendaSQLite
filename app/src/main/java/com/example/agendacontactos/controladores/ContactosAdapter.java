package com.example.agendacontactos.controladores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.agendacontactos.R;
import com.example.agendacontactos.modelos.Contacto;
import com.example.agendacontactos.vistas.VistaContacto;

import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder> implements View.OnClickListener{

    Context c;
    ArrayList<Contacto> listaContactos;

    public ContactosAdapter(ArrayList<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto,null,false);
        return new ContactoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ContactoViewHolder holder, final int position) {

        holder.asignarDatos(listaContactos.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c.getApplicationContext(), VistaContacto.class);
                intent.putExtra("id",listaContactos.get(position).getId());
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        ImageView ivItemContacto;
        Contacto con;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            c = itemView.getContext();

            ivItemContacto=itemView.findViewById(R.id.ivItemContacto);
            nombre=itemView.findViewById(R.id.tvItemNombre);
        }

        public void asignarDatos(Contacto contacto) {

            con = contacto;

            Glide.with(itemView.getContext()).load(contacto.getFoto()).into(ivItemContacto);

            nombre.setText(contacto.getNombre());

        }
    }
}
