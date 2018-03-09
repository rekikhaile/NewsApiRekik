package com.rekik.newsapirekik.repository;

import com.rekik.newsapirekik.model.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepo extends CrudRepository<AppRole, Long> {
    AppRole findAppRoleByRoleName(String roleName);
    //AppRole findByRole(String role);
}
