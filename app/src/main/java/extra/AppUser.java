package extra;

import java.util.ArrayList;
import java.util.Collection;

public class AppUser {

    private Long id;
    private String username;
    private String password;
    private boolean actived;
    private Collection<AppRole> roles=new ArrayList<>();
    private Collection<Vaccination> vaccinations;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActived() {
		return actived;
	}
	public void setActived(boolean actived) {
		this.actived = actived;
	}
	public Collection<AppRole> getRoles() {
		return roles;
	}
	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}
	public Collection<Vaccination> getVaccinations() {
		return vaccinations;
	}
	public void setVaccinations(Collection<Vaccination> vaccinations) {
		this.vaccinations = vaccinations;
	}

	public AppUser(Long id) {
		this.id = id;
	}
}
