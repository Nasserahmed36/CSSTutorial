package com.atypon.domain.dao;


import com.atypon.domain.Journal;

public interface JournalDao {
    boolean create(Journal journal);
    Journal get(String issn);
    boolean update(Journal journal);
    boolean delete(String issn);
}
