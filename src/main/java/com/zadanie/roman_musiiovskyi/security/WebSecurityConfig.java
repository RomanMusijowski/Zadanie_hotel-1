package com.zadanie.roman_musiiovskyi.security;


import com.zadanie.roman_musiiovskyi.repository.UserRepoJPA;
import com.zadanie.roman_musiiovskyi.security.services.SpringSecUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackageClasses = UserRepoJPA.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringSecUserDetailsServiceImpl userDetailsService;

    @Autowired
    protected void configureAuthManager(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().ignoringAntMatchers("/h2-console").disable()
                .authorizeRequests().antMatchers("/static/css") .permitAll()
                .and().authorizeRequests().antMatchers("/").authenticated()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/users").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PUT, "/users/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/users/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PATCH, "/users/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/users/{id}").hasAuthority("ADMIN")

                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/roles").hasAnyAuthority("ADMIN","STUDENT")
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/roles").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/roles/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PUT, "/roles/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PATCH, "/roles/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/roles/{id}").hasAuthority("ADMIN")

                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/rooms").hasAnyAuthority("ADMIN","STUDENT")
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/rooms").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/rooms/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PUT, "/rooms/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PATCH, "/rooms/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/rooms/{id}").hasAuthority("ADMIN")

                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/addtitives").hasAnyAuthority("ADMIN","STUDENT")
                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/addtitives").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/addtitives/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PUT, "/addtitives/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.PATCH, "/addtitives/{id}").hasAuthority("ADMIN")
                .and().authorizeRequests().antMatchers(HttpMethod.DELETE, "/addtitives/{id}").hasAuthority("ADMIN")

                .and().httpBasic();

    }
}
