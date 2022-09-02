package software.sigma.sip.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public static final String MONEY = "/wallets/money**";
    public static final String CURRENCY = "/currency**";
    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

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
                        .antMatchers(SWAGGER_WHITELIST).permitAll()
                        .antMatchers(HttpMethod.GET, USERS, WALLETS).hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.GET, CURRENCY).hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.POST, WALLETS).hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.POST, USERS).permitAll()
                        .antMatchers(HttpMethod.PUT, USERS, WALLETS, MONEY).hasAuthority(Permission.WRITE.getPermission())
                        .antMatchers(HttpMethod.DELETE, USERS).hasAuthority(Permission.READ.getPermission())
                        .antMatchers(HttpMethod.DELETE, WALLETS).hasAuthority(Permission.WRITE.getPermission())
                        .anyRequest()
                        .authenticated().and()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
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
