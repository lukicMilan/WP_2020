package src.model;

public class Amenities {
	long id;
	String type;
	String name;
	
	public Amenities() {
		super();
	}

	public Amenities(long id, String type, String name) {
		super();
		this.id = id;
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

	@Override
	public String toString() {
		return "Amenities [id=" + id + ", type=" + type + ", name=" + name + "]";
	}
}
