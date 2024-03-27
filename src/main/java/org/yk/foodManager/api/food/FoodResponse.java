package org.yk.foodManager.api.food;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoodResponse {
    String statusId;
    Long creationDate;
    Long lastUpdateDate;
}
