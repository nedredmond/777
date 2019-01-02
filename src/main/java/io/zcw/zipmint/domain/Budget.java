package io.zcw.zipmint.domain;
//test
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Budget.
 */
@Entity
@Table(name = "budget")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "expected_total")
    private Double expectedTotal;

    @Column(name = "actual_total")
    private Double actualTotal;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "budget")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BudgetItem> budgetItems = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getExpectedTotal() {
        return expectedTotal;
    }

    public Budget expectedTotal(Double expectedTotal) {
        this.expectedTotal = expectedTotal;
        return this;
    }

    public void setExpectedTotal(Double expectedTotal) {
        this.expectedTotal = expectedTotal;
    }

    public Double getActualTotal() {
        return actualTotal;
    }

    public Budget actualTotal(Double actualTotal) {
        this.actualTotal = actualTotal;
        return this;
    }

    public void setActualTotal(Double actualTotal) {
        this.actualTotal = actualTotal;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Budget startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Budget endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<BudgetItem> getBudgetItems() {
        return budgetItems;
    }

    public Budget budgetItems(Set<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
        return this;
    }

    public Budget addBudgetItems(BudgetItem budgetItem) {
        this.budgetItems.add(budgetItem);
        budgetItem.setBudget(this);
        return this;
    }

    public Budget removeBudgetItems(BudgetItem budgetItem) {
        this.budgetItems.remove(budgetItem);
        budgetItem.setBudget(null);
        return this;
    }

    public void setBudgetItems(Set<BudgetItem> budgetItems) {
        this.budgetItems = budgetItems;
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
        Budget budget = (Budget) o;
        if (budget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Budget{" +
            "id=" + getId() +
            ", expectedTotal=" + getExpectedTotal() +
            ", actualTotal=" + getActualTotal() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
