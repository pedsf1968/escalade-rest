package com.dsf.escalade;


import com.dsf.escalade.config.JwtAuthenticationEntryPoint;
import com.dsf.escalade.config.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   private UserDetailsService userDetailsService;

   @Bean
   public BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Autowired
   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


   @Autowired
   private JwtRequestFilter jwtRequestFilter;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
         .authorizeRequests().antMatchers("/authenticate").permitAll()
            .anyRequest().authenticated().and()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      // Add a filter to validate the tokens with every request
      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
   }

   @Bean
   public AuthenticationManager customAuthenticationManager() throws Exception {
      return authenticationManager();
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
   }

}