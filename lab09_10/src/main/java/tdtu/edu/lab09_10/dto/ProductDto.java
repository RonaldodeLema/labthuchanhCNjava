package tdtu.edu.lab09_10.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long code;
    private String productName;
    private Double price;
    private String illustration;
    private String description;
//    @OneToMany(mappedBy = "products",fetch = FetchType.EAGER)
//    @JsonBackReference
//    private List<ProductOrder> productOrders;
    @Builder
    public ProductDto(Long code, String productName, Double price, String illustration, String description) {
        this.code = code;
        this.productName = productName;
        this.price = price;
        this.illustration = illustration;
        this.description = description;
    }
}
