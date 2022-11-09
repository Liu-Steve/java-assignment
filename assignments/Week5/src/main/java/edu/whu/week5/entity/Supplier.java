package edu.whu.week5.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(columnDefinition = "VARCHAR(255)", insertable = false, updatable = false)
    @NonNull
    String id;

    String name;

    String phone;

    String email;

    @ManyToMany(mappedBy = "suppliers")
    List<Product> products;

}
