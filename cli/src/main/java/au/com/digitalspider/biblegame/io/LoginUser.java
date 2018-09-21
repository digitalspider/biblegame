package au.com.digitalspider.biblegame.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class LoginUser {

	private String username;
	private String password;
	@JsonProperty("grant_type")
	@SerializedName("grant_type")
	private String grantType;

	public LoginUser() {

	}

	public LoginUser(String username, String password, String grantType) {
		this.username = username;
		this.password = password;
		this.grantType = grantType;
	}

	@Override
	public String toString() {
		return "LoginUser [username=" + username + ", password=" + password + "]";
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

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

}
