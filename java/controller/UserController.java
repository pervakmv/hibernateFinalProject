package controller;

import model.User;
import service.UserService;

public class UserController {
    private UserService userService = new UserService();

    public User registerUser(User user) throws Exception{
        return userService.registerUser(user);
    }

    public void login(String userName, String password) throws Exception{
        userService.login(userName, password);
    }

    public void logout() throws Exception{
        userService.logout();
    }
}
