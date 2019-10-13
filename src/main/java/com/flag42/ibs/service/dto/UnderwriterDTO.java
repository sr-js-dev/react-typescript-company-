package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.Status;

/**
 * A DTO for the {@link com.flag42.ibs.domain.Underwriter} entity.
 */
public class UnderwriterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String logo;

    private String binder;

    private String website;

    private String contactPersion;

    private String telephone;

    private String mobile;

    private String email;

    private String address;

    private String streetAddress;

    private String county;

    private String country;

    private Long pinNumber;

    private Status status;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBinder() {
        return binder;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPersion() {
        return contactPersion;
    }

    public void setContactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnderwriterDTO underwriterDTO = (UnderwriterDTO) o;
        if (underwriterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), underwriterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnderwriterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", logo='" + getLogo() + "'" +
            ", binder='" + getBinder() + "'" +
            ", website='" + getWebsite() + "'" +
            ", contactPersion='" + getContactPersion() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", county='" + getCounty() + "'" +
            ", country='" + getCountry() + "'" +
            ", pinNumber=" + getPinNumber() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
