# payitforward

Java/Spring Boot web app that lets users create accounts and submit/view/claim/complete "opportunities."
An "opportunity" is defined as a request for volunteers to help with a task. Users can be individuals or existing community/philanthropic groups.


Installation Instructions:

What you'll need: MAMP and IntelliJ IDEA

1. Clone project from our repo to your own machine.

2. Start MAMP and create a user account with username "payitforward" and password "forward".

3. In IntelliJ, open application.properties.

4. Add the following lines to application.properties:
# Path to project
project.base-dir=file:///[Your path goes here!]

# Static resources reloading during development
spring.resources.static-locations=${project.base-dir}/src/main/resources/static/
spring.resources.cache-period=0

5. In the text you just pasted, replace [Your path goes here!] with the path to your project.
If you're unsure of the path, right-click in the navigation pane and select "Copy Path." If you use Windows, you'll need to change the back slashes to forward slashes.

5. Run the application and view it at localhost:8080.

Test Plan:

1. From landing page, view an "About" page for our app without needing to login.

2. From landing page, view a list of available opportunities without needing to login.

3. From list of opportunities, view details of a single opportunity without needing to login.

4. Create an account and redirect to myprofile page.

5. While logged in, view your account profile, edit it, and redirect to view profile to see your changes.

6. While logged in, create a new volunteer opportunity and redirect to opportunity list.

7. While logged in, edit a volunteer opportunity and redirect to opportunity list.

8. While logged in, delete one or more volunteer opportunities and redirect to opportunity list.

9. Log out and redirect to logout page.

10. Log in with the same credentials and redirect to landing page.*

* Any errors in input (ie. length requirements weren't met, username is already taken, passwords don't match) will result in the page re-rendering and an error message appearing.


User Stories:

*Browse opportunities: As a new user, I want to browse a list of opportunities without creating an account.

*View an opportunity: As a new user, I want to be able to view the details of an opportunity without creating an account.

*Create account: As a new user, I want to be able to create a new user account.

*Login: As an existing user, I want to be able to login to my account.

*Create opportunity: As an existing user, I want to be able to create a new opportunity.

*Claim opportunity: As an existing user, I want to be able to claim an opportunity.

*View claimed opportunities: As an existing user, I want to be able to view my list of claimed opportunities.

*Mark as done: As an existing user, I want to be able to mark a claimed opportunity as completed.

*View profile: As an existing user, I want to be able to view my profile page.

*Edit profile: As an existing user, I want to be able to edit my profile page.


Wireframes:

![payitforward-wireframe](https://user-images.githubusercontent.com/25624304/31366647-0823ea7c-ad27-11e7-8896-4022a72bb451.jpg)


