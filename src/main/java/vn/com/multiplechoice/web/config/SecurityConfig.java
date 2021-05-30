//package vn.com.multiplechoice.web.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import vn.com.multiplechoice.business.service.MyUserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityEnablerConfiguration {
//
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests() //
//                .antMatchers("/").permitAll() //
//                .antMatchers("/login").permitAll() //
//                .antMatchers("/registration").permitAll() //
//                .antMatchers("/admin/**").hasAuthority("ADMIN") //
//                .anyRequest().authenticated() //
//                .and().csrf().disable() //
//                .formLogin().loginPage("/login") //
//                .failureUrl("/login?error=true").defaultSuccessUrl("/bo/user").usernameParameter("username").passwordParameter("password").and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").and().exceptionHandling()
//                .accessDeniedPage("/access-denied");
//    }
//
//    public void configure(WebSecurity web) {
//        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
