package au.com.digitalspider.biblegame.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsUtils;

import au.com.digitalspider.biblegame.service.UserService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserService userService;
	
	@Value("${security.signing-key}")
	private String signingKey;

	@Value("${security.encoding-strength}")
	private Integer encodingStrength;

	@Value("${security.security-realm}")
	private String securityRealm;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
		//auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.requestMatchers(CorsUtils::isCorsRequest).permitAll()
				.antMatchers("/", "/oauth/token", "/login", "/register", "/index.html", "/swagger*/**",
						"/v1/user/login", "/v2/**",
						"/images/**", "/js/**", "/css/**", "/webjars/**", "/favicon.ico")
				.permitAll().anyRequest().authenticated()
				.and()
//				.formLogin().loginPage("/login").loginProcessingUrl("/api/v1/user/login").and()
				.rememberMe().rememberMeCookieName("remember-me")
				.tokenValiditySeconds(14 * 24 * 60 * 60) // expired time = 14 days
				.tokenRepository(persistentTokenRepository()).and()
				.logout().permitAll().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().httpBasic().realmName(securityRealm);
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
		return tokenRepository;
	}

	private PasswordEncoder getPasswordEncoder() {
//		PasswordEncoder encoder = new ShaPasswordEncoder(encodingStrength);
		PasswordEncoder encoder = new BCryptPasswordEncoder(encodingStrength);
		return encoder;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signingKey);
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	public static void main(String[] args) {
		String pass = "test";
		WebSecurityConfig web = new WebSecurityConfig();
		System.out.println(web.getPasswordEncoder().encode(pass));
		System.out.println(web.getPasswordEncoder().matches("test", "$2a$10$cDZMXfbtDatzflm/TJuJ1.JSC/cT/J9iDMhiIBXH9A/pt0TKv.bHK"));
		System.out.println(web.getPasswordEncoder().matches("david", "$2a$10$Fcuf2RSrfv8L5yS2QeGjG.fTK9mZap9DAPPMM5/tA6H042oBcDXG2"));
	}
}