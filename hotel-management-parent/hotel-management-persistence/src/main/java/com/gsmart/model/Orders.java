package com.gsmart.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;




@Entity
public class Orders {

	private int id;
	private Date createdAt;
	private Date checkOutAt;
	private Date paidAt;
	private Double totalPrice;
	private Double paymentPrice;
	private Double prepay;
	private String customerName;
	private String customerAddress;
	private String customerTelephone;
	private String customerNotice;
	private String customerId;
	private String description;
	private byte gender;
	private Double promotion;
	private int status;

	private Room room;

	private int numberDate;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPaymentPrice() {
		return paymentPrice;
	}

	public void setPaymentPrice(Double paymentPrice) {
		this.paymentPrice = paymentPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}

	public String getCustomerNotice() {
		return customerNotice;
	}

	public void setCustomerNotice(String customerNotice) {
		this.customerNotice = customerNotice;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "orders_room_id")
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCheckOutAt() {
		return checkOutAt;
	}

	public Date getPaidAt() {
		return paidAt;
	}

	@Transient
	public String getRoomStatus() {
		switch (this.status) {
		case 0: {
			return "Watting";
		}
		case 1: {
			return "Received";
		}
		default:
			return "Unknown";
		}
	}
	
	@Transient
	public String getRoomName() {
		if(this.room != null) {
			return this.room.getName();
		}
		return "Not Found";
	}

	public void setCheckOutAt(Date checkOutAt) {
		this.checkOutAt = checkOutAt;
	}

	public void setPaidAt(Date paidAt) {
		this.paidAt = paidAt;
	}

	@Transient
	public int getNumberDate() {
		return numberDate;
	}

	public void setNumberDate(int numberDate) {
		this.numberDate = numberDate;
	}

	public byte getGender() {
		return gender;
	}

	public Double getPromotion() {
		return promotion;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public void setPromotion(Double promotion) {
		this.promotion = promotion;
	}

	public Double getPrepay() {
		return prepay;
	}

	public void setPrepay(Double prepay) {
		this.prepay = prepay;
	}

}
