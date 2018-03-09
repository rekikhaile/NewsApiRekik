package com.rekik.newsapirekik.repository;


import com.rekik.newsapirekik.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {


    List<Profile> findAll();
    List<Profile> findByAppusersIn(AppUser appUser);

/*
    List<Profile> findByAppUsersIn(AppUser appUser);
*/







}
