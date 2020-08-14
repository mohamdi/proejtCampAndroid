package extra;

import java.util.Collection;

public class AppRole {
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

    private Long id;
    private String roleName;
    
    public AppRole(String roleName) {
    	this.roleName = roleName;
    }
}
