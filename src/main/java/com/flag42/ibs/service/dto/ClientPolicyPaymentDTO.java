package com.flag42.ibs.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.PaymentMethod;

/**
 * A DTO for the {@link com.flag42.ibs.domain.ClientPolicyPayment} entity.
 */
public class ClientPolicyPaymentDTO implements Serializable {

    private Long id;

    private LocalDate payDate;

    private Double amount;

    private PaymentMethod paymentMethod;

    private Boolean isIPF;


    private Long clientPolicyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean isIsIPF() {
        return isIPF;
    }

    public void setIsIPF(Boolean isIPF) {
        this.isIPF = isIPF;
    }

    public Long getClientPolicyId() {
        return clientPolicyId;
    }

    public void setClientPolicyId(Long clientPolicyId) {
        this.clientPolicyId = clientPolicyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientPolicyPaymentDTO clientPolicyPaymentDTO = (ClientPolicyPaymentDTO) o;
        if (clientPolicyPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clientPolicyPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClientPolicyPaymentDTO{" +
            "id=" + getId() +
            ", payDate='" + getPayDate() + "'" +
            ", amount=" + getAmount() +
            ", paymentMethod='" + getPaymentMethod() + "'" +
            ", isIPF='" + isIsIPF() + "'" +
            ", clientPolicy=" + getClientPolicyId() +
            "}";
    }
}
