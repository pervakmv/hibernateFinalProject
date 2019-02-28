package service;

import model.User;
import repository.UserDAO;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User registerUser(User user) throws Exception {
        //check business logic
        if (!user.canBeRegistred())
            throw new Exception("The entered data can't be written");

        if (userDAO.findUserByName(user.getUsername()) != null)
            throw new Exception("User with name " + user.getUsername() + " is already exist");

        return userDAO.registerUser(user);
    }

    public void login(String userName, String password) throws Exception {
        userDAO.login(userName, password);
    }

    public void logout() throws Exception {
        userDAO.logout();
    }
}
