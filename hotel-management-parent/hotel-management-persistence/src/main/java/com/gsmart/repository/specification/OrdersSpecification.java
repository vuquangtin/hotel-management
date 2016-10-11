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
			predicates.add(cb.like(cb.lower(root.get(Orders_.customerName)),
					orderCreiteria.getCustomerName().toLowerCase() + "%"));
		}

		/**
		 * Find by customer ID.
		 */
		if (!StringUtils.isEmpty(orderCreiteria.getCustomerId()) && orderCreiteria.getCustomerId() != null) {
			predicates.add(cb.like(cb.lower(root.get(Orders_.customerId)), orderCreiteria.getCustomerId() + "%"));
		}

		/**
		 * Find all order have date created and checkout date between value we
		 * give.
		 */
		if (orderCreiteria.getCreatedAt() != null && orderCreiteria.getCheckOutAt() != null) {
			predicates.add(cb.or(cb.greaterThan(root.get(Orders_.createdAt), orderCreiteria.getCreatedAt()),
					cb.lessThan(root.get(Orders_.checkOutAt), orderCreiteria.getCheckOutAt())));
		}

		if (orderCreiteria.getRoom() != null) {
			/**
			 * Find by room name
			 */
			if (!StringUtils.isEmpty(orderCreiteria.getRoom().getName())
					&& orderCreiteria.getRoom().getName() != null) {
				predicates.add(cb.like(cb.lower(root.get(Orders_.room).get(Room_.name)), orderCreiteria.getRoom().getName() + "%"));
			}

			/**
			 * Find by room category
			 */
			if (!StringUtils.isEmpty(orderCreiteria.getRoom().getRoomCategory())
					&& orderCreiteria.getRoom().getRoomCategory() != null) {
				predicates.add(cb.equal(root.get(Orders_.room).get(Room_.roomCategory), orderCreiteria.getRoom().getRoomCategory()));
			}
		}
	
		return andTogether(predicates, cb);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
