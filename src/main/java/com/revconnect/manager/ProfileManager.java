package com.revconnect.manager;

import com.revconnect.dao.ProfileDao;
import com.revconnect.domain.Profile;

public class ProfileManager implements ProfileService {

    private ProfileDao profileDao = new ProfileDao();

    public void createProfile(Profile profile) {
        profileDao.saveProfile(profile);
    }

    public Profile getProfileByUserId(int userId) {
        return profileDao.findByUserId(userId);
    }
    public boolean profileExists(int userId){
        return profileDao.existsByUserId(userId);
    }
    public void updateProfile(Profile p){
        profileDao.update(p);
    }

}
