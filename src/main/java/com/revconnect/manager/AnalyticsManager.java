package com.revconnect.manager;

import com.revconnect.dao.AnalyticsDao;
import com.revconnect.domain.Analytics;

public class AnalyticsManager {

    private AnalyticsDao dao = new AnalyticsDao();

    public Analytics getMyAnalytics(int userId){
        return dao.getAnalytics(userId);
    }
}
