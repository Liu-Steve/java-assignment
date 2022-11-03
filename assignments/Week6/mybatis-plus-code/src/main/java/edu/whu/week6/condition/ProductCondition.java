package edu.whu.week6.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCondition implements Serializable {

    private String name;
    private BigDecimal lowestPrice;
    private BigDecimal highestPrice;
    private Long lowestQuantity;
    private Long highestQuantity;
    private String supplierName;
    private String orderBy;
    private Boolean increase;

}
