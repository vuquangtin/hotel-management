package com.gsmart.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.gsmart.model.Orders;
import com.gsmart.model.Orders_;
import com.gsmart.model.Room_;

public class OrdersSpecification implements Specification<Orders> {

	private Orders orderCreiteria;

	public OrdersSpecification(Orders orderCreiteria) {
		this.orderCreiteria = orderCreiteria;
	}

	@Override
	public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		/**
		 * Find by customer name.
		 */
		if (!StringUtils.isEmpty(orderCreiteria.getCustomerName()) && orderCreiteria.getCustomerName() != null) {
			System.out.println("Search By Customer Name.");
			predicates.add(cb.like(cb.lower(root.get(Orders_.customerName)),
					"%" + orderCreiteria.getCustomerName().toLowerCase() + "%"));
		}

		/**
		 * Find by customer ID.
		 */
		if (!StringUtils.isEmpty(orderCreiteria.getCustomerId()) && orderCreiteria.getCustomerId() != null) {
			System.out.println("Search By Customer ID.");
			predicates.add(cb.like(cb.lower(root.get(Orders_.customerId)), "%" + orderCreiteria.getCustomerId() + "%"));
		}

		/**
		 * Find all order have date created and checkout date between value we
		 * give.
		 */
		if (orderCreiteria.getCreatedAt() != null && orderCreiteria.getCheckOutAt() != null) {
			System.out.println("Search By Pair Date.");
			predicates.add(cb.and(cb.greaterThanOrEqualTo(root.get(Orders_.createdAt), orderCreiteria.getCreatedAt()),
					cb.lessThanOrEqualTo(root.get(Orders_.checkOutAt), orderCreiteria.getCheckOutAt())));
		}
		
		/**
		 * Find all order had date created equal or greater.
		 */
		if(orderCreiteria.getCreatedAt() != null && orderCreiteria.getCheckOutAt() == null) {
			System.out.println("Search By Created Date.");
			predicates.add(cb.greaterThanOrEqualTo(root.get(Orders_.createdAt), orderCreiteria.getCreatedAt()));
		}
		
		/**
		 * Find all order had date checkout equal or less than.
		 */
		if(orderCreiteria.getCreatedAt() == null && orderCreiteria.getCheckOutAt() != null) {
			System.out.println("Search By Checkout Date.");
			predicates.add(cb.lessThanOrEqualTo(root.get(Orders_.checkOutAt), orderCreiteria.getCheckOutAt()));
		}

		if (orderCreiteria.getRoom() != null) {
			/**
			 * Find by room name
			 */
			if (!StringUtils.isEmpty(orderCreiteria.getRoom().getName())
					&& orderCreiteria.getRoom().getName() != null) {
				System.out.println("Search By Room Name.");
				predicates.add(cb.like(cb.lower(root.get(Orders_.room).get(Room_.name)),
						"%" + orderCreiteria.getRoom().getName() + "%"));
			}

			/**
			 * Find by room category
			 */
			if (!StringUtils.isEmpty(orderCreiteria.getRoom().getRoomCategory())
					&& orderCreiteria.getRoom().getRoomCategory() != null) {
				System.out.println("Search By Room Category.");
				predicates.add(cb.equal(root.get(Orders_.room).get(Room_.roomCategory),
						orderCreiteria.getRoom().getRoomCategory()));
			}
		}

		return andTogether(predicates, cb);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
