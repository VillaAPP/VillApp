package studio.waterwell.villaapp.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by Efecto Dopler on 02/05/2017.
 */

public class Usuario implements Parcelable {
    private String userName;
    private String password;
    private ArrayList<MiOpinion> opiniones;

    // TODO: Meter un ArrayList de sitios visitados. Eso implicará modificar el parcelable

    public Usuario(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.opiniones = new ArrayList<MiOpinion>();
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<MiOpinion> getOpiniones(){
        return opiniones;
    }

    public void addOpinion(MiOpinion opinion){
        opiniones.add(opinion);
    }

    protected Usuario(Parcel in) {
        userName = in.readString();
        password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}