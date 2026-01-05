package ro.utbv.sqlitedb;

public class Address {
    private String zipcode;
    private String street;
    private String city;

    public Address(String zipcode, String street, String city) {
        this.zipcode = zipcode;
        this.street = street;
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
