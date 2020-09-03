package model;

public enum UserGender {
	MALE, FEMALE, OTHER;
	
	public UserGender fromString(String userGender) {
		switch (userGender) {
		case "MALE": 
			return UserGender.MALE;
		case "FEMALE":
			return UserGender.FEMALE;
		case "OTHER":
			return UserGender.OTHER;
		default:
			return UserGender.OTHER;
		}
	}
}
