package studio.waterwell.villaapp.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Efecto Dopler on 02/05/2017.
 */

public class Usuario implements Parcelable {
    private String userName;
    private String password;
    // TODO: Meter un ArrayList de sitios visitados. Eso implicará modificar el parcelable

    public Usuario(String userName, String password){
        this.userName = userName;
        this.password = password;
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