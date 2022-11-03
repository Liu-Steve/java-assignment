package edu.whu.week6.condition;

import edu.whu.week6.entity.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierCondition {

    private String name;
    private String email;
    private String phone;
    private String orderBy;
    private Boolean increase;

}
