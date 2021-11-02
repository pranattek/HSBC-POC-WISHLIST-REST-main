package com.albertsonspoc.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.albertsonspoc.dto.WishListCountChangeDto;
import com.albertsonspoc.dto.WishListDto;
import com.albertsonspoc.exception.ActionFailureException;
import com.albertsonspoc.model.Product;
import com.albertsonspoc.model.User;
import com.albertsonspoc.model.WishList;
import com.albertsonspoc.repository.ProductRepository;
import com.albertsonspoc.repository.UserRepository;
import com.albertsonspoc.repository.WishListRepository;

@Service
public class WishListService {

	@Autowired
	private WishListRepository wishListRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	public List<WishListDto> fetchCart(String userName) throws ActionFailureException {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new ActionFailureException("User not found"));
		List<WishList> wishList = wishListRepository.findByUser(user);
		return wishList.stream().map((wish -> {
			float pricePerUnit = wish.getProduct().getPricePerUnit();
			return new WishListDto(wish.getId(), wish.getProduct().getId(), wish.getUser().getUserName(),
					wish.getQuantity(), pricePerUnit, wish.getProduct().getName(), wish.getProduct().getImageUrl());
		})).collect(Collectors.toList());
	}

	public void addToCart(WishListDto wishListDto) throws ActionFailureException {
		Product product = productRepository.findById(wishListDto.getProductId())
				.orElseThrow(() -> new ActionFailureException("Product not found"));
		User user = userRepository.findByUserName(wishListDto.getUserName())
				.orElseThrow(() -> new ActionFailureException("User not found"));
		WishList wishList = wishListRepository.findByProductAndUser(product, user).orElse(new WishList());
		if (product.getStock() < 1 + wishList.getQuantity())
			throw new ActionFailureException("Not enough stock");
		wishList.setUser(user);
		wishList.setProduct(product);
		wishList.setOrderPlaced(false);
		wishList.setPricePerUnit(product.getPricePerUnit());
		wishList.setQuantity(wishList.getQuantity() + 1);
		wishListRepository.save(wishList);
	}

	public void changeCount(WishListCountChangeDto changeDto) throws ActionFailureException {
		WishList cart = wishListRepository.findById(changeDto.getId())
				.orElseThrow(() -> new ActionFailureException("Cart Item not found"));
		wishListRepository.delete(cart);
	}

	public void updateCart() {

	}

}
