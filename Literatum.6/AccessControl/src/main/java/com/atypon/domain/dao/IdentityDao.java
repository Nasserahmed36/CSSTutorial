package com.atypon.domain.dao;

import com.atypon.domain.Identity;

import java.util.List;

public interface IdentityDao {
    boolean createIdentity(Identity identity);
    Identity getIdentity(String username);
    Identity getIdentity(String username, String password);
    boolean updateIdentity(Identity identity);
    boolean deleteIdentity(String username);
    List<Identity> getAll();
}
