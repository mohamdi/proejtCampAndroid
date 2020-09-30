package extra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collection;

public class Vaccination implements Serializable {

	@SerializedName("id")
	@Expose
	private Long id;
	@SerializedName("date_vaccination")
	@Expose
	private String date_vaccination;
	@SerializedName("longiude")
	@Expose
	private double longiude;
	@SerializedName("latitude")
	@Expose
	private double latitude;
	@SerializedName("nombre_enfant")
	@Expose
	private Integer nombre_enfant;
	@SerializedName("tranche_age")
	@Expose
	private String tranche_age;
	@SerializedName("campagne")
	@Expose
	private Campagne campagne;
	@SerializedName("moughataa")
	@Expose
	private Moughataa moughataa;
	@SerializedName("user")
	@Expose
	private AppUser user;
	@SerializedName("vaccin")
	@Expose
	private Vaccin vaccin;

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

	public Integer getNombre_enfant() {
		return nombre_enfant;
	}

	public void setNombre_enfant(Integer nombre_enfant) {
		this.nombre_enfant = nombre_enfant;
	}

	public String getTranche_age() {
		return tranche_age;
	}

	public void setTranche_age(String tranche_age) {
		this.tranche_age = tranche_age;
	}

	public Campagne getCampagne() {
		return campagne;
	}

	public void setCampagne(Campagne campagne) {
		this.campagne = campagne;
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

	public Vaccin getVaccin() {
		return vaccin;
	}

	public void setVaccin(Vaccin vaccin) {
		this.vaccin = vaccin;
	}

	public Vaccination(String date_vaccination, Moughataa moughataa) {
		this.date_vaccination = date_vaccination;
		this.moughataa = moughataa;
	}

	public Vaccination(Long id, String date_vaccination, double longiude, double latitude, int nombre_enfant, Campagne campagne, Moughataa moughataa, AppUser user) {
		this.id = id;
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre_enfant = nombre_enfant;
		this.campagne = campagne;
		this.moughataa = moughataa;
		this.user = user;
	}

	public Vaccination(Long id, String date_vaccination, double longiude, double latitude, int nombre_enfant, Campagne campagne,  Moughataa moughataa, AppUser user, String tranche_age) {
		this.id = id;
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre_enfant = nombre_enfant;
		this.campagne = campagne;
		this.moughataa = moughataa;
		this.user = user;
		this.tranche_age = tranche_age;
	}

	public Vaccination() {
	}

	// date, lon, lat, nbr, campagne, moughataa, user, trancheAge


	public Vaccination(String date_vaccination, double longiude, double latitude, Integer nombre_enfant, String tranche_age, Campagne campagne, Moughataa moughataa, AppUser user) {
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre_enfant = nombre_enfant;
		this.tranche_age = tranche_age;
		this.campagne = campagne;
		this.moughataa = moughataa;
		this.user = user;
	}

	public Vaccination(Long id, String date_vaccination, double longiude, double latitude, Integer nombre_enfant, String tranche_age, Campagne campagne, Moughataa moughataa, AppUser user, Vaccin vaccin) {
		this.id = id;
		this.date_vaccination = date_vaccination;
		this.longiude = longiude;
		this.latitude = latitude;
		this.nombre_enfant = nombre_enfant;
		this.tranche_age = tranche_age;
		this.campagne = campagne;
		this.moughataa = moughataa;
		this.user = user;
		this.vaccin = vaccin;
	}

}

