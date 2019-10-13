package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.Status;

/**
 * A DTO for the {@link com.flag42.ibs.domain.Policy} entity.
 */
public class PolicyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double primiumPayable;

    private Status status;


    private Long coverTypeId;

    private Long underwriterId;

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

    public Double getPrimiumPayable() {
        return primiumPayable;
    }

    public void setPrimiumPayable(Double primiumPayable) {
        this.primiumPayable = primiumPayable;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCoverTypeId() {
        return coverTypeId;
    }

    public void setCoverTypeId(Long coverTypeId) {
        this.coverTypeId = coverTypeId;
    }

    public Long getUnderwriterId() {
        return underwriterId;
    }

    public void setUnderwriterId(Long underwriterId) {
        this.underwriterId = underwriterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PolicyDTO policyDTO = (PolicyDTO) o;
        if (policyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolicyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", primiumPayable=" + getPrimiumPayable() +
            ", status='" + getStatus() + "'" +
            ", coverType=" + getCoverTypeId() +
            ", underwriter=" + getUnderwriterId() +
            "}";
    }
}
