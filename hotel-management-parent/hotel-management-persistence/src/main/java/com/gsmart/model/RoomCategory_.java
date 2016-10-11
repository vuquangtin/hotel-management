package com.gsmart.model;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class RoomCategory_ {
	
	public static volatile SingularAttribute<RoomCategory, Integer> id;
    public static volatile SingularAttribute<RoomCategory, String> name;
    public static volatile SingularAttribute<RoomCategory, String> description;
    public static volatile SingularAttribute<RoomCategory, Double> price;
    public static volatile SingularAttribute<RoomCategory, Integer> capacity;
    public static volatile SetAttribute<RoomCategory, Room> listOrders;
}
