package br.com.sinerji.model.pet;

import java.io.Serializable;
import java.util.Date;

public class PetDTO implements Serializable {
    private Long id;
    private String type;
    private String name;
    private Date birthDate;
    private Long ownerId;

    private String additionalInformation;

    public PetDTO() {
    }

    public PetDTO(Long id, String type, String name, Date birthDate, Long ownerId, String additionalInformation) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.ownerId = ownerId;

        this.additionalInformation = additionalInformation;
    }
    public PetDTO(String type, String name, Date birthDate, Long ownerId, String additionalInformation) {
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.ownerId = ownerId;

        this.additionalInformation = additionalInformation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
