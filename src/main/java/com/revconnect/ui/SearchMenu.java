package com.revconnect.ui;

import java.util.List;
import java.util.Scanner;

import com.revconnect.domain.User;
import com.revconnect.manager.UserManager;
import com.revconnect.manager.UserService;

public class SearchMenu {

    private Scanner sc = new Scanner(System.in);
    private UserService userService = new UserManager();

    public void start() {

        System.out.print("Enter username to search: ");
        String key = sc.nextLine();

        List<User> list = userService.search(key);

        if (list.isEmpty()) {
            System.out.println("No users found");
        } else {
            for (User u : list) {
                System.out.println(
                        u.getId() + " | " +
                                u.getUsername() + " | " +
                                u.getAccountType()
                );
            }
        }
    }
}
