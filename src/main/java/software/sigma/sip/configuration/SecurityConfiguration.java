package software.sigma.sip.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
public class SecurityConfiguration {
    public static final String USERS = "/users**";
    public static final String WALLETS = "/wallets**";

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(HttpMethod.GET, USERS, WALLETS).hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.POST, USERS, WALLETS).hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.PUT, USERS, WALLETS).hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.DELETE, USERS).hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.DELETE, WALLETS).hasAuthority(Permission.WRITE.getPermission())
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
