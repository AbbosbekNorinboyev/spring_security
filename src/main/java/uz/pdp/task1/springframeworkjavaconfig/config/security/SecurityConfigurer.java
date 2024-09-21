package uz.pdp.task1.springframeworkjavaconfig.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfigurer {
    private static final String[] WHITE_LIST = {
            "/css/*",
            "/auth/login",
            "/auth/register",
            "/home"
    };
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.csrf().disable();
        httpSecurity.userDetailsService(customUserDetailsService);

        httpSecurity.authorizeHttpRequests()
                .requestMatchers(WHITE_LIST).permitAll()
//                .requestMatchers("/admin").hasRole("ADMIN")
//                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                .anyRequest()
                .authenticated();

        httpSecurity.formLogin()
                .loginPage("/auth/login")
                .usernameParameter("uname")
                .passwordParameter("pswd")
                .defaultSuccessUrl("/home", false);

        httpSecurity.logout()
                .logoutUrl("/auth/logout")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"));

        httpSecurity.rememberMe()
                .rememberMeParameter("rememberMe")
                .rememberMeCookieName("rem-me")
                .tokenValiditySeconds(86400)
                .key("secret_key")
                .userDetailsService(customUserDetailsService);

        return httpSecurity.build();

        /*
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        {auth.requestMatchers("/css/*", "/auth/login").permitAll()
                                    .anyRequest()
                                    .authenticated();
                        })
                .formLogin(form -> {form.loginPage("/auth/login")
                                    .usernameParameter("uname")
                                    .passwordParameter("pswd")
                                    .defaultSuccessUrl("/home", false);
                        })
                .build();
         */
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()  // do not use in production
                .username("john@gmail.com")
                .password("2210")
                .roles("USER")
                .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("Abbos@gmail.com")
                .password("2210")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    */
}
