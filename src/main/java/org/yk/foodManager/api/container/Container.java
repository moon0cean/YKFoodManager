package org.yk.foodManager.api.container;

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
@Table(name = "Container")
public class Container {

    @Id
    private String containerTypeId;

    @Column
    private String description;

    public Container(String containerTypeId) {
        this.containerTypeId = containerTypeId;
    }

    @Override
    public String toString() {
        return "Container{" +
                "containerTypeId='" + containerTypeId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
