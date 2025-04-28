package com.tutorial.doce.controllers;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.doce.models.Product;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestBody; // <-- ¡Esta es la correcta!

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api")
public class ProductController {

// Simulación de datos en memoria
    private List<Product> products = new ArrayList<>();

    public ProductController() {
    // Productos de ejemplo
        products.add(new Product(1L, "Producto A", 10.0));
        products.add(new Product(2L, "Producto B", 20.0));
    }


    // Endpoint para listar todos los productos

    @GetMapping("/products")
    public ResponseEntity<List<Product>> index() {
        return ResponseEntity.ok(products);
    }

    // Endpoint para mostrar un producto por ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> show(@PathVariable Long id) {
        Product product = products.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @PostMapping("/products")
    public ResponseEntity<Product> create(@RequestBody Product newProduct) {
        // Log para ver qué se recibió realmente después de la conversión JSON -> Java
        log.info(">>> Producto Recibido: ID={}, Nombre={}, Precio={}",
                 newProduct.getId(), newProduct.getName(), newProduct.getPrice()); // <-- ¿QUÉ VALORES SE VEN AQUÍ?
    
        products.add(newProduct);
    
        // Log para ver qué se está a punto de devolver antes de la conversión Java -> JSON
        log.info("<<< Devolviendo Producto: ID={}, Nombre={}, Precio={}",
                 newProduct.getId(), newProduct.getName(), newProduct.getPrice()); // <-- ¿Y AQUÍ?
    
        return ResponseEntity.ok(newProduct);
    }

}