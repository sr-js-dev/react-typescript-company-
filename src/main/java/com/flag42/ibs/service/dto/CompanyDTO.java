package com.flag42.ibs.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.UserType;

/**
 * A DTO for the {@link com.flag42.ibs.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {

    private Long id;

    private UserType usertype;

    private String firstName;

    private String middleName;

    private String lastName;

    private String name;

    private String email;

    private String displayName;

    private String logo;

    private String telephone;

    private String contactPersion;

    private String mobile;

    private String address;

    private String streetAddress;

    private String county;

    private String country;

    private Long pinNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactPersion() {
        return contactPersion;
    }

    public void setContactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", usertype='" + getUsertype() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", contactPersion='" + getContactPersion() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", county='" + getCounty() + "'" +
            ", country='" + getCountry() + "'" +
            ", pinNumber=" + getPinNumber() +
            "}";
    }
}
