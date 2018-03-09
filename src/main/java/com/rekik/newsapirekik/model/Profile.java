package com.rekik.newsapirekik.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String category;

    private String topic;

    @ManyToMany
    private List<AppUser> appusers;

    public void addAppUser(AppUser appUser){

        appusers.add(appUser);

    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

   public Profile(){
       appusers = new ArrayList<>();
   }

    public List<AppUser> getAppUsers() {
        return appusers;
    }

    public void setAppUsers(List<AppUser> appUsers) {
        this.appusers = appUsers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
