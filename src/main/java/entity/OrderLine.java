package entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Mark
 */
@Entity
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ITEM_ID")
    private ItemType item;
    
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ORDER_ID")
    private OrderExamprep order;

    public OrderLine(int quanity) {
        this.quantity = quanity;
    }

    public OrderExamprep getOrder() {
        return order;
    }

    public OrderLine() {
    }

    public void setOrder(OrderExamprep order) {
        this.order = order;
    }
    
    

    
    public Double getOrderLinePrice() {
        return quantity*item.getPrice();
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ItemType getItem() {
        return item;
    }

    public void setItem(ItemType item) {
        this.item = item;
    }
    
    
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderLine)) {
            return false;
        }
        OrderLine other = (OrderLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderLine[ id=" + id + " ]";
    }
    
}
