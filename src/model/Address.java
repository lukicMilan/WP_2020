package model;

public class Address {
	String street;
	int number;
	String city;
	int zipCode;
	
	public Address() {
		super();
	}

	public Address(String street, int number, String city, int zipCode) {
		super();
		this.street = street;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address: " + street + " " + number + ", " + city + " " + zipCode;
	}
	
}
