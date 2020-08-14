package extra;

import java.io.Serializable;
import java.util.Collection;

public class Campagne implements Serializable {

    private Long id;
    private String name;
    private String date;
    private String statut;
    private Demographie demographie;
    private Collection<Vaccination> vaccinations;
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
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public Demographie getDemographie() {
		return demographie;
	}
	public void setDemographie(Demographie demographie) {
		this.demographie = demographie;
	}
	public Collection<Vaccination> getVaccinations() {
		return vaccinations;
	}
	public void setVaccinations(Collection<Vaccination> vaccinations) {
		this.vaccinations = vaccinations;
	}

	public Campagne(Long id, String name, String date, String statut) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.statut = statut;
	}

	public Campagne() {
	}
}
