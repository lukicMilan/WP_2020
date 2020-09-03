package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//Interfejs postoji samo kako bi forsirao serijalizaciju imena klasa
//pokusao sam na druge nacine, ovaj je jedini uspeo
@JsonTypeInfo(
        use=JsonTypeInfo.Id.CLASS,
        include=JsonTypeInfo.As.EXISTING_PROPERTY,
        property="class")
public interface WorkaroundInterface {
	@JsonProperty("class")
	public default String getClassName() {
		return this.getClass().getName();
	}
}
