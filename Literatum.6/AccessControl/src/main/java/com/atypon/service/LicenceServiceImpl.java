package com.atypon.service;

import com.atypon.domain.ContentLicence;
import com.atypon.domain.LicenceType;
import com.atypon.domain.UserLicence;
import com.atypon.domain.dao.LicenceDao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class LicenceServiceImpl implements LicenceService {

    private final LicenceDao licenceDao;

    public LicenceServiceImpl(LicenceDao licenceDao) {
        this.licenceDao = licenceDao;
    }

    @Override
    public boolean createContentLicence(ContentLicence contentLicence) {
        return licenceDao.createContentLicence(contentLicence);
    }

    @Override
    public boolean createUserLicence(UserLicence userLicence) {
        return licenceDao.createUserLicence(userLicence);
    }

    @Override
    public boolean deleteContentLicence(int contentLicenceId) {
        return licenceDao.deleteContentLicence(contentLicenceId);
    }

    @Override
    public boolean deleteUserLicence(UserLicence userLicence) {
        return licenceDao.deleteUserLicence(userLicence);
    }

    @Override
    public boolean updateContentLicence(ContentLicence contentLicence) {
        return licenceDao.updateContentLicence(contentLicence);
    }

    @Override
    public boolean updateUserLicenceDate(UserLicence userLicence) {
        return licenceDao.updateUserLicenceDate(userLicence);
    }

    @Override
    public List<UserLicence> getLicences(String userName, String contentId) {
        return licenceDao.getLicences(userName, contentId);
    }

    @Override
    public List<ContentLicence> getLicences(String contentId) {
        return licenceDao.getLicences(contentId);
    }

    @Override
    public boolean hasAccess(String userName, String contentId) {
        return contentHasFreeAccess(contentId) ||
        userHasAccess(userName, contentId);
    }

    private boolean contentHasFreeAccess(String contentId) {
        List<ContentLicence> contentLicences = licenceDao.getLicences(contentId);
        for(ContentLicence contentLicence:contentLicences) {
            int licenceId = contentLicence.getLicence().getId();
            LicenceType licenceType = LicenceType.getLicenceType(licenceId);
            if(licenceType == LicenceType.FREE &&
                    contentLicence.getPrice().compareTo(BigDecimal.ZERO) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean userHasAccess(String userName, String contentId) {
        List<UserLicence> userLicences = licenceDao.getLicences(userName, contentId);
        for (UserLicence userLicence : userLicences) {
            int licenceId = userLicence.getContentLicence().getLicence().getId();
            LicenceType licenceType = LicenceType.getLicenceType(licenceId);
            switch (licenceType) {
                case FREE:
                    return true;
                case LIMITED:
                    if (!hasLicenceExpired(userLicence)) {
                        return true;
                    }
            }
        }
        return false;
    }


    private boolean hasLicenceExpired(UserLicence userLicence) {
        long finishDate = getFinishDate(userLicence);
        return hasFinished(finishDate);
    }


    private long getFinishDate(UserLicence userLicence) {
        Calendar finishDate = Calendar.getInstance();
        finishDate.setTimeInMillis(userLicence.getStartDate());
        finishDate.add(Calendar.MONTH, userLicence.getContentLicence().getPeriod());
        return finishDate.getTimeInMillis();
    }

    private boolean hasFinished(long finishDate) {
        long now = System.currentTimeMillis();
        return now > finishDate;
    }

}
