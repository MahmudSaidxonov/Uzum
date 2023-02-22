package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import org.postgresql.Driver;
import uz.nt.uzumproject.dto.ResponseDto;
import uz.nt.uzumproject.security.JwtFilter;
import uz.nt.uzumproject.service.UsersService;
import uz.nt.uzumproject.service.validator.AppStatusCodes;


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;


    @Autowired
    @Lazy
    private UsersService usersService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private Gson gson;

    //    JdbcUserDetailsManager
    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(usersService);

        return provider;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(Driver.class);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        return dataSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .requestMatchers("/user/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling(e -> e.authenticationEntryPoint(entryPoint()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    private AuthenticationEntryPoint entryPoint() {
        return ((req, res, exp) -> {
            res.getWriter().println(gson.toJson(ResponseDto.builder()
                    .message("Token is not valid: " + exp.getMessage() +
                            (exp.getCause() != null ? exp.getCause().getMessage() : ""))
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build()));
            res.setContentType("application/json");
            res.setStatus(400);
        });
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}















//otamnanqolgandalalar