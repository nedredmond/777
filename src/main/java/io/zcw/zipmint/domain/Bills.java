package io.zcw.zipmint.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Bills.
 */
@Entity
@Table(name = "bills")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "payment_total")
    private Long paymentTotal;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_amount")
    private Double paymentAmount;

    @Column(name = "auto_pay")
    private Boolean autoPay;

    @OneToMany(mappedBy = "bills")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillItem> billItems = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("")
    private MoneyAccount moneyAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaymentTotal() {
        return paymentTotal;
    }

    public Bills paymentTotal(Long paymentTotal) {
        this.paymentTotal = paymentTotal;
        return this;
    }

    public void setPaymentTotal(Long paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Bills companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Bills dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public Bills paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public Bills paymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Boolean isAutoPay() {
        return autoPay;
    }

    public Bills autoPay(Boolean autoPay) {
        this.autoPay = autoPay;
        return this;
    }

    public void setAutoPay(Boolean autoPay) {
        this.autoPay = autoPay;
    }

    public Set<BillItem> getBillItems() {
        return billItems;
    }

    public Bills billItems(Set<BillItem> billItems) {
        this.billItems = billItems;
        return this;
    }

    public Bills addBillItems(BillItem billItem) {
        this.billItems.add(billItem);
        billItem.setBills(this);
        return this;
    }

    public Bills removeBillItems(BillItem billItem) {
        this.billItems.remove(billItem);
        billItem.setBills(null);
        return this;
    }

    public void setBillItems(Set<BillItem> billItems) {
        this.billItems = billItems;
    }

    public MoneyAccount getMoneyAccount() {
        return moneyAccount;
    }

    public Bills moneyAccount(MoneyAccount moneyAccount) {
        this.moneyAccount = moneyAccount;
        return this;
    }

    public void setMoneyAccount(MoneyAccount moneyAccount) {
        this.moneyAccount = moneyAccount;
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
        Bills bills = (Bills) o;
        if (bills.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bills.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bills{" +
            "id=" + getId() +
            ", paymentTotal=" + getPaymentTotal() +
            ", companyName='" + getCompanyName() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", autoPay='" + isAutoPay() + "'" +
            "}";
    }
}
