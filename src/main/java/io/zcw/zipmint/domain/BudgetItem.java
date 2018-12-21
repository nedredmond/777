package io.zcw.zipmint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.zcw.zipmint.domain.enumeration.Category;

/**
 * A BudgetItem.
 */
@Entity
@Table(name = "budget_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BudgetItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "expected_spending")
    private Long expectedSpending;

    @Column(name = "actual_spending")
    private Long actualSpending;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties("budgetItems")
    private Budget budget;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpectedSpending() {
        return expectedSpending;
    }

    public BudgetItem expectedSpending(Long expectedSpending) {
        this.expectedSpending = expectedSpending;
        return this;
    }

    public void setExpectedSpending(Long expectedSpending) {
        this.expectedSpending = expectedSpending;
    }

    public Long getActualSpending() {
        return actualSpending;
    }

    public BudgetItem actualSpending(Long actualSpending) {
        this.actualSpending = actualSpending;
        return this;
    }

    public void setActualSpending(Long actualSpending) {
        this.actualSpending = actualSpending;
    }

    public Category getCategory() {
        return category;
    }

    public BudgetItem category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Budget getBudget() {
        return budget;
    }

    public BudgetItem budget(Budget budget) {
        this.budget = budget;
        return this;
    }

    public void setBudget(Budget budget) {
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
        BudgetItem budgetItem = (BudgetItem) o;
        if (budgetItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
            "id=" + getId() +
            ", expectedSpending=" + getExpectedSpending() +
            ", actualSpending=" + getActualSpending() +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
