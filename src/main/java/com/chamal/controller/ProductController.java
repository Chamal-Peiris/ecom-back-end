package com.chamal.controller;

import com.chamal.dto.ProductDto;
import com.chamal.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    public ResponseEntity saveProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.save(productDto));
    }
    @PutMapping("/update")
    public ResponseEntity updateProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.ok(productService.save(productDto));
    }

    @PutMapping("/{productId}/quantity/{amount}")
    public ResponseEntity updateStock(@PathVariable String productId,@PathVariable String amount){
        return ResponseEntity.ok(productService.updateStockAmount(Long.parseLong(productId),Long.parseLong(amount)));
    }

    @GetMapping("/get-product/{productId}")
    public ResponseEntity getProduct(@PathVariable String productId){
        return ResponseEntity.ok(productService.getProduct(Long.parseLong(productId)));
    }

    @GetMapping()
    public ResponseEntity getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

}
