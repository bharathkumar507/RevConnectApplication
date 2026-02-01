RevConnect is a modular, console-based social media platform developed using Java and MySQL. 
It enables personal users, creators, and business accounts to connect, share posts, interact through likes and comments, follow others, and receive real-time notifications.
The application is designed with a layered architecture that ensures scalability, maintainability, and clean separation of concerns.

Features
---------
Authentication & Security, 
User registration with unique username and email, 
Login using username and password, 
Password validation (8–20 chars, uppercase, lowercase, digit, special char), 
Change password with old password verification, 
Forgot password using security question & answer, and 
Account privacy settings (public / private).

Profile Management
--------------------
Create and edit profile, 
View own and other user profiles, 
Creator/Business enhanced profile fields:
Business/Creator name, 
Category / Industry, 
Contact info, 
Address, and 
Business hours.

Post Management
-----------------
Create posts with hashtags, 
View feed and own posts, 
Edit and delete posts, and 
Repost / Share posts.

Social Interactions
--------------------
Like / Unlike posts, 
Comment on posts, and
Edit and delete own comments.

Network Building
-----------------
Send / accept / reject connection requests, 
View connections, 
Follow / Unfollow users, and 
View followers & following list.

Notifications
--------------
Likes, comments, shares, 
Follow notifications, 
Connection request notifications, and 
Unread count and history.

Creator / Business Analytics
-----------------------------
Total likes per post, 
Total comments per post, and 
Total shares per post.

Account Management
------------------
Delete own account (cascade delete of related data),


 System Architecture

RevConnect follows a Layered Architecture:
-----------------------------------------
Presentation Layer (UI Menus), 
Service / Manager Layer, 
DAO Layer, and
Database Layer (MySQL).

 Database Design
------------------
Relational database with normalized tables and foreign key constraints.

Main Tables:
----------
users, 
profiles, 
posts, 
comments, 
likes, 
follows, 
connection_requests, and
notifications. 
ON DELETE CASCADE is used to maintain data integrity.

Technologies Used
---------------------
Java, 
MySQL, 
JDBC, 
Maven, 
JUnit, 
Mockito, 
Log4j2, and 
Git.
