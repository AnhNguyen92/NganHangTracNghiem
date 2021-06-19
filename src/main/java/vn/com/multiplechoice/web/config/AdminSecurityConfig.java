//package vn.com.multiplechoice.web.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import vn.com.multiplechoice.business.service.impl.UserDetailsServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//@Order(2)
//public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//    
//    @Autowired
//    private BCryptPasswordEncoder encoder;
//    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//        .antMatchers("/bo/login").permitAll() //
//        .anyRequest().authenticated().and() //
//        
////        .hasRole("USER").and().formLogin()//
//                .loginProcessingUrl("/j_spring_security_login")//
//                .loginPage("/login2")//
//                .defaultSuccessUrl("/user")//
//                .failureUrl("/login2?message=error")//
//                .usernameParameter("username").passwordParameter("password").and().exceptionHandling().accessDeniedPage("/403").and().logout()
//                .logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login2?message=logout");
//    }
//
//}
