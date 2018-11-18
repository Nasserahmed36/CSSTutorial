package com.atypon.service;

import com.atypon.domain.Journal;
import com.atypon.domain.dao.JournalDao;

public class JournalServiceImpl implements JournalService {
    private final JournalDao dao;

    public JournalServiceImpl(JournalDao journalDao) {
        dao = journalDao;
    }

    @Override
    public boolean create(Journal journal) {
        return dao.create(journal);
    }

    @Override
    public Journal get(String issn) {
        return dao.get(issn);
    }

    @Override
    public boolean update(Journal journal) {
        return dao.update(journal);
    }

    @Override
    public boolean delete(String issn) {
        return dao.delete(issn);
    }
}
