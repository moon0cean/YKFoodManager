package org.yk.foodManager.api.foodType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FoodType")
public class FoodType {

    @Id
    private String typeId;

    @Column(name = "description")
    private String description;

    public FoodType(String typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "FoodType{" +
                "typeId='" + typeId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
