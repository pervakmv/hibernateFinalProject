package model;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

@Entity(name = "Room")
@Table(name = "Room")
public class Room {
    private long id;
    private int numberOfGuests;
    private double price;
    private int breakfastIncluded;
    private int petsAllowed;
    private Date dateAvailableFrom;
    private Hotel hotel;


    public Room() {

    }

    public Room(int numberOfGuests, double price, int breakfastInclueded, int petersAllowed, Date dateAvailableFrom, Hotel hotel) {
        this.numberOfGuests = numberOfGuests;
        this.price = price;
        this.breakfastIncluded = breakfastInclueded;
        this.petsAllowed = petersAllowed;
        this.dateAvailableFrom = dateAvailableFrom;
        this.hotel = hotel;
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

    @Column(name = "NUMBEROFGUESTS")
    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    @Column(name = "PRICE")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "BREAKFASTINCLUDED")
    public int getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public void setBreakfastIncluded(int breakfastInclueded) {
        this.breakfastIncluded = breakfastInclueded;
    }

    @Column(name = "PETSALLOWED")
    public int getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(int petersAllowed) {
        this.petsAllowed = petersAllowed;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATEAVAILABLEFROM")
    public Date getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public void setDateAvailableFrom(Date dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    //@ManyToOne(cascade = CascadeType.ALL, optional = false, fetch= FetchType.EAGER)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HOTEL_ID")
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        Long hotelId = null;
        if (hotel != null) hotelId = hotel.getId();
        return "Room{" +
                "id=" + id +
                ", numberOfGuests=" + numberOfGuests +
                ", price=" + price +
                ", breakfastIncluded=" + breakfastIncluded +
                ", petsAllowed=" + petsAllowed +
                ", dateAvailableFrom=" + dateAvailableFrom +
                ", hotel id=" + hotelId +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        return id == room.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public boolean canBeAdd() {
        if (hotel != null)
            return true;
        return false;

    }


    public boolean equalsByFilter(Filter filter) {
        if (numberOfGuests != filter.getNumberOfGuests()) return false;
        if (price > filter.getPrice()) return false;
        boolean breakfastIncludedCast = true;
        boolean petsAllowedCast = true;
        if (breakfastIncluded == 0)
            breakfastIncludedCast = false;
        if (petsAllowed == 0)
            petsAllowedCast = false;

        if (breakfastIncludedCast != filter.isBreakfastIncluded()) return false;
        if (petsAllowedCast != filter.isPetsAllowed()) return false;


        if (dateAvailableFrom.getYear() != filter.getDateAvailableFrom().getYear()) return false;
        if (dateAvailableFrom.getMonth() != filter.getDateAvailableFrom().getMonth()) return false;
        if (dateAvailableFrom.getDate() != filter.getDateAvailableFrom().getDate()) return false;

        if (!hotel.getCountry().equals(filter.getCountry())) return false;
        if (!hotel.getCity().equals(filter.getCity())) return false;

        return true;
    }



    public Room enterDataByKeyboard() throws Exception{


        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Number of guests : ");
            this.numberOfGuests = Integer.parseInt(br.readLine());
            System.out.println("Price :");
            this.price = Double.parseDouble(br.readLine());
            System.out.println("Breakfast inclueded 1-Yes/2-No");
            if (Integer.valueOf(br.readLine()) == 1)
                this.breakfastIncluded = 1;
            else
                this.breakfastIncluded = 0;

            System.out.println("Pets allowed 1-Yes/2-No");
            if (Integer.parseInt(br.readLine()) == 1)
                this.petsAllowed = 1;
            else
                this.petsAllowed = 0;

            System.out.println("Date available from ");

            int date = 0;
            int month = 0;
            int year = 0;
            do {
                System.out.println("Day");
                date = Integer.parseInt(br.readLine());

            } while (!(date > 0 && date < 32));


            do {
                System.out.println("Month");
                month = Integer.parseInt(br.readLine());
            } while (!(month >= 1 && month < 12));


            do {
                System.out.println("Year");
                year = Integer.parseInt(br.readLine());
            } while (!(year > 2017 && year < 2030));

            dateAvailableFrom = new Date(year - 1900, month - 1, date);



        }

        return this;
    }


}
