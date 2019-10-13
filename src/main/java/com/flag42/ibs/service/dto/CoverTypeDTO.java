package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.flag42.ibs.domain.CoverType} entity.
 */
public class CoverTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double brokerCommission;

    private String description;


    private Long riskClassId;

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

    public Double getBrokerCommission() {
        return brokerCommission;
    }

    public void setBrokerCommission(Double brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRiskClassId() {
        return riskClassId;
    }

    public void setRiskClassId(Long riskClassId) {
        this.riskClassId = riskClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoverTypeDTO coverTypeDTO = (CoverTypeDTO) o;
        if (coverTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coverTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoverTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brokerCommission=" + getBrokerCommission() +
            ", description='" + getDescription() + "'" +
            ", riskClass=" + getRiskClassId() +
            "}";
    }
}
