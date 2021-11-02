package com.albertsonspoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.albertsonspoc.domain.Response;
import com.albertsonspoc.dto.WishListCountChangeDto;
import com.albertsonspoc.dto.WishListDto;
import com.albertsonspoc.exception.ActionFailureException;
import com.albertsonspoc.service.WishListService;

@RestController
public class WishListController {

	@Autowired
	private WishListService wishListService;

	@PostMapping("/wishList")
	public void addToCart(@RequestBody WishListDto cartDto) throws ActionFailureException {
		wishListService.addToCart(cartDto);
	}

	@GetMapping("/wishList")
	public ResponseEntity<Response<WishListDto>> getAllProducts(@RequestParam("userName") String userName)
			throws ActionFailureException {
		Response<WishListDto> response = new Response<>();
		response.setData(wishListService.fetchCart(userName));
		response.setSuccessMessage("Success");
		response.setStatusCode("Success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/wishList/change")
	public void changeCartCount(@RequestBody WishListCountChangeDto changeDto) throws ActionFailureException {
		wishListService.changeCount(changeDto);
	}

}
