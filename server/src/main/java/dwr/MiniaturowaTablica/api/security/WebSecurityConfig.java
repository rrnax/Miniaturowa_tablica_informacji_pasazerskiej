package dwr.MiniaturowaTablica.api.security;

import dwr.MiniaturowaTablica.api.models.ERole;
import dwr.MiniaturowaTablica.api.security.jwt.AuthEntryPointJwt;
import dwr.MiniaturowaTablica.api.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
   private final UserDetailsService userDetailsService;

   private final AuthEntryPointJwt unauthorizedHandler;

   private final AuthTokenFilter authTokenFilter;

   private final PasswordEncoder passwordEncoder;

   @Bean
   public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder);

      return authProvider;
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return authConfig.getAuthenticationManager();
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests()
            .requestMatchers("/api/auth/**", "/api/test/**","/api/ztm/warszawa/**","/api/ztm/**","/api/ztm/gdansk/**").permitAll()
            .requestMatchers("/api/departures/**", "/api/user/all/**","/api/displays/**").hasAuthority(String.valueOf(ERole.ROLE_USER))
            .requestMatchers("/api/user/admin/**").hasAuthority(String.valueOf(ERole.ROLE_ADMIN))
            .and().logout().invalidateHttpSession(true).
            clearAuthentication(true).
            logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login?logout").permitAll();

      //http.csrf().disable().authorizeHttpRequests().requestMatchers("/**").permitAll();
//                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeHttpRequests(auth-> auth .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers("/api/test/**").permitAll().anyRequest().authenticated());


//    .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers("/api/test/**").permitAll()
//                .anyRequest().authenticated();

//    .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/token/**").permitAll()
//                .anyRequest().authenticated()
//        )
      http.authenticationProvider(authenticationProvider());

      http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);


      return http.build();
   }
}