package org.yk.foodManager.api.uom;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Uom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Uom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uomId;

    @Column(name = "uomType")
    private String uomType;

    @Column(name = "value")
    private String value;

    @Column(name = "description")
    private String description;

    public Uom(long uomId) {
        this.uomId = uomId;
    }

    @Override
    public String toString() {
        return "Uom{" +
                "uomId=" + uomId +
                ", uomType='" + uomType + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
