package com.rekik.newsapirekik.security;

import com.rekik.newsapirekik.model.AppRole;
import com.rekik.newsapirekik.model.AppUser;
import com.rekik.newsapirekik.repository.AppUserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {
    private AppUserRepo appUserRepo;
    public SSUserDetailsService(AppUserRepo appUserRepo){
        this.appUserRepo=appUserRepo;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            AppUser thisUser = appUserRepo.findAppUserByUsername(username);
            if (thisUser == null) {
               throw new UsernameNotFoundException("Invalid username or password");
               // return null;
            }
            else {

                System.out.println(thisUser.getUsername());
                System.out.println(thisUser.getPassword());
                System.out.println(thisUser.getRoles());
                return new org.springframework.security.core.userdetails.User(
                        thisUser.getUsername(),
                        thisUser.getPassword(),
                        getAuthorities(thisUser));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("user not found");

        }

    }
    private Set<GrantedAuthority> getAuthorities (AppUser user){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (AppRole appRole : user.getRoles()) {
            GrantedAuthority grantedAuthority
                    = new SimpleGrantedAuthority(appRole.getRoleName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}