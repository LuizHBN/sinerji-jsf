package br.com.sinerji.model.user;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Long id;
    private String name;
    private int age;
    private String phone;
    private Genders gender;

    public UserDTO() {
    }

    public UserDTO(String name, int age, String phone, Genders gender) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
    }
    public UserDTO(Long id,String name, int age, String phone, Genders gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.gender = gender;
    }

    public UserDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                '}';
    }
}