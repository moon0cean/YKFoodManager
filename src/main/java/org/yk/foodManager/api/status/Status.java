package org.yk.foodManager.api.status;

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
@Table(name = "Status")
public class Status {

    @Id
    private String statusId;

    @Column(name = "description")
    private String description;

    public Status(String statusId) {
        this.statusId = statusId;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusId='" + statusId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
