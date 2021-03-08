package uk.me.uohiro.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(value = "uk.me.uohiro.ssia.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		// エンドポイントレベルでのアクセス制御
		.authorizeRequests(a -> 
			a
				//　トップページにはアクセス制限を行わない
				.mvcMatchers("/", "/webjars/**").permitAll()
				// ボタンをクリックして遷移するページアクセスには、ロール「user」を持つユーザにのみアクセスを許可する
				// Keycloakでロールを小文字の「user」に設定しても、アクセストークンには「ROLE_USER」と大文字表現に変換されることに注意
				.mvcMatchers("/hello**").hasRole("USER")
				.mvcMatchers("/products").hasRole("USER")
				// すべてのアクセスには、ユーザ認証（とアクセストークンの取得）が要求される
				.anyRequest().authenticated())
		.exceptionHandling(e -> 
			e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
		.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
		.logout(l -> 
			l
				// ログアウトに成功した後の遷移先ページとしてトップページを指定する（このページにはアクセス制限を掛けない
				.logoutSuccessUrl("/").permitAll()
				// ログアウトエンドポイントを指定する
				.logoutUrl("/logout")
				// ログアウトしたときにHTTPセッションを無効化する
				.invalidateHttpSession(true)
				// ログアウトしたときに、HTTPセッションを特定するセッションIDをCookieから削除する
				// RP（Webアプリ）のCookieが削除されるだけであって、Keycloakセッションは有効な状態であるため、
				// Keycloak側では再ログインが要求されない
				.deleteCookies("JSESSIONID"))
		.sessionManagement(s ->
			s.
				// セッションタイムアウトした場合の遷移先（トップページに遷移させる）
				invalidSessionUrl("/")
		)
		.oauth2Login()
			// ログインした後の遷移先ページをトップページに設定する
			.defaultSuccessUrl("/");
	}
	
}
