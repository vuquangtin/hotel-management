package com.gsmart.repository.specification;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.gsmart.model.Orders;
import com.gsmart.model.Orders_;

public class OrderReportSpecification {

	/**
	 * 
	 * @return
	 */
	public static Specification<Orders> getDailyReportSpecification() {
		return new Specification<Orders>() {

			@Override
			public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.set(Calendar.HOUR, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				
				//From 00:00:00
				Date fromDate = calendar.getTime();
				
				calendar.set(Calendar.HOUR, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				
				//To 23:59:59
				Date toDate = calendar.getTime();
				
				return cb.and(cb.greaterThanOrEqualTo(root.get(Orders_.createdAt), fromDate),
						cb.lessThanOrEqualTo(root.get(Orders_.checkOutAt), toDate));
			}
		};

	}

	/**
	 * 
	 * @return
	 */
	public static Specification<Orders> getOrderReportByDateSpecification(final Date fromDate, final Date toDate) {
		return new Specification<Orders>() {

			@Override
			public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				return cb.and(cb.greaterThanOrEqualTo(root.get(Orders_.createdAt), fromDate),
						cb.lessThanOrEqualTo(root.get(Orders_.checkOutAt), toDate));
			}
		};

	}
}
