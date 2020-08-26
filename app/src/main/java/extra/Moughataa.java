package extra;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;

public class Moughataa {

	@SerializedName("id")
	@Expose
    private Long id;
	@SerializedName("moughataaname")
	@Expose
    private String moughataaname;
	@SerializedName("enquetes")
	@Expose
    private Collection<Enquete> enquetes;
	@SerializedName("wilaya")
	@Expose
    private Wilaya wilaya;
	@SerializedName("vaccinations")
	@Expose
    private Collection<Vaccination> vaccinations;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMoughataaname() {
		return moughataaname;
	}
	public void setMoughataaname(String moughataaname) {
		this.moughataaname = moughataaname;
	}
	public Collection<Enquete> getEnquetes() {
		return enquetes;
	}
	public void setEnquetes(Collection<Enquete> enquetes) {
		this.enquetes = enquetes;
	}
	public Wilaya getWilaya() {
		return wilaya;
	}
	public void setWilaya(Wilaya wilaya) {
		this.wilaya = wilaya;
	}
	public Collection<Vaccination> getVaccinations() {
		return vaccinations;
	}
	public void setVaccinations(Collection<Vaccination> vaccinations) {
		this.vaccinations = vaccinations;
	}

	public Moughataa(String moughataaname) {
		this.moughataaname = moughataaname;
	}

	public Moughataa() {
	}

	public Moughataa(Long id, String moughataaname) {
		this.id = id;
		this.moughataaname = moughataaname;
	}
}
