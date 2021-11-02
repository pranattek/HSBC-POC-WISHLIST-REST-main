package com.albertsonspoc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.albertsonspoc.model.Product;
import com.albertsonspoc.model.User;
import com.albertsonspoc.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
	@Query("Select c FROM WishList c WHERE c.orderPlaced = false AND c.product = ?1 AND c.user=?2")
	public Optional<WishList> findByProductAndUser(Product product, User user);

	@Query("Select c FROM WishList c WHERE c.orderPlaced = false")
	public List<WishList> findByUser(User user);
}
