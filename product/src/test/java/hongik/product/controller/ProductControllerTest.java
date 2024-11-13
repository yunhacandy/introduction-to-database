package hongik.product.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import hongik.product.entity.Product;
import hongik.product.service.ProductService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        product1.setDescription("Description1");
        product1.setPrice(100.0);

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Product2");
        product2.setDescription("Description2");
        product2.setPrice(200.0);
    }

    @Test
    void 상품_등록() throws Exception {
        Product newProduct = new Product();
        newProduct.setName("Product3");
        newProduct.setDescription("Description3");
        newProduct.setPrice(300.0);

        doNothing().when(productService).saveProduct(Mockito.any(Product.class));   // 실제로는 저장 동작 수행x

        ResultActions result = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", newProduct.getName())
                .param("description", newProduct.getDescription())
                .param("price", String.valueOf(newProduct.getPrice()))
        );

        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    void 상품_목록_조회() throws Exception {
        List<Product> products = Arrays.asList(product1, product2);
        given(productService.findAllProducts()).willReturn(products);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk()) //상태 코드가 200인지 확인
                .andExpect(view().name("product"))  // 컨트롤러가 반환하는 뷰 이름이 product인가
                .andExpect(model().attributeExists("products")) // 뷰 모델에 products 속성이 존재하는지 검증
                .andExpect(model().attribute("products",
                        products));    // 모델에 추가된 products 속성 값이 테스트에서 준비한 products 목록과 동일한지 검증
    }

}