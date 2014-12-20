package de.codecentric.moviedatabase.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import de.codecentric.moviedatabase.security.controller.LoginLogoutController;
import de.codecentric.moviedatabase.security.sso.CookieAuthenticationFilter;
import de.codecentric.moviedatabase.security.sso.CustomAuthenticationSuccessHandler;
import de.codecentric.moviedatabase.security.sso.CustomLogoutSuccessHandler;
import de.codecentric.moviedatabase.security.sso.SsoTokenRedisRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	// provided by spring-boot-starter-redis
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Bean
	public LoginLogoutController loginController(){
		return new LoginLogoutController();
	}

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN").and().withUser("user").password("user").roles("USER");
        UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper =
                new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(auth.getDefaultUserDetailsService());
        PreAuthenticatedAuthenticationProvider preAuthenticatedProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedProvider.setPreAuthenticatedUserDetailsService(wrapper);
        auth.authenticationProvider(preAuthenticatedProvider);
	}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CookieAuthenticationFilter cookieAuthenticationFilter = new CookieAuthenticationFilter(ssoTokenRedisRepository());
        cookieAuthenticationFilter.setAuthenticationManager(authenticationManager());
        cookieAuthenticationFilter.setCheckForPrincipalChanges(true);
        cookieAuthenticationFilter.setInvalidateSessionOnPrincipalChange(true);
        http
                .addFilter(cookieAuthenticationFilter) 
                .authorizeRequests()
                .antMatchers("/mappings/**","/env/**","/health/**","/metrics/**","/trace/**","/dump/**","/beans/**","/info/**","/autoconfig/**","/configprops/**","/logfile/**","/jolokia/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customAuth()) 
                .permitAll().and()
                .logout()
                .logoutSuccessHandler(customLogout()) 
                .logoutUrl("/logout")
                .and()
                .csrf().disable();
    }
    
    @Bean
    public SsoTokenRedisRepository ssoTokenRedisRepository(){
    	return new SsoTokenRedisRepository(redisTemplate);
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuth() {
        return new CustomAuthenticationSuccessHandler(ssoTokenRedisRepository());
    }

    @Bean
    public CustomLogoutSuccessHandler customLogout() {
        return new CustomLogoutSuccessHandler(ssoTokenRedisRepository());
    }


}
