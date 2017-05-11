package studio.waterwell.villaapp.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class Opinion implements Parcelable {
    private String userName;
    private int rate;
    private String opinion;

    public Opinion(String userName, int rate, String opinion) {
        this.userName = userName;
        this.rate = rate;
        this.opinion = opinion;
    }

    public String getUserName() {
        return userName;
    }

    public int getRate() {
        return rate;
    }

    public String getOpinion() {
        return opinion;
    }

    protected Opinion(Parcel in) {
        userName = in.readString();
        rate = in.readInt();
        opinion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeInt(rate);
        dest.writeString(opinion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Opinion> CREATOR = new Parcelable.Creator<Opinion>() {
        @Override
        public Opinion createFromParcel(Parcel in) {
            return new Opinion(in);
        }

        @Override
        public Opinion[] newArray(int size) {
            return new Opinion[size];
        }
    };
}
