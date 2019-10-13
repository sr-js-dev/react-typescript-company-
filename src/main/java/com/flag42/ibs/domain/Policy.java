package com.flag42.ibs.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.flag42.ibs.domain.enumeration.Status;

/**
 * A Policy.
 */
@Entity
@Table(name = "policy")
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "primium_payable", nullable = false)
    private Double primiumPayable;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("policies")
    private CoverType coverType;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("policies")
    private Underwriter underwriter;

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

    public Policy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrimiumPayable() {
        return primiumPayable;
    }

    public Policy primiumPayable(Double primiumPayable) {
        this.primiumPayable = primiumPayable;
        return this;
    }

    public void setPrimiumPayable(Double primiumPayable) {
        this.primiumPayable = primiumPayable;
    }

    public Status getStatus() {
        return status;
    }

    public Policy status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public Policy coverType(CoverType coverType) {
        this.coverType = coverType;
        return this;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }

    public Underwriter getUnderwriter() {
        return underwriter;
    }

    public Policy underwriter(Underwriter underwriter) {
        this.underwriter = underwriter;
        return this;
    }

    public void setUnderwriter(Underwriter underwriter) {
        this.underwriter = underwriter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Policy)) {
            return false;
        }
        return id != null && id.equals(((Policy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Policy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", primiumPayable=" + getPrimiumPayable() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
