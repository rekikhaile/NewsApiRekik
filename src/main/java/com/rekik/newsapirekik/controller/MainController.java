package com.rekik.newsapirekik.controller;

import com.rekik.newsapirekik.model.*;
import com.rekik.newsapirekik.repository.AppRoleRepo;
import com.rekik.newsapirekik.repository.AppUserRepo;
import com.rekik.newsapirekik.repository.ProfileRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    AppRoleRepo roleRepo;

    @Autowired
    AppUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ProfileRepository profileRepo;


    //for all users
    @GetMapping("/")
    public String showTopHeadLineArticles(Model model) {
        /*public @ResponseBody String showIndex(Model model){*/
        RestTemplate restTemplate = new RestTemplate();

        TopHeadline topHeadline = restTemplate.getForObject
                ("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=229cf848edc742dfa2fd35b816ef08e8", TopHeadline.class);

       /* for (int i = 0; i < topHeadline.getArticles().size(); i++) {
            System.out.println(topHeadline.getArticles().get(i).getTitle());
        }
*/

        model.addAttribute("topheadlinearticles", topHeadline.getArticles());


        return "topheadlinearticles";

        //return "landing";

    }

    @RequestMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("newuser", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    /*public String saveUser(@Valid @ModelAttribute("newuser")AppUser user,
                           BindingResult result, HttpServletRequest request, Model model)*/
    public String saveUser(@Valid @ModelAttribute("newuser") AppUser user,
                           BindingResult result, Model model) {
        String thePassword = user.getPassword();
        if (result.hasErrors()) {
            return "register";
        }
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        user.setPassword(passwordEncoder.encode(thePassword));
        userRepo.save(user);
        return "redirect:/login";

    }

    //users add interested topics and select interested categories to add to their profile
    @GetMapping("/useraddtoprofile")
    public String addtopictoprofile(Model model) {
        RestTemplate restTemplate = new RestTemplate();

        Publishers publishers = restTemplate.getForObject
                ("https://newsapi.org/v2/sources?apiKey=229cf848edc742dfa2fd35b816ef08e8", Publishers.class);

        //ArrayList<Source> sources = publishers.getSources();
        List<Source> sources = publishers.getSources();

        Set<String> categories = new HashSet<>();

        for (Source source : sources) {

            if (source.getCategory() != null) {
                categories.add(source.getCategory());
            }
        }

        model.addAttribute("categories", categories);
        model.addAttribute("profile", new Profile());

        return "cataddtoprofile";
    }

    @PostMapping("/useraddtoprofile")
    public String addTopicToProfile(@Valid Profile profile, BindingResult result,
                                    Model model, Authentication auth, HttpServletRequest request) {
        if (result.hasErrors()) {
            return "cataddtoprofile";
        }

        AppUser appuser = userRepo.findByUsername(auth.getName());
        profile.addAppUser(appuser);
        profileRepo.save(profile);

        //return "usersownpage";
        return "redirect:/newspertopic";
    }


    @GetMapping("/addtopic")
    public String addtopic(Model model) {

        model.addAttribute("profile", new Profile());

        return "addtopic";
    }

    @PostMapping("/addtopic")
    public String addtopic(@Valid Profile profile, BindingResult result,
                           Model model, Authentication auth, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "addtopic";
        }
        AppUser appUser = userRepo.findByUsername(auth.getName());
        profile.addAppUser(appUser);
        profileRepo.save(profile);

        //return "usersownpage";
        return "redirect:/newspertopic";


    }


    @GetMapping("/newspertopic")
    public String newsPerTopic(Model model, Authentication auth) {
        AppUser appuser = userRepo.findByUsername(auth.getName());

        List<Profile> categoryForUser = profileRepo.findByAppusersIn(appuser);

        RestTemplate restTemplate = new RestTemplate();

        Publishers publishers = restTemplate.getForObject("https://newsapi.org/v2/sources?apiKey=229cf848edc742dfa2fd35b816ef08e8", Publishers.class);

        //TopHeadline allarticlesinteresting = restTemplate.getForObject("https://newsapi.org/v2/top-headlines?q=bitcoin&apiKey=229cf848edc742dfa2fd35b816ef08e8", TopHeadline.class);

        List<Source> sources = publishers.getSources();
        List<Source> profilematchingsources = new ArrayList<>();
        Set<String> newsurl = new HashSet<>();

        for (Source source : sources) {
            for (Profile profile : categoryForUser) {
                if (source.getCategory().equals(profile.getCategory())) {

                    /*System.out.println(source.getCategory());*/

                    profilematchingsources.add(source);

                    newsurl.add(source.getUrl());
                }
            }
        }


        model.addAttribute("categoryforuser", categoryForUser);
        model.addAttribute("profilematchingsources", profilematchingsources);

        Set<String> topics = new HashSet<>();
        List<Article> articles = new ArrayList<>();
        List<Profile> profiles = profileRepo.findByAppusersIn(appuser);

        for(Profile profile: profiles){
           /* System.out.println(profile.getTopic());*/
            topics.add(profile.getTopic());
        }

        TopHeadline topHeadline;
        restTemplate = new RestTemplate();

        List<TopHeadline> topHeadlines = new ArrayList<>();

       /* for (String topic :
                topics) {
            topHeadlines.add(restTemplate.getForObject
                    ("https://newsapi.org/v2/top-headlines?q=\"+topic+\"&apiKey=229cf848edc742dfa2fd35b816ef08e8", TopHeadline.class));
        }*/

        for (String topic :
                topics) {
            topHeadlines.add(restTemplate.getForObject("https://newsapi.org/v2/top-headlines?q="+topic+"&apiKey=229cf848edc742dfa2fd35b816ef08e8", TopHeadline.class));
        }

       /* System.out.println("Riri checking sources matching profiles");
        for(int i=0; i<topHeadlines.size();i++){
            topHeadlines

        }*/

        model.addAttribute("topheadlines", topHeadlines);

        return "usersownpage";
    }

    @RequestMapping("/removecat/{id}")
    public String deleteCat(@PathVariable("id") long id){
        profileRepo.deleteById(id);
        return "redirect:/newspertopic";

    }

}
