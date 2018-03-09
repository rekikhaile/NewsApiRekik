package com.rekik.newsapirekik.repository;

import com.rekik.newsapirekik.model.AppUser;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepo extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String s);
   AppUser findAppUserByUsername(String username);
    /*  Iterable <AppUser> findAllByUsernameIsContaining(String searchstring);*/

}
