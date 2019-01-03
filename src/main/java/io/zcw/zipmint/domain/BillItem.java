package io.zcw.zipmint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A BillItem.
 */
@Entity
@Table(name = "bill_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_amount")
    private Long paymentAmount;

    @Column(name = "auto_pay")
    private Boolean autoPay;

    @ManyToOne
    @JsonIgnoreProperties("billItems")
    private Bills bills;

    @ManyToOne
    @JsonIgnoreProperties("")
    private MoneyAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BillItem companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BillItem dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public BillItem paymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public BillItem paymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
        return this;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Boolean isAutoPay() {
        return autoPay;
    }

    public BillItem autoPay(Boolean autoPay) {
        this.autoPay = autoPay;
        return this;
    }

    public void setAutoPay(Boolean autoPay) {
        this.autoPay = autoPay;
    }

    public Bills getBills() {
        return bills;
    }

    public BillItem bills(Bills bills) {
        this.bills = bills;
        return this;
    }

    public void setBills(Bills bills) {
        this.bills = bills;
    }

    public MoneyAccount getAccount() {
        return account;
    }

    public BillItem account(MoneyAccount moneyAccount) {
        this.account = moneyAccount;
        return this;
    }

    public void setAccount(MoneyAccount moneyAccount) {
        this.account = moneyAccount;
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
        BillItem billItem = (BillItem) o;
        if (billItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillItem{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentAmount=" + getPaymentAmount() +
            ", autoPay='" + isAutoPay() + "'" +
            "}";
    }
}
