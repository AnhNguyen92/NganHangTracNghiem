package vn.com.multiplechoice.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import vn.com.multiplechoice.business.service.impl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String loginPage = "/fo/login";
        
        http.csrf().disable();
        http.authorizeRequests() //
                .antMatchers(loginPage, "/fo/signup", "/fo/forgot-password").permitAll() //
                .anyRequest().authenticated() //
                .and().formLogin().loginPage(loginPage) //
                .loginProcessingUrl("/fo/j_spring_security_login") //
                .failureUrl("/fo/login?error=true") //
                .defaultSuccessUrl("/fo/index", true) //
                .usernameParameter("username").passwordParameter("password") //
                .and() //
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/fo/logout")) //
                .logoutSuccessUrl(loginPage) //
                .and().exceptionHandling() //
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/css/**", "/imgs/**", "/js/**", "/scss/**", "/vendor/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
