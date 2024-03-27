package org.yk.foodManager.api.food;

import org.yk.foodManager.api.container.Container;
import org.yk.foodManager.api.customer.Customer;
import org.yk.foodManager.api.foodType.FoodType;
import org.yk.foodManager.api.status.Status;
import org.yk.foodManager.api.uom.Uom;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "requiresCooler")
    private boolean requiresCooler;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "foodTypeId", referencedColumnName = "typeId", nullable = false)
    private FoodType foodTypeId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "capacity", referencedColumnName = "uomId", nullable = false)
    private Uom capacity;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "containerTypeId", referencedColumnName = "containerTypeId", nullable = false)
    private Container containerTypeId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "statusId", referencedColumnName = "statusId", nullable = false)
    private Status statusId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "createdBy", referencedColumnName = "userName", nullable = false)
    private Customer createdBy;

    @Column(name = "createdTimestamp")
    private Timestamp createdTimestamp;

    @Column(name = "lastUpdatedTimestamp")
    private Timestamp lastUpdatedTimestamp;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "lastUpdatedBy", referencedColumnName = "userName")
    private Customer lastUpdatedBy;

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", requiresCooler=" + requiresCooler +
                ", foodTypeId=" + foodTypeId +
                ", capacity=" + capacity +
                ", containerTypeId=" + containerTypeId +
                ", statusId=" + statusId +
                ", createdBy=" + createdBy +
                ", createdTimestamp=" + createdTimestamp +
                ", lastUpdatedTimestamp=" + lastUpdatedTimestamp +
                ", lastUpdatedBy=" + lastUpdatedBy +
                '}';
    }
}
