package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.flag42.ibs.domain.enumeration.PaymentStatus;

/**
 * A ClientPolicy.
 */
@Entity
@Table(name = "client_policy")
public class ClientPolicy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "policy_date", nullable = false)
    private LocalDate policyDate;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "primium_payable", nullable = false)
    private Double primiumPayable;

    @NotNull
    @Column(name = "open_payable", nullable = false)
    private Double openPayable;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientPolicies")
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientPolicies")
    private Policy policy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPolicyDate() {
        return policyDate;
    }

    public ClientPolicy policyDate(LocalDate policyDate) {
        this.policyDate = policyDate;
        return this;
    }

    public void setPolicyDate(LocalDate policyDate) {
        this.policyDate = policyDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public ClientPolicy invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ClientPolicy startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ClientPolicy endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getPrimiumPayable() {
        return primiumPayable;
    }

    public ClientPolicy primiumPayable(Double primiumPayable) {
        this.primiumPayable = primiumPayable;
        return this;
    }

    public void setPrimiumPayable(Double primiumPayable) {
        this.primiumPayable = primiumPayable;
    }

    public Double getOpenPayable() {
        return openPayable;
    }

    public ClientPolicy openPayable(Double openPayable) {
        this.openPayable = openPayable;
        return this;
    }

    public void setOpenPayable(Double openPayable) {
        this.openPayable = openPayable;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public ClientPolicy paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Client getClient() {
        return client;
    }

    public ClientPolicy client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Policy getPolicy() {
        return policy;
    }

    public ClientPolicy policy(Policy policy) {
        this.policy = policy;
        return this;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientPolicy)) {
            return false;
        }
        return id != null && id.equals(((ClientPolicy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClientPolicy{" +
            "id=" + getId() +
            ", policyDate='" + getPolicyDate() + "'" +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", primiumPayable=" + getPrimiumPayable() +
            ", openPayable=" + getOpenPayable() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            "}";
    }
}
