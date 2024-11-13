package hongik.product.controller;

import hongik.product.entity.Product;
import hongik.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 목록 조회
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        log.info("상품 목록 조회: {}개의 상품을 로드했습니다.", products.size());
        return "product"; // product.html
    }

    // 상품 등록
    @PostMapping("/products")
    public String addProduct(@ModelAttribute Product product) {
        log.info("상품 등록 요청: name={}, description={}, price={}", product.getName(), product.getDescription(), product.getPrice());
        productService.saveProduct(product);
        log.info("상품 등록 완료: name={}", product.getName());
        return "redirect:/products"; // 상품 목록 페이지로 리다이렉트
    }

    // 상품 삭제
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        log.info("상품 삭제 요청: id={}", id);
        productService.deleteProduct(id);
        log.info("상품 삭제 완료: id={}", id);
        return "redirect:/products"; // 상품 목록 페이지로 리다이렉트
    }
}
