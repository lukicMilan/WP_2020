package dto;

public class ImagePathDTO {
	long apartmentId;
	String path;

	public ImagePathDTO() {
		super();
	}
	
	public long getApartmentId() {
		return apartmentId;
	}
	
	public void setApartmentId(long id) {
		this.apartmentId = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
