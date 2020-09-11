package model;

public class Amenities {
	long id;
	String type;
	String name;
	boolean deleted = false;
	
	public Amenities() {
		super();
	}

	public Amenities(String type, String name, boolean deleted) {
		super();
		this.type = type;
		this.name = name;
		this.deleted = deleted;
	}
	
	public Amenities(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Amenities [id=" + id + ", type=" + type + ", name=" + name + "]";
	}
}
