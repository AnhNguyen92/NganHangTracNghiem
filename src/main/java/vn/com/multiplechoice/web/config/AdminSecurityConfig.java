package vn.com.multiplechoice.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import vn.com.multiplechoice.business.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        String loginPage = "/bo/login";
        http.antMatcher("/bo/**") //
                .authorizeRequests() //
                .antMatchers(loginPage, "/bo/users/ajax/list").permitAll() //
                .antMatchers("/bo/users", "/bo/users/**", "/bo/questions/**").permitAll() //
                .anyRequest().hasAnyRole("ADMIN", "INSPECTOR") //
                .and().formLogin() //
                .loginPage(loginPage)//
                .loginProcessingUrl("/bo/j_spring_security_login") //
                .defaultSuccessUrl("/bo/users", true) //
                .usernameParameter("username").passwordParameter("password") //
                .and().exceptionHandling().accessDeniedPage("/403") //
                .and().logout() //
                .logoutUrl("/bo/j_spring_security_logout") //
                .logoutSuccessUrl(loginPage);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/css/**", "/imgs/**", "/js/**", "/scss/**", "/vendor/**");
    }

}
