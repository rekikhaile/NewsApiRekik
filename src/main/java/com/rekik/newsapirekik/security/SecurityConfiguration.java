package com.rekik.newsapirekik.security;

import com.rekik.newsapirekik.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

  /*  @Autowired
    private SSUserDetailsService userDetailsService;*/
    @Autowired
    private AppUserRepo appUserRepo;

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(appUserRepo);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

      //  PasswordEncoder pE = passwordEncoder();
//        auth.inMemoryAuthentication().withUser("username").password(pE.encode("password")).authorities("USER")
//                .and().withUser("admin").password(pE.encode("password")).authorities("ADMIN");
//        auth.userDetailsService(userDetailsServiceBean());

        //Allows database authentication
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder( passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{



        http
                .authorizeRequests()

                //allow to all
                .antMatchers("/", "/imgriri/**",  "/test/**","/h2-console/**","/register","/css/**",
                        "/sass/**","/js/**","/img/**","/fonts/**","/landing/**","/bootstrap3/**").permitAll()
                //allowed only to recruiter
                //.antMatchers("/listlosts","/addlost", "/lostitem/**", "/edititem/**", "/deleteitem/**").hasAuthority("ADMIN")
                //allowed to User and Admin
                .antMatchers("/useraddtoprofile","/addtopic","/newspertopic","/removecat/**").access("hasAuthority('ADMIN') or hasAuthority('USER')")
                //.antMatchers("/useraddlost"). hasAuthority("USER")

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll().permitAll()
                .and()
                .httpBasic();

        //For H2
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();



    }
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception{

        PasswordEncoder pE = passwordEncoder();

        auth.inMemoryAuthentication()
                .withUser("username").password("pass").authorities("ADMIN");
        auth
                .userDetailsService(userDetailsServiceBean());
    }
*/






}
