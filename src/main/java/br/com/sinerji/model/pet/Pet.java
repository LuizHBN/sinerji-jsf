package br.com.sinerji.model.pet;

import br.com.sinerji.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private PetTypes type;
    private String name;
    private Date birthDate;
    @JoinColumn(name = "owner_id")
    @ManyToOne
    private User owner;
    private String additionalInformation;

    public Pet() {
    }

    public Pet(Long id, PetTypes type, String name, Date birthDate, User owner, String additionalInformation) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.owner = owner;
        this.additionalInformation = additionalInformation;
    }
    public Pet(PetDTO petDTO) {
        this.id = petDTO.getId();
        this.type = PetTypes.valueOf(petDTO.getType()); // assuming PetTypes is an enum
        this.name = petDTO.getName();
        this.birthDate = petDTO.getBirthDate();
        this.additionalInformation = petDTO.getAdditionalInformation();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetTypes getType() {
        return type;
    }

    public void setType(PetTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}


