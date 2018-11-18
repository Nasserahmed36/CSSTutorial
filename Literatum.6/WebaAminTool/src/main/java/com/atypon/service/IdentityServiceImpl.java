package com.atypon.service;

import com.atypon.domain.Identity;
import com.atypon.domain.dao.IdentityDao;

import java.util.List;

public class IdentityServiceImpl implements IdentityService {

    private final IdentityDao identityDao;

    public IdentityServiceImpl(IdentityDao identityDao) {
        this.identityDao = identityDao;
    }

    @Override
    public boolean create(Identity identity) {
        return identityDao.create(identity);
    }

    @Override
    public Identity get(String username) {
        return identityDao.get(username);
    }

    @Override
    public Identity get(String username, String password) {
        return identityDao.get(username, password);
    }

    @Override
    public boolean update(Identity identity) {
        return identityDao.update(identity);
    }

    @Override
    public boolean delete(String username) {
        return identityDao.delete(username);
    }

    @Override
    public List<Identity> getAll() {
        return identityDao.getAll();
    }
}
