package studio.waterwell.villaapp.Modelo;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nadrixa on 10/05/2017.
 */

public class Lugar implements Parcelable {

    /*
    Atributos
     */

    private String id;
    private String nombre;
    private String direccion;
    private double latitud;
    private double longitud;
    private String descripcion;
    private String rutaImagen;
    private Bitmap imagen;

    /*
    Constructor
     */

    public Lugar() {

    }

    /*
    Funciones
     */

    public LatLng obtenerCoordenadas() {
        return new LatLng(latitud, longitud);
    }


    /*
    Getter & Setter
     */

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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


    protected Lugar(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        direccion = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
        descripcion = in.readString();
        rutaImagen = in.readString();
        imagen = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(direccion);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeString(descripcion);
        dest.writeString(rutaImagen);
        dest.writeValue(imagen);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Lugar> CREATOR = new Parcelable.Creator<Lugar>() {
        @Override
        public Lugar createFromParcel(Parcel in) {
            return new Lugar(in);
        }

        @Override
        public Lugar[] newArray(int size) {
            return new Lugar[size];
        }
    };
}