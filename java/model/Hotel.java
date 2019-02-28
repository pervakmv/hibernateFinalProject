package model;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity(name = "Hotel")
@Table(name = "HOTEL")

public class Hotel {
    private long id;
    private String name;
    private String country;
    private String city;
    String street;
    List<Room>rooms;

    public Hotel() {
    }

    public Hotel(String name, String country, String city, String street, List<Room>rooms) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.street = street;
        this.rooms = rooms;
    }

@Id
@SequenceGenerator(name= "PR_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PR_SEQ")
@Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="COUNTRY")
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "STREET")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @OneToMany(fetch=FetchType.EAGER, targetEntity=  Room.class, mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)

    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", rooms=" + rooms +
                '}';
    }

    public boolean canBeAdd() {
        if (country != null &&
                name != null &&
                city != null &&
                street != null) {
            return true;
        }
        return false;
    }
}
