package com.example.agendacontactos.modelos;

public class Contacto {
    int id;
    String nombre,telefono,direccion,email,web,foto;

    public Contacto(int id, String nombre, String telefono, String direccion, String email, String web, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.web = web;
        this.foto = foto;
    }

    public Contacto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
