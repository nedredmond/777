package io.zcw.zipmint.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A Dashboard.
 */
@Entity
@Table(name = "dashboard")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dashboard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_transaction")
    private Instant transaction;

    @Column(name = "bills")
    private Instant bills;

    @Column(name = "accounts")
    private Instant accounts;

    @Column(name = "budget")
    private Instant budget;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransaction() {
        return transaction;
    }

    public Dashboard transaction(Instant transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Instant transaction) {
        this.transaction = transaction;
    }

    public Instant getBills() {
        return bills;
    }

    public Dashboard bills(Instant bills) {
        this.bills = bills;
        return this;
    }

    public void setBills(Instant bills) {
        this.bills = bills;
    }

    public Instant getAccounts() {
        return accounts;
    }

    public Dashboard accounts(Instant accounts) {
        this.accounts = accounts;
        return this;
    }

    public void setAccounts(Instant accounts) {
        this.accounts = accounts;
    }

    public Instant getBudget() {
        return budget;
    }

    public Dashboard budget(Instant budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Instant budget) {
        this.budget = budget;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dashboard dashboard = (Dashboard) o;
        if (dashboard.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dashboard.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dashboard{" +
            "id=" + getId() +
            ", transaction='" + getTransaction() + "'" +
            ", bills='" + getBills() + "'" +
            ", accounts='" + getAccounts() + "'" +
            ", budget='" + getBudget() + "'" +
            "}";
    }
}
