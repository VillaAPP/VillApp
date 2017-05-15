package studio.waterwell.villaapp.Modelo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class MiOpinion implements Parcelable {
    private String id;
    private int rate;
    private String opinion;

    public MiOpinion(String id, int rate, String opinion){
        this.id = id;
        this.rate = rate;
        this.opinion = opinion;
    }

    public String getId(){
        return id;
    }

    public int getRate() {
        return rate;
    }

    public String getOpinion() {
        return opinion;
    }

    protected MiOpinion(Parcel in) {
        id = in.readString();
        rate = in.readInt();
        opinion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(rate);
        dest.writeString(opinion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MiOpinion> CREATOR = new Parcelable.Creator<MiOpinion>() {
        @Override
        public MiOpinion createFromParcel(Parcel in) {
            return new MiOpinion(in);
        }

        @Override
        public MiOpinion[] newArray(int size) {
            return new MiOpinion[size];
        }
    };
}
