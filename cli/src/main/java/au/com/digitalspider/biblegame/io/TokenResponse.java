package au.com.digitalspider.biblegame.io;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
	// {"access_token":"eyJhbGciOiJIUzI1NiIsI...",
	// "token_type":"bearer",
	// "expires_in":43199,
	// "scope":"read write",
	// "jti":"99750440-17b1-4a89-b25b-e0fbf1744731"}

	@SerializedName("access_token")
	private String accessToken;
	@SerializedName("token_type")
	private String tokenType;
	@SerializedName("expires_in")
	private String expiresIn;
	private String scope;
	private String jti;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

}
