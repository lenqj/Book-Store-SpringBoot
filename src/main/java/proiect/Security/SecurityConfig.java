package proiect.Security;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import proiect.User.Repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authConfig -> {
                    authConfig.requestMatchers(HttpMethod.GET, "/", "/login", "/register", "/error", "/login-error", "/logout", "/css/**", "/js/**", "/images/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/", "/login", "/register", "/error", "/login-error", "/logout", "/css/**", "/js/**", "/images/**").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/categories", "/tags", "/books", "/books/detail").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/categories", "/tags", "/books", "/books/detail").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/cart", "/cart/add-to-cart", "/cart/remove-from-cart").permitAll();
                    authConfig.requestMatchers(HttpMethod.POST, "/cart", "/cart/add-to-cart", "/cart/remove-from-cart").permitAll();
                    authConfig.requestMatchers(HttpMethod.GET, "/admin", "/profile").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.POST, "/admin", "/profile").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin/books", "/admin/books/create", "/admin/books/delete", "/admin/books/add-tag", "/admin/books/update").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.POST, "/admin/books", "/admin/books/create", "/admin/books/delete", "/admin/books/add-tag", "/admin/books/update").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.PUT, "/admin/books", "/admin/books/create", "/admin/books/delete", "/admin/books/add-tag", "/admin/books/update").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin/categories", "/admin/categories/create", "/admin/categories/delete").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.POST, "/admin/categories", "/admin/categories/create", "/admin/categories/delete").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin/tags", "/admin/tags/create", "/admin/tags/delete").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.POST, "/admin/tags", "/admin/tags/create", "/admin/tags/delete").hasAnyAuthority("ADMIN", "USER");
                    authConfig.requestMatchers(HttpMethod.GET, "/admin/users", "/admin/users/create", "/admin/users/delete").hasAnyAuthority("ADMIN");
                    authConfig.requestMatchers(HttpMethod.POST, "/admin/users", "/admin/users/create", "/admin/users/delete").hasAnyAuthority("ADMIN");
                    authConfig.anyRequest().authenticated();

                })
                .formLogin(login -> {
                            login.loginPage("/login");
                            login.defaultSuccessUrl("/admin");
                            login.failureUrl("/login-error");
                        }
                )

                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.logoutSuccessUrl("/");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                });

        return http.build();
    }

    @Bean
    UserDetailsService myUserDetailsService(UserRepository userRepository) {
        return new MyUserDetailsService(userRepository);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successEvent() {
        return event -> {
            System.out.println("Success Login " + event.getAuthentication().getClass().getSimpleName() + " - " + event.getAuthentication().getName());
        };
    }

    @Bean
    ApplicationListener<AuthenticationFailureBadCredentialsEvent> failureEvent() {
        return event -> {
            System.err.println("Bad Credentials Login " + event.getAuthentication().getClass().getSimpleName() + " - " + event.getAuthentication().getName());
        };
    }
}
