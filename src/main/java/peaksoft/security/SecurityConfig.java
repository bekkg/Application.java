package peaksoft.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return (UserDetailsService) new UserDetailsServiceImpl();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize->
                authorize.requestMatchers("/").permitAll()
                        .requestMatchers("/add").permitAll()
                        .requestMatchers("/save").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/profile/{Id}").permitAll()
                        .requestMatchers("/update/{id}").permitAll()
                        .requestMatchers("/find-all").permitAll()
//                        .requestMatchers("/find-all").hasAnyAuthority("ADMIN","USER")
                        .anyRequest().authenticated())
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }
}
