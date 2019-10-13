package com.flag42.ibs.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.flag42.ibs.domain.enumeration.BenefitRate;

/**
 * A DTO for the {@link com.flag42.ibs.domain.PolicyBenefit} entity.
 */
public class PolicyBenefitDTO implements Serializable {

    private Long id;

    private BenefitRate benefitRate;

    private String benefitValue;

    private Double benefitMinValue;


    private Long benefitId;

    private Long policyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BenefitRate getBenefitRate() {
        return benefitRate;
    }

    public void setBenefitRate(BenefitRate benefitRate) {
        this.benefitRate = benefitRate;
    }

    public String getBenefitValue() {
        return benefitValue;
    }

    public void setBenefitValue(String benefitValue) {
        this.benefitValue = benefitValue;
    }

    public Double getBenefitMinValue() {
        return benefitMinValue;
    }

    public void setBenefitMinValue(Double benefitMinValue) {
        this.benefitMinValue = benefitMinValue;
    }

    public Long getBenefitId() {
        return benefitId;
    }

    public void setBenefitId(Long benefitId) {
        this.benefitId = benefitId;
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

        PolicyBenefitDTO policyBenefitDTO = (PolicyBenefitDTO) o;
        if (policyBenefitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), policyBenefitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PolicyBenefitDTO{" +
            "id=" + getId() +
            ", benefitRate='" + getBenefitRate() + "'" +
            ", benefitValue='" + getBenefitValue() + "'" +
            ", benefitMinValue=" + getBenefitMinValue() +
            ", benefit=" + getBenefitId() +
            ", policy=" + getPolicyId() +
            "}";
    }
}
