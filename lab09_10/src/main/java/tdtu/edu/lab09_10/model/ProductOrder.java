package tdtu.edu.lab09_10.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ProductOrderPK pk;
    private Integer quantity;
    @Builder
    public ProductOrder(Long id, ProductOrderPK pk, Integer quantity) {
        this.id = id;
        this.pk = pk;
        this.quantity = quantity;
    }
}
