package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.flag42.ibs.domain.enumeration.BenefitRate;

/**
 * A PolicyBenefit.
 */
@Entity
@Table(name = "policy_benefit")
public class PolicyBenefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "benefit_rate")
    private BenefitRate benefitRate;

    @Column(name = "benefit_value")
    private String benefitValue;

    @Column(name = "benefit_min_value")
    private Double benefitMinValue;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("policyBenefits")
    private Benefit benefit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("policyBenefits")
    private Policy policy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BenefitRate getBenefitRate() {
        return benefitRate;
    }

    public PolicyBenefit benefitRate(BenefitRate benefitRate) {
        this.benefitRate = benefitRate;
        return this;
    }

    public void setBenefitRate(BenefitRate benefitRate) {
        this.benefitRate = benefitRate;
    }

    public String getBenefitValue() {
        return benefitValue;
    }

    public PolicyBenefit benefitValue(String benefitValue) {
        this.benefitValue = benefitValue;
        return this;
    }

    public void setBenefitValue(String benefitValue) {
        this.benefitValue = benefitValue;
    }

    public Double getBenefitMinValue() {
        return benefitMinValue;
    }

    public PolicyBenefit benefitMinValue(Double benefitMinValue) {
        this.benefitMinValue = benefitMinValue;
        return this;
    }

    public void setBenefitMinValue(Double benefitMinValue) {
        this.benefitMinValue = benefitMinValue;
    }

    public Benefit getBenefit() {
        return benefit;
    }

    public PolicyBenefit benefit(Benefit benefit) {
        this.benefit = benefit;
        return this;
    }

    public void setBenefit(Benefit benefit) {
        this.benefit = benefit;
    }

    public Policy getPolicy() {
        return policy;
    }

    public PolicyBenefit policy(Policy policy) {
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
        if (!(o instanceof PolicyBenefit)) {
            return false;
        }
        return id != null && id.equals(((PolicyBenefit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PolicyBenefit{" +
            "id=" + getId() +
            ", benefitRate='" + getBenefitRate() + "'" +
            ", benefitValue='" + getBenefitValue() + "'" +
            ", benefitMinValue=" + getBenefitMinValue() +
            "}";
    }
}
