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
    public boolean createIdentity(Identity identity) {
        return identityDao.createIdentity(identity);
    }

    @Override
    public Identity getIdentity(String username) {
        return identityDao.getIdentity(username);
    }

    @Override
    public Identity getIdentity(String username, String password) {
        return identityDao.getIdentity(username, password);
    }

    @Override
    public boolean updateIdentity(Identity identity) {
        return identityDao.updateIdentity(identity);
    }

    @Override
    public boolean deleteIdentity(String username) {
        return identityDao.deleteIdentity(username);
    }

    @Override
    public List<Identity> getAll() {
        return identityDao.getAll();
    }
}
