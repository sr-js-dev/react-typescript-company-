package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.flag42.ibs.domain.enumeration.PaymentMethod;

/**
 * A ClientPolicyPayment.
 */
@Entity
@Table(name = "client_policy_payment")
public class ClientPolicyPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_date")
    private LocalDate payDate;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "is_ipf")
    private Boolean isIPF;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientPolicyPayments")
    private ClientPolicy clientPolicy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public ClientPolicyPayment payDate(LocalDate payDate) {
        this.payDate = payDate;
        return this;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public Double getAmount() {
        return amount;
    }

    public ClientPolicyPayment amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public ClientPolicyPayment paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean isIsIPF() {
        return isIPF;
    }

    public ClientPolicyPayment isIPF(Boolean isIPF) {
        this.isIPF = isIPF;
        return this;
    }

    public void setIsIPF(Boolean isIPF) {
        this.isIPF = isIPF;
    }

    public ClientPolicy getClientPolicy() {
        return clientPolicy;
    }

    public ClientPolicyPayment clientPolicy(ClientPolicy clientPolicy) {
        this.clientPolicy = clientPolicy;
        return this;
    }

    public void setClientPolicy(ClientPolicy clientPolicy) {
        this.clientPolicy = clientPolicy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientPolicyPayment)) {
            return false;
        }
        return id != null && id.equals(((ClientPolicyPayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClientPolicyPayment{" +
            "id=" + getId() +
            ", payDate='" + getPayDate() + "'" +
            ", amount=" + getAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", isIPF='" + isIsIPF() + "'" +
            "}";
    }
}
