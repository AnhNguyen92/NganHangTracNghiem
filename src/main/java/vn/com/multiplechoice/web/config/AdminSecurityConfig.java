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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import vn.com.multiplechoice.business.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String INSPECTOR = "INSPECTOR";

    private static final String ADMIN = "ADMIN";

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
        String loginPage = "/bo/login";

        http.antMatcher("/bo/**") //
                .authorizeRequests() //
                .antMatchers(loginPage, "/bo/ajax/users/list").permitAll() //
                .antMatchers("/bo/**").hasAnyAuthority(ADMIN, INSPECTOR)
//                .antMatchers("/bo/users/**").hasAnyAuthority(ADMIN) //
//                .antMatchers("/bo/comments/**").hasAnyAuthority(ADMIN) //
//                .antMatchers("/bo/charts/**").hasAnyAuthority(ADMIN) //
//                .antMatchers("/bo/tests/**").hasAnyAuthority(ADMIN, INSPECTOR) //
//                .antMatchers("/bo/questions/**").hasAnyAuthority(ADMIN, INSPECTOR) //
//                .antMatchers("/bo/test-feedbacks/**").hasAnyAuthority(ADMIN, INSPECTOR) //
                .anyRequest().authenticated() //
                .and().formLogin() //
                .loginPage(loginPage)//
                .loginProcessingUrl("/bo/j_spring_security_login") //
                //.defaultSuccessUrl("/bo/users", true) //
             // .defaultSuccessUrl("/bo/tests", true) //
                .successHandler(successHandler())
                .usernameParameter("username").passwordParameter("password") //
                .and().exceptionHandling().accessDeniedPage("/403") //
                .and().logout() //
                .logoutUrl("/bo/j_spring_security_logout") //
                .logoutSuccessUrl(loginPage) //
                .and()
                .csrf().disable().cors();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/css/**", "/imgs/**", "/js/**", "/scss/**", "/vendor/**");
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new CustomSuccessHandler();
    }

}
