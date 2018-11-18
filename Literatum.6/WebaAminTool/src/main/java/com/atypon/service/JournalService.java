package com.atypon.service;

import com.atypon.domain.Journal;

public interface JournalService {
    boolean create(Journal journal);

    Journal get(String issn);

    boolean update(Journal journal);

    boolean delete(String issn);
}
