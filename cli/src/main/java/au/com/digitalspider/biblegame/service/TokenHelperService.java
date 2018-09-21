package au.com.digitalspider.biblegame.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class TokenHelperService {
	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${oauth.resource:http://localhost:8080/api/v1}")
	private String baseUrl;
	@Value("${oauth.token:http://localhost:8080/oauth/token}")
	private String tokenUrl;

	public String getToken(String username, String password) {
		return getRestTemplate(username, password).getAccessToken().getValue();
	}

	public OAuth2RestTemplate getRestTemplate(String username, String password) {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setGrantType(grantType);
		resourceDetails.setAccessTokenUri(tokenUrl);

		// -- set the clients info
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(clientSecret);

		// -- set Resource Owner info
		resourceDetails.setUsername(username);
		resourceDetails.setPassword(password);

		return new OAuth2RestTemplate(resourceDetails);
	}

	/**
	 * This is a way of getting the token without Spring. Only Apache HttpClient.
	 * 
	 * @deprecated No longer used as using OAuth2RestTemplate
	 * 
	 * @param username
	 * @param password
	 * @return access token
	 */
	@Deprecated
	public String getTokenOld(String username, String password) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(tokenUrl);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("username", "test"));
			urlParameters.add(new BasicNameValuePair("password", "test"));
			urlParameters.add(new BasicNameValuePair("grant_type", "password"));
			httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
			UsernamePasswordCredentials creds = new UsernamePasswordCredentials(clientId, clientSecret);
			httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
			try (CloseableHttpResponse response = client.execute(httpPost)) {
				System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
					System.out.println(result);
					Gson gson = new Gson();
					Map<String, String> tokenResponse = gson.fromJson(result, Map.class);
					return tokenResponse.get("access_token");
				} else {
					System.err.println("response=" + response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
			return null;
		}
	}
}
