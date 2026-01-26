package com.revconnect.ui;

import com.revconnect.domain.Analytics;
import com.revconnect.domain.User;
import com.revconnect.manager.AnalyticsManager;

public class AnalyticsMenu {

    private User user;
    private AnalyticsManager manager =
            new AnalyticsManager();

    public AnalyticsMenu(User user){
        this.user = user;
    }

    public void start(){

        Analytics a =
                manager.getMyAnalytics(user.getId());

        System.out.println("\n--- ANALYTICS ---");
        System.out.println("Total Posts: " +
                a.getTotalPosts());
        System.out.println("Total Likes: " +
                a.getTotalLikes());
        System.out.println("Total Comments: " +
                a.getTotalComments());
        System.out.println("Total Shares: " +
                a.getTotalShares());
        System.out.println("Reach: " +
                a.getReach());
    }
}
