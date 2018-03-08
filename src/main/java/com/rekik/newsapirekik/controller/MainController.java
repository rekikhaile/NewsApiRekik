package com.rekik.newsapirekik.controller;

import com.rekik.newsapirekik.model.AppRole;
import com.rekik.newsapirekik.model.AppUser;
import com.rekik.newsapirekik.model.NewsRoot;
import com.rekik.newsapirekik.repository.AppRoleRepo;
import com.rekik.newsapirekik.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping("/")
    public @ResponseBody String showIndex(Model model){
        RestTemplate restTemplate = new RestTemplate();

        NewsRoot newsRoot= restTemplate.getForObject
                ("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=229cf848edc742dfa2fd35b816ef08e8",NewsRoot.class);

        for(int i=0; i<newsRoot.getArticles().size(); i++)
        {

            System.out.println(newsRoot.getArticles().get(i).getTitle());
            model.addAttribute("articloch",newsRoot.getArticles().get(i).getTitle());
        }

        return newsRoot.getArticles().get(2).getTitle();

    }

    @RequestMapping("/login")
    public String showLogin(Model model){
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register";
    }

    @PostMapping("/register")
    /*public String saveUser(@Valid @ModelAttribute("newuser")AppUser user,
                           BindingResult result, HttpServletRequest request, Model model)*/
    public String saveUser(@Valid @ModelAttribute("newuser")AppUser user,
                           BindingResult result, Model model)
    {
        String thePassword = user.getPassword();
        if(result.hasErrors())
        {
            return "register";
        }
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        user.setPassword(passwordEncoder.encode(thePassword));
        userRepo.save(user);
        return "redirect:/login";

       /* if(result.hasErrors()){
            System.out.println(result.toString());
            return "register";
        }

        else{
            //Create a new ordinary user
            model.addAttribute(user.getUsername()+" created");
            AppRole r = roleRepo.findAppRoleByRoleName("USER");
            userRepo.save(user);
            user.addRole(r);
            userRepo.save(user);
            return "redirect:/login";
        }*/
    }

}
