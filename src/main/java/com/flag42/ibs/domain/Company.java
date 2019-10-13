package com.flag42.ibs.domain;

import javax.persistence.*;

import java.io.Serializable;

import com.flag42.ibs.domain.enumeration.UserType;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype")
    private UserType usertype;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "logo")
    private String logo;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "contact_persion")
    private String contactPersion;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "county")
    private String county;

    @Column(name = "country")
    private String country;

    @Column(name = "pin_number")
    private Long pinNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public Company usertype(UserType usertype) {
        this.usertype = usertype;
        return this;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getFirstName() {
        return firstName;
    }

    public Company firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Company middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Company lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Company displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLogo() {
        return logo;
    }

    public Company logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTelephone() {
        return telephone;
    }

    public Company telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactPersion() {
        return contactPersion;
    }

    public Company contactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
        return this;
    }

    public void setContactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
    }

    public String getMobile() {
        return mobile;
    }

    public Company mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public Company address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Company streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCounty() {
        return county;
    }

    public Company county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public Company country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPinNumber() {
        return pinNumber;
    }

    public Company pinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
        return this;
    }

    public void setPinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
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
