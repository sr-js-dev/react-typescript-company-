package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.flag42.ibs.domain.RiskClass} entity.
 */
public class RiskClassDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;


    private Long riskCategoryId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRiskCategoryId() {
        return riskCategoryId;
    }

    public void setRiskCategoryId(Long riskCategoryId) {
        this.riskCategoryId = riskCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RiskClassDTO riskClassDTO = (RiskClassDTO) o;
        if (riskClassDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), riskClassDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RiskClassDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", riskCategory=" + getRiskCategoryId() +
            "}";
    }
}
