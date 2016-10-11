package com.gsmart.model;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Room.class)
public class Room_ {
	
	public static volatile SingularAttribute<Room, Integer> id;
    public static volatile SingularAttribute<Room, String> name;
    public static volatile SingularAttribute<Room, Date> acreage;
    public static volatile SingularAttribute<Room, Date> description;
    public static volatile SingularAttribute<Room, RoomCategory> roomCategory;
    public static volatile SetAttribute<Room, Orders> listOrders;
}
