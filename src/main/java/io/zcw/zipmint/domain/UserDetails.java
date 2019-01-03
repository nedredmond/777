package io.zcw.zipmint.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserDetails.
 */
@Entity
@Table(name = "user_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "net_worth")
    private Long netWorth;

    @Column(name = "total_cash")
    private Long totalCash;

    @OneToMany(mappedBy = "userDetails")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MoneyAccount> accounts = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNetWorth() {
        return netWorth;
    }

    public UserDetails netWorth(Long netWorth) {
        this.netWorth = netWorth;
        return this;
    }

    public void setNetWorth(Long netWorth) {
        this.netWorth = netWorth;
    }

    public Long getTotalCash() {
        return totalCash;
    }

    public UserDetails totalCash(Long totalCash) {
        this.totalCash = totalCash;
        return this;
    }

    public void setTotalCash(Long totalCash) {
        this.totalCash = totalCash;
    }

    public Set<MoneyAccount> getAccounts() {
        return accounts;
    }

    public UserDetails accounts(Set<MoneyAccount> moneyAccounts) {
        this.accounts = moneyAccounts;
        return this;
    }

    public UserDetails addAccounts(MoneyAccount moneyAccount) {
        this.accounts.add(moneyAccount);
        moneyAccount.setUserDetails(this);
        return this;
    }

    public UserDetails removeAccounts(MoneyAccount moneyAccount) {
        this.accounts.remove(moneyAccount);
        moneyAccount.setUserDetails(null);
        return this;
    }

    public void setAccounts(Set<MoneyAccount> moneyAccounts) {
        this.accounts = moneyAccounts;
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
        UserDetails userDetails = (UserDetails) o;
        if (userDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDetails{" +
            "id=" + getId() +
            ", netWorth=" + getNetWorth() +
            ", totalCash=" + getTotalCash() +
            "}";
    }
}
