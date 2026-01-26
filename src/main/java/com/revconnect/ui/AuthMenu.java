package com.revconnect.ui;

import java.util.Scanner;

import com.revconnect.domain.User;
import com.revconnect.manager.*;

public class AuthMenu {

    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserManager();

    public void register() {

        User user = new User();

        // USERNAME
        String username;
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();

            if (userService.usernameExists(username))
                System.out.println("Username already exists.");
            else
                break;
        }
        user.setUsername(username);

        // EMAIL
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();

            if (userService.emailExists(email))
                System.out.println("Email already exists.");
            else
                break;
        }
        user.setEmail(email);

        // PASSWORD
        String password;
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            if (!userService.isValidPassword(password))
                System.out.println(
                        "Password must contain uppercase, lowercase, digit, special char (8-20)");
            else
                break;
        }
        user.setPassword(password);

        // SECURITY QUESTION
        System.out.println("1. Favourite color?");
        System.out.println("2. Pet name?");
        System.out.println("3. Birth city?");

        int q = Integer.parseInt(scanner.nextLine());

        if (q == 1)
            user.setSecurityQuestion("Favourite color?");
        if (q == 2)
            user.setSecurityQuestion("Pet name?");
        if (q == 3)
            user.setSecurityQuestion("Birth city?");

        System.out.print("Answer: ");
        user.setSecurityAnswer(scanner.nextLine());

        // ACCOUNT TYPE
        System.out.println("1. Personal");
        System.out.println("2. Creator");
        System.out.println("3. Business");

        int type = Integer.parseInt(scanner.nextLine());

        if (type == 1)
            user.setAccountType("PERSONAL");
        if (type == 2)
            user.setAccountType("CREATOR");
        if (type == 3)
            user.setAccountType("BUSINESS");

        user.setStatus("ACTIVE");

        userService.register(user);
        System.out.println("Registration successful");
    }

    public void login() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);

        if (user != null) {
            System.out.println("Login successful");

            // ✅ HOME SCREEN
            UserMenu menu = new UserMenu(user);
            menu.start();
        } else {
            System.out.println("Invalid credentials");
        }
    }

    public void forgotPassword() {

        System.out.print("Username: ");
        String uname = scanner.nextLine();

        String question =
                userService.getSecurityQuestion(uname);

        if (question == null) {
            System.out.println("User not found");
            return;
        }

        System.out.println(question);
        System.out.print("Answer: ");
        String ans = scanner.nextLine();

        String pass;
        while (true) {
            System.out.print("New Password: ");
            pass = scanner.nextLine();

            if (!userService.isValidPassword(pass))
                System.out.println("Weak password");
            else
                break;
        }

        boolean ok =
                userService.resetPassword(uname, ans, pass);

        if (ok)
            System.out.println("Password reset successful");
        else
            System.out.println("Wrong answer");
    }
}
