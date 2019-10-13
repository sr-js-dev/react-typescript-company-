package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.Status;

/**
 * A DTO for the {@link com.flag42.ibs.domain.Client} entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String clientName;

    private String ledgerName;

    private String clientPrintName;

    private String idNumber;

    private String contactPersion;

    private String telephone;

    private String mobile;

    private String email;

    private String address;

    private String streetAddress;

    private String county;

    private String country;

    private Long pinNumber;

    private String notes;

    private Status status;


    private Long categoryId;

    private Long titleId;

    private Long idTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getClientPrintName() {
        return clientPrintName;
    }

    public void setClientPrintName(String clientPrintName) {
        this.clientPrintName = clientPrintName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long clientCategoryId) {
        this.categoryId = clientCategoryId;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long nameTitleId) {
        this.titleId = nameTitleId;
    }

    public Long getIdTypeId() {
        return idTypeId;
    }

    public void setIdTypeId(Long idTypeId) {
        this.idTypeId = idTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (clientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
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
            ", category=" + getCategoryId() +
            ", title=" + getTitleId() +
            ", idType=" + getIdTypeId() +
            "}";
    }
}
