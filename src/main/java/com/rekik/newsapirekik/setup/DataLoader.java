package com.rekik.newsapirekik.setup;

import com.rekik.newsapirekik.model.AppRole;
import com.rekik.newsapirekik.model.AppUser;
import com.rekik.newsapirekik.repository.AppRoleRepo;
import com.rekik.newsapirekik.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    //Include a password encododer for data (using bcrypt hashing algorithm, but can use others)
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        //Add all data that should be in the database at the beginning of the application
//
//        AppRole role = new AppRole();
//        role.setRoleName("ADMIN");
//        roleRepo.save(role);
//
//        role = new AppRole();
//        role.setRoleName("USER");
//        roleRepo.save(role);

//Add test data for users

        System.out.println("Loading data into the application");

        System.out.println("Loading roles into the application");
        //Create new roles for the database
        AppRole aRole = new AppRole();
        aRole.setRoleName("ADMIN");
        roleRepo.save(aRole);

        aRole = new AppRole();
        aRole.setRoleName("USER");
        roleRepo.save(aRole);

       /* AppRole r = new AppRole("ADMIN");
        roleRepo.save(r);

        r = new AppRole("USER");
        roleRepo.save(r);*/



//        AppUser u = new AppUser("rekik","pass",roleRepo.findAppRoleByRoleName("USER"));
//        userRepo.save(u);
//
//        u = new AppUser("admin","pass",roleRepo.findAppRoleByRoleName("ADMIN"));
//        userRepo.save(u);

        //Create new users for the database
        System.out.println("Loading users into the application");
        AppUser user = new AppUser();
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("newuser");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);

        user = new AppUser();
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("adminuser");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        userRepo.save(user);

        user = new AppUser();
        user.setPassword(passwordEncoder.encode("password"));
        user.setUsername("ordinaryuser");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        userRepo.save(user);

    }

}
