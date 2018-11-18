package com.atypon.service;

import com.atypon.domain.Identity;

import java.util.List;

public interface IdentityService {
    boolean create(Identity identity);
    Identity get(String username);
    Identity get(String username, String password);
    boolean update(Identity identity);
    boolean delete(String username);
    List<Identity> getAll();
}
