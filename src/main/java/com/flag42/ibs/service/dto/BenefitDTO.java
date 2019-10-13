package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.BenefitRate;

/**
 * A DTO for the {@link com.flag42.ibs.domain.Benefit} entity.
 */
public class BenefitDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private BenefitRate benefitRate;

    private String description;


    private Long benefitTypeId;

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

    public BenefitRate getBenefitRate() {
        return benefitRate;
    }

    public void setBenefitRate(BenefitRate benefitRate) {
        this.benefitRate = benefitRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBenefitTypeId() {
        return benefitTypeId;
    }

    public void setBenefitTypeId(Long benefitTypeId) {
        this.benefitTypeId = benefitTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BenefitDTO benefitDTO = (BenefitDTO) o;
        if (benefitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), benefitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BenefitDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", benefitRate='" + getBenefitRate() + "'" +
            ", description='" + getDescription() + "'" +
            ", benefitType=" + getBenefitTypeId() +
            "}";
    }
}
