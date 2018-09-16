package au.com.digitalspider.biblegame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceIds).tokenServices(tokenServices);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatchers().and().authorizeRequests()
				.requestMatchers(CorsUtils::isCorsRequest).permitAll()
				.antMatchers("/actuator/**", "/api-docs/**").permitAll()
				.antMatchers("/", "/oauth/token", "/login", "/register", "/index.html", "/swagger*/**",
						"/v1/user/login", "/v2/**",
						"/images/**", "/js/**", "/css/**", "/webjars/**", "/favicon.ico").permitAll()
				// .antMatchers("/v1/**").permitAll();
				.antMatchers("/v1/**").authenticated();
	}
}