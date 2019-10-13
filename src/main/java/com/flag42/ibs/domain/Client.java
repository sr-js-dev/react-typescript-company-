package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.flag42.ibs.domain.enumeration.Status;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "ledger_name")
    private String ledgerName;

    @Column(name = "client_print_name")
    private String clientPrintName;

    @Column(name = "id_number")
    private String idNumber;

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

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clients")
    private ClientCategory category;

    @ManyToOne
    @JsonIgnoreProperties("clients")
    private NameTitle title;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clients")
    private IdType idType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Client firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Client middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Client lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClientName() {
        return clientName;
    }

    public Client clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public Client ledgerName(String ledgerName) {
        this.ledgerName = ledgerName;
        return this;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getClientPrintName() {
        return clientPrintName;
    }

    public Client clientPrintName(String clientPrintName) {
        this.clientPrintName = clientPrintName;
        return this;
    }

    public void setClientPrintName(String clientPrintName) {
        this.clientPrintName = clientPrintName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public Client idNumber(String idNumber) {
        this.idNumber = idNumber;
        return this;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getContactPersion() {
        return contactPersion;
    }

    public Client contactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
        return this;
    }

    public void setContactPersion(String contactPersion) {
        this.contactPersion = contactPersion;
    }

    public String getTelephone() {
        return telephone;
    }

    public Client telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public Client mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Client address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Client streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCounty() {
        return county;
    }

    public Client county(String county) {
        this.county = county;
        return this;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public Client country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getPinNumber() {
        return pinNumber;
    }

    public Client pinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
        return this;
    }

    public void setPinNumber(Long pinNumber) {
        this.pinNumber = pinNumber;
    }

    public String getNotes() {
        return notes;
    }

    public Client notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Status getStatus() {
        return status;
    }

    public Client status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClientCategory getCategory() {
        return category;
    }

    public Client category(ClientCategory clientCategory) {
        this.category = clientCategory;
        return this;
    }

    public void setCategory(ClientCategory clientCategory) {
        this.category = clientCategory;
    }

    public NameTitle getTitle() {
        return title;
    }

    public Client title(NameTitle nameTitle) {
        this.title = nameTitle;
        return this;
    }

    public void setTitle(NameTitle nameTitle) {
        this.title = nameTitle;
    }

    public IdType getIdType() {
        return idType;
    }

    public Client idType(IdType idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", clientName='" + getClientName() + "'" +
            ", ledgerName='" + getLedgerName() + "'" +
            ", clientPrintName='" + getClientPrintName() + "'" +
            ", idNumber='" + getIdNumber() + "'" +
            ", contactPersion='" + getContactPersion() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", county='" + getCounty() + "'" +
            ", country='" + getCountry() + "'" +
            ", pinNumber=" + getPinNumber() +
            ", notes='" + getNotes() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
