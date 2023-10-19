package com.source.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.source.config.jwt.JwtAuthenticationEntryPoint;
import com.source.config.jwt.JwtAuthenticationFilter;

/**
 *  Written
 *  By
 *  Rabendra (SE)
 **/

@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Order(1)
	@Configuration
	public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

		public final String [] publicUrl= {
				"/api/tran/auth/login/**",
				"/api/tran/auth/register/**",
				"/api/tran/auth/verify/email/**",
				"/api/tran/auth/resend/email/verification/email/**",
				"/api/general/util/**",
				"/v2/api-docs",
				"/v3/api-docs",
				"/swagger-resources/**",
				"/swagger-ui/**"
		};

		@Autowired
	    private JwtAuthenticationEntryPoint unauthorizedHandler;

	    @Bean
	    public JwtAuthenticationFilter jwtAuthenticationFilter() {
	        return new JwtAuthenticationFilter();
	    }

	    @Bean
		@Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
	            .cors()
	            .configurationSource(cors())
	            .and()
	            .csrf()
	            .disable()
	            .antMatcher("/api/**")
	            .exceptionHandling()
	            .authenticationEntryPoint(unauthorizedHandler)
	            .and()
	            .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()

	            .authorizeRequests()
	            .antMatchers(publicUrl)
	            .permitAll()
	            .anyRequest()
	            .authenticated();

			    // Add our custom JWT security filter
			    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
			    http.exceptionHandling().accessDeniedHandler(null);

		}


		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
		}

		private CorsConfigurationSource cors() {
		      CorsConfiguration config = new CorsConfiguration();
		      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		      config.addAllowedOrigin("*");
		      config.addAllowedMethod("*");
		      config.addAllowedHeader("Content-Type");
		      config.addAllowedHeader("Authorization");
		      config.addAllowedHeader("responseType");
		      config.addAllowedHeader("X-Requested-With");
		      source.registerCorsConfiguration("/**", config);
		      return source;
		}
	} // End API security config


}
