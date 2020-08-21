package model;

public enum UserType {
	ADMINISTRATOR, HOST, GUEST;
	
	public UserType fromString(String userType) {
		switch (userType) {
			case "ADMINISTRATOR": 
				return UserType.ADMINISTRATOR;
			case "HOST":
				return UserType.HOST;
			case "GUEST": 
				return UserType.GUEST;
			default: 
				return UserType.GUEST;
		}
	}
}
