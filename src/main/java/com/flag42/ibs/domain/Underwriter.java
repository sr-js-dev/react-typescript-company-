package com.flag42.ibs.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.flag42.ibs.domain.enumeration.Status;

/**
 * A Underwriter.
 */
@Entity
@Table(name = "underwriter")
public class Underwriter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    @Column(name = "binder")
    private String binder;

    @Column(name = "website")
    private String website;

    @Column(name = "contact_persion")
    private String contactPersion;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Underwriter name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Underwriter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public Underwriter logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBinder() {
        return binder;
    }

    public Underwriter binder(String binder) {
        this.binder = binder;
        return this;
    }

    public void setBinder(String binder) {
        this.binder = binder;
    }

    public String getWebsite() {
        return website;
    }

    public Underwriter website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPersion() {
        return contactPersion;
    }

    public Underwriter contactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
        return this;
    }

    public void setContactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
    }

    public String getTelephone() {
        return telephone;
    }

    public Underwriter telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public Underwriter mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Underwriter email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Underwriter address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Underwriter streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCounty() {
        return county;
    }

    public Underwriter county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public Underwriter country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPinNumber() {
        return pinNumber;
    }

    public Underwriter pinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
        return this;
    }

    public void setPinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Underwriter status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Underwriter)) {
            return false;
        }
        return id != null && id.equals(((Underwriter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Underwriter{" +
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
