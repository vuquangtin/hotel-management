package com.gsmart.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class RoomCategory {
    private int id;
    private String name;
    private String description;
    private Double price;
    private Integer capacity;
    private Set<Room> rooms;

    public RoomCategory(){

    }

    public RoomCategory(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "roomCategory", cascade = CascadeType.ALL)
    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        String result = String.format(
                "RoomCategory[id=%d, name='%s']%n",
                id, name);
        if (rooms != null) {
            for(Room rooms : rooms) {
                result += String.format(
                        "Room[id=%d, name='%s']%n",
                        rooms.getId(), rooms.getName());
            }
        }

        return result;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
}