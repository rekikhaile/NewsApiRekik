package com.rekik.newsapirekik.model;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @ManyToMany()
    private Set<AppRole> roles;


    @ManyToMany(mappedBy = "appusers")
    private Set<Profile> profiles;

    @Transient //Equivalent to an ignore statement
    private PasswordEncoder encoder;


    @Transient //Equivalent to an ignore statement
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AppUser() {
        this.roles=new HashSet<>();
        this.profiles = new HashSet<>();
        encoder = passwordEncoder();
    }

    public AppUser( String username, @NotNull String password, AppRole role) {
        this.username = username;
        this.roles = new HashSet<>();
        this.profiles = new HashSet<>();
        addRole(role);
        encoder = passwordEncoder();
        setPassword(password);
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = encoder.encode(password);
        System.out.println("Password:"+this.password);
    }

    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }

    public void addRole(AppRole role)
    {
        this.roles.add(role);
    }

    /*public PasswordEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }*/
}
