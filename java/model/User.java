package model;




import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity(name = "User")
@Table(name = "Users")

public class User  {
    private long id;
    private String username;
    private String password;
    private String country;
    private UserType userType;
    private List<Order> orders = new ArrayList<Order>();




    public User() {
    }

    public User(String username, String password, String country, UserType userType, List<Order> orders) {
        this.username = username;
        this.password = password;
        this.country = country;
        this.userType = userType;
        this.orders = orders;
    }

    @Id
    @SequenceGenerator(name = "PR_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PR_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Enumerated
    @Column(name = "USERTYPE")
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    //  @OneToMany(fetch=FetchType.EAGER, targetEntity=  Room.class, mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
   @OneToMany(fetch = FetchType.EAGER, targetEntity = Order.class,  mappedBy="user", cascade = CascadeType.ALL,  orphanRemoval = true)
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", userType=" + userType +
              //  ", orders=" + orders +
                '}';
    }


    public boolean canBeRegistred() {
        if (username != null &&
                password != null &&
                country != null &&
                userType != null)
            return true;
        return false;
    }



    public User enterDataByKeyboard() throws Exception {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.print("UserName: ");
            this.username = br.readLine();


            System.out.print("Password: ");
            this.password = br.readLine();


            System.out.print("Country: ");
            this.country = br.readLine();


            System.out.print("UserType: 1- USER, 2-ADMIN. The default value is USER. ");
            if (Integer.valueOf(br.readLine()) == 2) {
                this.userType = UserType.ADMIN;
            } else {
                this.userType = userType.USER;
            }

        }
        return this;
    }
}
