package com.revconnect.ui;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner = new Scanner(System.in);

    public void start() {

        while (true) {

            System.out.println("\n=== REVCONNECT ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("0. Exit");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                AuthMenu authMenu = new AuthMenu();
                authMenu.register();
            }

            if (choice == 2) {
                AuthMenu authMenu = new AuthMenu();
                authMenu.login();
            }
            if (choice == 3) {
                AuthMenu authMenu = new AuthMenu();
                authMenu.forgotPassword();
            }

            if (choice == 0) {
                System.out.println("Goodbye");
                break;
            }

        }

    }

}
