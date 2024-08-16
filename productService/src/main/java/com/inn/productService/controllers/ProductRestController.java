package com.inn.productService.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inn.productService.dto.Coupons;
import com.inn.productService.model.Product;
import com.inn.productService.repos.ProductRepo;

@RestController
@RequestMapping("/ProductApi")
public class ProductRestController {
	
	private ProductRepo productRepo;
	
	@Autowired
	public void setProductRepo(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${couponService.URL}")
	private String couponServiceURL;
	
	@PostMapping("/product")
	public Product createProduct(@RequestBody Product product) {
		try {
			couponServiceURL = couponServiceURL + product.getCouponCode();
			Coupons coupon = restTemplate.getForObject(couponServiceURL, Coupons.class);
			product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
			return this.productRepo.save(product);
		}catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable Long id) {
		Optional<Product> optionalProduct = this.productRepo.findById(id);
		return optionalProduct.isPresent() ? optionalProduct.get(): null;
	}
}
