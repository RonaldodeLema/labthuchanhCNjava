package tdtu.edu.lab09_10.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ProductOrderPK {
    @ManyToOne
    private Product products;
    @ManyToOne
    private Account accounts;
}
