package com.pool.curso.springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pool.curso.springboot.app.springbootcrud.entities.Product;
import com.pool.curso.springboot.app.springbootcrud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository pRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) pRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return pRepository.findById(id);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return pRepository.save(product);
    }
    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        Optional<Product> productOp = pRepository.findById(id);
        if (productOp.isPresent()) {
            Product productDb = productOp.orElseThrow();

            productDb.setSku(product.getSku());
            productDb.setName(product.getName());
            productDb.setDescription(product.getDescription());
            productDb.setPrice(product.getPrice());
            return Optional.of(pRepository.save(productDb));
        }
        return productOp;
    }

    @Override
    @Transactional
    public Optional<Product> delete(Long id) {
        Optional<Product> productOp = pRepository.findById(id);
        productOp.ifPresent(productDb -> {
            pRepository.delete(productDb);
        });
        return productOp;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsBySku(String sku) {
        return pRepository.existsBySku(sku);
    }

}
