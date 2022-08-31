package software.sigma.sip.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import software.sigma.sip.domain.entity.Permission;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.GET, "/users").hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.POST, "/users").hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.PUT, "/users").hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.GET, "/users/**").hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.POST, "/wallets").hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.GET, "/wallets").hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.GET, "/wallets/**").hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.DELETE, "/wallets/**").hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.PUT, "/wallets/**").hasAuthority(Permission.WRITE.getPermission())
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(withDefaults());
        return http.build();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}
