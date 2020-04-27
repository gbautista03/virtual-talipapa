package ph.virtual.talipapa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "order_number", nullable = false, unique = true)
    private Integer orderNumber;

    @NotNull
    @Column(name = "order_date", nullable = false)
    private Instant orderDate;

    @Column(name = "process_date")
    private LocalDate processDate;

    @OneToOne
    @JoinColumn(unique = true)
    private UserExtra handler;

    @OneToOne
    @JoinColumn(unique = true)
    private Discount discount;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LineItem> items = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public Order orderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public Order orderDate(Instant orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public Order processDate(LocalDate processDate) {
        this.processDate = processDate;
        return this;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    public UserExtra getHandler() {
        return handler;
    }

    public Order handler(UserExtra userExtra) {
        this.handler = userExtra;
        return this;
    }

    public void setHandler(UserExtra userExtra) {
        this.handler = userExtra;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Order discount(Discount discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Set<LineItem> getItems() {
        return items;
    }

    public Order items(Set<LineItem> lineItems) {
        this.items = lineItems;
        return this;
    }

    public Order addItems(LineItem lineItem) {
        this.items.add(lineItem);
        lineItem.setOrder(this);
        return this;
    }

    public Order removeItems(LineItem lineItem) {
        this.items.remove(lineItem);
        lineItem.setOrder(null);
        return this;
    }

    public void setItems(Set<LineItem> lineItems) {
        this.items = lineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderNumber=" + getOrderNumber() +
            ", orderDate='" + getOrderDate() + "'" +
            ", processDate='" + getProcessDate() + "'" +
            "}";
    }
}
