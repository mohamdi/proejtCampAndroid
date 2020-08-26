package extra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vaccin {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("nom_vaccin")
    @Expose
    private String nom_vaccin;
    @SerializedName("campagne")
    @Expose
    private Campagne campagne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_vaccin() {
        return nom_vaccin;
    }

    public void setNom_vaccin(String nom_vaccin) {
        this.nom_vaccin = nom_vaccin;
    }

    public Campagne getCampagne() {
        return campagne;
    }

    public void setCampagne(Campagne campagne) {
        this.campagne = campagne;
    }

    public Vaccin(Long id, String nom_vaccin, Campagne campagne) {
        this.id = id;
        this.nom_vaccin = nom_vaccin;
        this.campagne = campagne;
    }

    public Vaccin() {
    }
}

