package studio.waterwell.villaapp.Modelo;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nadrixa on 10/05/2017.
 */

public class Lugar {

    private String id;
    private String nombre;
    private String direccion;
    private LatLng coordenadas;
    private String descripcion;
    private String rutaImagen;
    private Bitmap imagen;

    public Lugar(String id, String nombre, String direccion, double latitud, double longitud, String descripcion, String rutaImagen) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenadas = new LatLng(latitud, longitud);
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LatLng getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(LatLng coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

}
