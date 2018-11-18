package com.atypon.service;

import com.atypon.domain.ContentLicence;
import com.atypon.domain.UserLicence;

import java.util.List;

public interface LicenceService {
    boolean createContentLicence(ContentLicence contentLicence);
    boolean createUserLicence(UserLicence userLicence);

    boolean deleteContentLicence(int contentLicenceId);
    boolean deleteUserLicence(UserLicence userLicence);

    boolean updateContentLicence(ContentLicence contentLicence);
    boolean updateUserLicenceDate(UserLicence userLicence);

    List<UserLicence> getLicences(String userName, String contentId);
    List<ContentLicence> getLicences(String contentId);

    boolean hasAccess(String userName, String contentId);
}