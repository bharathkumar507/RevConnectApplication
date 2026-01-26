package com.revconnect.dao;

import com.revconnect.domain.Profile;

public interface ProfileRepo {

    void saveProfile(Profile profile);

    Profile findByUserId(int userId);

}
