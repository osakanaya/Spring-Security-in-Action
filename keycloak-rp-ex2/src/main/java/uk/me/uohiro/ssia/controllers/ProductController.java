package uk.me.uohiro.ssia.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import uk.me.uohiro.ssia.service.ProductService;

@Controller
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public String getProducts(Model model, @AuthenticationPrincipal OAuth2User principal) {
		model.addAttribute("products", productService.getProducts());

		logger.info(principal.toString());
		
		return "products";
	}
}
