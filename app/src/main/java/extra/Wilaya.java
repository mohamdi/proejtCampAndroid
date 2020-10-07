package extra;

import java.util.Collection;

public class Wilaya {

    private Long id;
    private String name;
    private Collection<Moughataa> moughataas;
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
	public Collection<Moughataa> getMoughataas() {
		return moughataas;
	}
	public void setMoughataas(Collection<Moughataa> moughataas) {
		this.moughataas = moughataas;
	}

	public Wilaya(Long id, String name){
		this.id = id;
		this.name = name;
	}
}
