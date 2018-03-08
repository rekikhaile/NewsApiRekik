package com.rekik.newsapirekik.setup;

import com.rekik.newsapirekik.model.AppRole;
import com.rekik.newsapirekik.model.AppUser;
import com.rekik.newsapirekik.repository.AppRoleRepo;
import com.rekik.newsapirekik.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;



    @Override
    public void run(String... strings) throws Exception {
        //Add all data that should be in the database at the beginning of the application

        AppRole role = new AppRole();
        role.setRoleName("ADMIN");
        roleRepo.save(role);

        role = new AppRole();
        role.setRoleName("USER");
        roleRepo.save(role);

//Add test data for users

        AppUser user = new AppUser();
        user.setUsername("rekik");
        user.setPassword("pass");
        user.setFirstName("Rekik");
        user.setLastName("Haile");
        user.setImage("http://www.nurseryrhymes.org/nursery-rhymes-styles/images/john-jacob-jingleheimer-schmidt.jpg");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);


        user = new AppUser();
        user.setUsername("selam");
        user.setPassword("pass");
        user.setFirstName("Selam");
        user.setLastName("Samuel");
        user.setImage("http://www.nurseryrhymes.org/nursery-rhymes-styles/images/john-jacob-jingleheimer-schmidt.jpg");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);


        user = new AppUser();
        user.setUsername("admin");
        user.setPassword("pass");
        user.setFirstName("PotAdmin");
        user.setLastName("CoolGal");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        userRepo.save(user);




    }

}
