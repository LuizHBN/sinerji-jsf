package br.com.sinerji.model.address;

import br.com.sinerji.model.user.User;
import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String street;
    private String state;
    private String city;
    private String number;
    private String additional;
    private String zipCode;
    private String country;

    public Address(User user, String street, String state, String city, String number, String additional, String zipCode, String country) {
        this.user = user;
        this.street = street;
        this.state = state;
        this.city = city;
        this.number = number;
        this.additional = additional;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Address() {

    }

    public Address(AddressDTO addressDTO) {
        this.id = addressDTO.getId();
        this.street = addressDTO.getStreet();
        this.state = addressDTO.getState();
        this.city = addressDTO.getCity();
        this.number = addressDTO.getNumber();
        this.additional = addressDTO.getAdditional();
        this.zipCode = addressDTO.getZipCode();
        this.country = addressDTO.getCountry();
        this.user = null;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", user=" + user +
                ", street='" + street + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", number='" + number + '\'' +
                ", additional='" + additional + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}