package extra;

import java.io.Serializable;
import java.util.Collection;

public class Vaccination implements Serializable {

    private Long id;
    private String date_vaccination;
    private double longiude,latitude;
    private String nombre;
    private Campagne campagne;
    private Collection<Enfant> enfants;
    private Moughataa moughataa;
    private AppUser user;
	private String vaccin;
    private String trancheAge;
	public String getTrancheAge() {
		return trancheAge;
	}

	public void setTrancheAge(String trancheAge) {
		this.trancheAge = trancheAge;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDate_vaccination() {
		return date_vaccination;
	}
	public void setDate_vaccination(String date_vaccination) {
		this.date_vaccination = date_vaccination;
	}
	public double getLongiude() {
		return longiude;
	}
	public void setLongiude(double longiude) {
		this.longiude = longiude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Campagne getCampagne() {
		return campagne;
	}
	public void setCampagne(Campagne campagne) {
		this.campagne = campagne;
	}
	public Collection<Enfant> getEnfants() {
		return enfants;
	}
	public void setEnfants(Collection<Enfant> enfants) {
		this.enfants = enfants;
	}
	public Moughataa getMoughataa() {
		return moughataa;
	}
	public void setMoughataa(Moughataa moughataa) {
		this.moughataa = moughataa;
	}
	public AppUser getUser() {
		return user;
	}
	public void setUser(AppUser user) {
		this.user = user;
	}

	public Vaccination(String date_vaccination, Moughataa moughataa) {
		this.date_vaccination = date_vaccination;
		this.moughataa = moughataa;
	}

	public Vaccination(Long id, String date_vaccination, double longiude, double latitude, String nombre, Campagne campagne, Collection<Enfant> enfants, Moughataa moughataa, AppUser user) {
		this.id = id;
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre = nombre;
		this.campagne = campagne;
		this.enfants = enfants;
		this.moughataa = moughataa;
		this.user = user;
	}

	public Vaccination(Long id, String date_vaccination, double longiude, double latitude, String nombre, Campagne campagne, Collection<Enfant> enfants, Moughataa moughataa, AppUser user, String trancheAge) {
		this.id = id;
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre = nombre;
		this.campagne = campagne;
		this.enfants = enfants;
		this.moughataa = moughataa;
		this.user = user;
		this.trancheAge = trancheAge;
	}

	public Vaccination() {
	}

	// date, lon, lat, nbr, campagne, moughataa, user, trancheAge
	public Vaccination(String date_vaccination, double longiude, double latitude, String nombre, Campagne campagne, Moughataa moughataa, AppUser user, String trancheAge) {
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre = nombre;
		this.campagne = campagne;
		this.moughataa = moughataa;
		this.user = user;
		this.trancheAge = trancheAge;
	}

	public String getVaccin() {
		return vaccin;
	}

	public void setVaccin(String vaccin) {
		this.vaccin = vaccin;
	}
}

