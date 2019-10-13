package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CoverType.
 */
@Entity
@Table(name = "cover_type")
public class CoverType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "broker_commission", nullable = false)
    private Double brokerCommission;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("coverTypes")
    private RiskClass riskClass;

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

    public CoverType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBrokerCommission() {
        return brokerCommission;
    }

    public CoverType brokerCommission(Double brokerCommission) {
        this.brokerCommission = brokerCommission;
        return this;
    }

    public void setBrokerCommission(Double brokerCommission) {
        this.brokerCommission = brokerCommission;
    }

    public String getDescription() {
        return description;
    }

    public CoverType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RiskClass getRiskClass() {
        return riskClass;
    }

    public CoverType riskClass(RiskClass riskClass) {
        this.riskClass = riskClass;
        return this;
    }

    public void setRiskClass(RiskClass riskClass) {
        this.riskClass = riskClass;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoverType)) {
            return false;
        }
        return id != null && id.equals(((CoverType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CoverType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", brokerCommission=" + getBrokerCommission() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
