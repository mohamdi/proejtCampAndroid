package extra;

import java.io.Serializable;
import java.util.Collection;

public class Demographie implements Serializable {
    private Long id;
    private String name;
    private String date;
    private int annee;
    private Collection<Enquete> enquetes;
    private Collection<Campagne> campagnes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public Collection<Enquete> getEnquetes() {
		return enquetes;
	}
	public void setEnquetes(Collection<Enquete> enquetes) {
		this.enquetes = enquetes;
	}
	public Collection<Campagne> getCampagnes() {
		return campagnes;
	}
	public void setCampagnes(Collection<Campagne> campagnes) {
		this.campagnes = campagnes;
	}
    
}
