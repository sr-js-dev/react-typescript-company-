package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.flag42.ibs.domain.enumeration.BenefitRate;

/**
 * A Benefit.
 */
@Entity
@Table(name = "benefit")
public class Benefit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "benefit_rate")
    private BenefitRate benefitRate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("benefits")
    private BenefitType benefitType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Benefit name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BenefitRate getBenefitRate() {
        return benefitRate;
    }

    public Benefit benefitRate(BenefitRate benefitRate) {
        this.benefitRate = benefitRate;
        return this;
    }

    public void setBenefitRate(BenefitRate benefitRate) {
        this.benefitRate = benefitRate;
    }

    public String getDescription() {
        return description;
    }

    public Benefit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BenefitType getBenefitType() {
        return benefitType;
    }

    public Benefit benefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
        return this;
    }

    public void setBenefitType(BenefitType benefitType) {
        this.benefitType = benefitType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Benefit)) {
            return false;
        }
        return id != null && id.equals(((Benefit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Benefit{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", benefitRate='" + getBenefitRate() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
