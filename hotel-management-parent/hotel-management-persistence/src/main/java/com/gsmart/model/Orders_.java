package com.gsmart.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Orders.class)
public class Orders_ {
	
	public static volatile SingularAttribute<Orders, Integer> id;
    public static volatile SingularAttribute<Orders, Room> room;
    public static volatile SingularAttribute<Orders, Date> createdAt;
    public static volatile SingularAttribute<Orders, Date> checkOutAt;
    public static volatile SingularAttribute<Orders, Date> paidAt;
    public static volatile SingularAttribute<Orders, Double> totalPrice;
    public static volatile SingularAttribute<Orders, Double> paymentPrice;
    public static volatile SingularAttribute<Orders, Double> prepay;
    public static volatile SingularAttribute<Orders, String> customerName;
    public static volatile SingularAttribute<Orders, String> customerAddress;
    public static volatile SingularAttribute<Orders, String> customerTelephone;
    public static volatile SingularAttribute<Orders, String> customerNotice;
    public static volatile SingularAttribute<Orders, String> customerId;
    public static volatile SingularAttribute<Orders, String> description;
    public static volatile SingularAttribute<Orders, Byte> gender;
    public static volatile SingularAttribute<Orders, Double> promotion;
    public static volatile SingularAttribute<Orders, Integer> status;
    public static volatile SingularAttribute<Orders, Integer> numberDate;
}
