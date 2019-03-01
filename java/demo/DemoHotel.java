package demo;

import controller.HotelController;
import controller.UserController;
import model.Hotel;
import model.User;
import model.UserType;
import repository.UserDAO;
import utils.Utils;

public class DemoHotel {

    public static void main(String[] args) throws Exception{

        Hotel hotel = new Hotel();
        HotelController hotelController = new HotelController();
        UserController userController = new UserController();
        UserDAO userDAO = new UserDAO();

        String name = Utils.readKeyboardWithScannerString("User: ");
        String password = Utils.readKeyboardWithScannerString("Password: ");

        userController.login(name, password);

        System.out.println(hotelController.addHotel(new Hotel().enterDataByKeyboard()));
       //System.out.println(userDAO.getAll());
//        User user  = new User();
//        user.setId(81);
//        user.setUsername("Nikolay");
//        user.setPassword("2278");
//        user.setCountry("Ukraine");
//        user.setUserType(UserType.ADMIN);
//        userDAO.update(user);





    }
}
