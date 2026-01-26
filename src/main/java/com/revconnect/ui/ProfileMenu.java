package com.revconnect.ui;

import java.util.Scanner;

import com.revconnect.domain.Profile;
import com.revconnect.domain.User;
import com.revconnect.manager.*;

public class ProfileMenu {

    private User user;
    private Scanner sc = new Scanner(System.in);

    private ProfileService profileService = new ProfileManager();
    private UserService userService = new UserManager();

    public ProfileMenu(User user) {
        this.user = user;
    }

    public void start() {

        while (true) {

            System.out.println("\n--- PROFILE ---");
            System.out.println("1. Create/Edit Profile");
            System.out.println("2. View Profile");
            System.out.println("3. Change Password");
            System.out.println("4. Privacy Settings");
            System.out.println("0. Back");

            int ch =
                    Integer.parseInt(sc.nextLine());

            if (ch == 1) {
                if (profileService.profileExists(user.getId()))
                    editProfile();
                else
                    createProfile();
            }

            else if (ch == 2)
                viewProfile();

            else if (ch == 3)
                changePassword();

            else if (ch == 4)
                privacySettings();

            else if (ch == 0)
                break;
        }
    }

    private void createProfile() {

        Profile p = new Profile();
        p.setUserId(user.getId());

        // ----- COMMON FIELDS -----

        System.out.print("Name: ");
        p.setName(sc.nextLine());

        System.out.print("Bio: ");
        p.setBio(sc.nextLine());

        System.out.print("Location: ");
        p.setLocation(sc.nextLine());

        System.out.print("Website: ");
        p.setWebsite(sc.nextLine());

        // ----- CREATOR / BUSINESS EXTRA FIELDS -----

        if(user.getAccountType().equals("CREATOR") ||
                user.getAccountType().equals("BUSINESS")){

            System.out.print("Business/Creator Name: ");
            p.setBusinessName(sc.nextLine());

            System.out.print("Category / Industry: ");
            p.setCategory(sc.nextLine());

            System.out.print("Contact Info: ");
            p.setContactInfo(sc.nextLine());

            System.out.print("Address: ");
            p.setAddress(sc.nextLine());

            System.out.print("Business Hours: ");
            p.setBusinessHours(sc.nextLine());
        }

        profileService.createProfile(p);

        System.out.println("Profile created");
    }


    private void editProfile() {

        Profile p =
                profileService.getProfileByUserId(
                        user.getId());

        // -------- COMMON FIELDS --------

        System.out.print("Name (" + p.getName() + "): ");
        String name = sc.nextLine();
        if(!name.isEmpty())
            p.setName(name);

        System.out.print("Bio (" + p.getBio() + "): ");
        String bio = sc.nextLine();
        if(!bio.isEmpty())
            p.setBio(bio);

        System.out.print("Location (" + p.getLocation() + "): ");
        String loc = sc.nextLine();
        if(!loc.isEmpty())
            p.setLocation(loc);

        System.out.print("Website (" + p.getWebsite() + "): ");
        String web = sc.nextLine();
        if(!web.isEmpty())
            p.setWebsite(web);

        // ----- CREATOR / BUSINESS EXTRA FIELDS -----

        if(user.getAccountType().equals("CREATOR") ||
                user.getAccountType().equals("BUSINESS")){

            System.out.print("Business/Creator Name (" +
                    p.getBusinessName() + "): ");
            String bn = sc.nextLine();
            if(!bn.isEmpty())
                p.setBusinessName(bn);

            System.out.print("Category / Industry (" +
                    p.getCategory() + "): ");
            String cat = sc.nextLine();
            if(!cat.isEmpty())
                p.setCategory(cat);

            System.out.print("Contact Info (" +
                    p.getContactInfo() + "): ");
            String ci = sc.nextLine();
            if(!ci.isEmpty())
                p.setContactInfo(ci);

            System.out.print("Address (" +
                    p.getAddress() + "): ");
            String ad = sc.nextLine();
            if(!ad.isEmpty())
                p.setAddress(ad);

            System.out.print("Business Hours (" +
                    p.getBusinessHours() + "): ");
            String bh = sc.nextLine();
            if(!bh.isEmpty())
                p.setBusinessHours(bh);
        }

        profileService.updateProfile(p);

        System.out.println("Profile updated");
    }


    private void viewProfile() {

        Profile p =
                profileService.getProfileByUserId(
                        user.getId());

        if(p == null){
            System.out.println("Profile not created yet.");
            return;
        }

        System.out.println("\n--- PROFILE DETAILS ---");

        System.out.println("Name: " + p.getName());
        System.out.println("Bio: " + p.getBio());
        System.out.println("Location: " + p.getLocation());
        System.out.println("Website: " + p.getWebsite());

        // ----- CREATOR / BUSINESS EXTRA FIELDS -----

        if(user.getAccountType().equals("CREATOR") ||
                user.getAccountType().equals("BUSINESS")){

            System.out.println("Business/Creator Name: " +
                    p.getBusinessName());

            System.out.println("Category / Industry: " +
                    p.getCategory());

            System.out.println("Contact Info: " +
                    p.getContactInfo());

            System.out.println("Address: " +
                    p.getAddress());

            System.out.println("Business Hours: " +
                    p.getBusinessHours());
        }
    }


    private void changePassword() {

        System.out.print("Old Password: ");
        String oldp = sc.nextLine();

        System.out.print("New Password: ");
        String newp = sc.nextLine();

        boolean ok =
                userService.changePassword(user.getId(), oldp, newp);

        if (ok)
            System.out.println("Password changed");
        else
            System.out.println("Wrong password");
    }

    private void privacySettings() {

        System.out.println("1.Public 2.Private");
        int ch =
                Integer.parseInt(sc.nextLine());

        if (ch == 1)
            userService.updatePrivacy(user.getId(), false);
        else
            userService.updatePrivacy(user.getId(), true);

        System.out.println("Updated");
    }
}
