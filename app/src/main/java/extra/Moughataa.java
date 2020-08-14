package extra;

import java.util.Collection;

public class Moughataa {

    private Long id;
    private String moughataaname;
    private Collection<Enquete> enquetes;
    private Wilaya wilaya;
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
