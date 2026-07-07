package com.example.product.service;

import com.example.product.dto.ProductReqDTO;
import com.example.product.dto.ProductResDTO;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class productService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResDTO createProduct(ProductResDTO productReqDTO) {
        Product product = new Product();
        updateProductWithReqDTOFormat(product , productReqDTO);
        Product savedProduct = productRepository.save(product);
        return mapProductToProductResDTO(savedProduct);
    }

    public String updateProductById(Long id , ProductResDTO productReqDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        updateProductWithReqDTOFormat(product , productReqDTO);
        productRepository.save(product);
        return "Product updated successfully";
    }

    public List<ProductResDTO> getProducts() {
        List<Product> products = productRepository.findByActiveTrue();
        return products.stream()
                .map(this::mapProductToProductResDTO)
                .collect(Collectors.toList());
    }

    public boolean deleteProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                })
                .orElse(false);
    }

    public List<ProductResDTO> searchProducts(String keyword) {
        List<Product> products = productRepository.searchProducts(keyword);
        return products.stream()
                .map(this::mapProductToProductResDTO)
                .collect(Collectors.toList());
    }



    public void updateProductWithReqDTOFormat(Product product ,ProductResDTO productReqDTO) {
       product.setName(productReqDTO.getName());
       product.setDescription(productReqDTO.getDescription());
       product.setPrice(productReqDTO.getPrice());
       product.setCategory(productReqDTO.getCategory());
       product.setStockQuantity(productReqDTO.getStockQuantity());
       product.setImageUrl(productReqDTO.getImageUrl());
    }

    public ProductResDTO mapProductToProductResDTO(Product savedProduct) {
        ProductResDTO response = new ProductResDTO();
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setCategory(savedProduct.getCategory());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setActive(savedProduct.isActive());
        return response;
    }

    public Optional<ProductResDTO> getProductById(Long id) {
        return productRepository.findByIdAndActiveTrue(id)
                .map(this::mapProductToProductResDTO);
    }
}
