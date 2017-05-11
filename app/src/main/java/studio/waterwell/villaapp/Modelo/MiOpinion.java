package studio.waterwell.villaapp.Modelo;

/**
 * Created by Efecto Dopler on 11/05/2017.
 */

public class MiOpinion {
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
}
