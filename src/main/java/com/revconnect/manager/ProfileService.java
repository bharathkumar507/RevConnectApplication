package com.revconnect.manager;

import com.revconnect.domain.Profile;

public interface ProfileService {

    void createProfile(Profile profile);

    Profile getProfileByUserId(int userId);
    public boolean profileExists(int userId);
    public void updateProfile(Profile p);


}
