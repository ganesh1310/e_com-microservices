package com.example.product.controller;

import com.example.product.dto.ProductResDTO;
import com.example.product.service.productService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final productService productService;

    @PostMapping("/addProduct")
    public ResponseEntity<String> createProduct(@RequestBody ProductResDTO productReqDTO) {
        productService.createProduct(productReqDTO);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable Long id , @RequestBody ProductResDTO productReqDTO) {
        String response = productService.updateProductById(id , productReqDTO);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductResDTO>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductResDTO> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/searchProducts")
    public ResponseEntity<List<ProductResDTO>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

}
