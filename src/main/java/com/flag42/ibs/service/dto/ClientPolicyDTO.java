package com.flag42.ibs.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.PaymentStatus;

/**
 * A DTO for the {@link com.flag42.ibs.domain.ClientPolicy} entity.
 */
public class ClientPolicyDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate policyDate;

    private String invoiceNo;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Double primiumPayable;

    @NotNull
    private Double openPayable;

    private PaymentStatus paymentStatus;


    private Long clientId;

    private Long policyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPolicyDate() {
        return policyDate;
    }

    public void setPolicyDate(LocalDate policyDate) {
        this.policyDate = policyDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getPrimiumPayable() {
        return primiumPayable;
    }

    public void setPrimiumPayable(Double primiumPayable) {
        this.primiumPayable = primiumPayable;
    }

    public Double getOpenPayable() {
        return openPayable;
    }

    public void setOpenPayable(Double openPayable) {
        this.openPayable = openPayable;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientPolicyDTO clientPolicyDTO = (ClientPolicyDTO) o;
        if (clientPolicyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientPolicyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientPolicyDTO{" +
            "id=" + getId() +
            ", policyDate='" + getPolicyDate() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", primiumPayable=" + getPrimiumPayable() +
            ", openPayable=" + getOpenPayable() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", client=" + getClientId() +
            ", policy=" + getPolicyId() +
            "}";
    }
}
