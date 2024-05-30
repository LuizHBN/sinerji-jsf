package br.com.sinerji.model.address;

public class AddressDTO {
    private Long id;
    private Long userId;
    private String street;
    private String state;
    private String city;
    private String number;
    private String additional;
    private String zipCode;
    private String country;

    public AddressDTO() {
    }

    public AddressDTO(Long id, Long userId, String street, String state, String city, String number, String additional, String zipCode, String country) {
        this.id = id;
        this.userId = userId;
        this.street = street;
        this.state = state;
        this.city = city;
        this.number = number;
        this.additional = additional;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
