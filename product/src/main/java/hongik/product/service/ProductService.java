package hongik.product.service;

import hongik.product.entity.Product;
import hongik.product.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 모든 상품 목록 조회
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 상품 저장
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
