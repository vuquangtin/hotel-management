package com.gsmart.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gsmart.model.Orders;
import com.gsmart.model.Orders_;

public class ValidOrderSpecification implements Specification<Orders> {

	@Override
	public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		/**
		 * Valid status is not equal -1.
		 */
		predicates.add(cb.notEqual(root.get(Orders_.status), -1));
		
		return andTogether(predicates, cb);
	}
	
	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}

}
