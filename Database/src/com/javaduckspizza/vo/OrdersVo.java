package com.javaduckspizza.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ORDERS")
public class OrdersVo implements Serializable, Comparable<OrdersVo>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6998541744518061169L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	@Column(name="METHOD")
	private Long method; //CARRYOUT, DINE-IN, PICKUP
	@Column(name="TOTAL")
	private BigDecimal total;
	@Column(name="STATUS") //FROM TYPES TABLE
	private Long status;
	@Column(name="DATE_TIME_PLACED")
	private Timestamp dateTimePlaced;
	@Column(name="DATE_TIME_DUE")
	private Timestamp dateTimeDue;
	@Column(name="DATE_TIME_READY")
	private Timestamp dateTimeReady;
	@Column(name="DATE_TIME_COMPLETED")
	private Timestamp dateTimeCompleted;

	public OrdersVo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getMethod() {
		return method;
	}

	public void setMethod(Long method) {
		this.method = method;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Timestamp getDateTimePlaced() {
		return dateTimePlaced;
	}

	public void setDateTimePlaced(Timestamp dateTimePlaced) {
		this.dateTimePlaced = dateTimePlaced;
	}

	public Timestamp getDateTimeDue() {
		return dateTimeDue;
	}

	public void setDateTimeDue(Timestamp dateTimeDue) {
		this.dateTimeDue = dateTimeDue;
	}

	public Timestamp getDateTimeReady() {
		return dateTimeReady;
	}

	public void setDateTimeReady(Timestamp dateTimeReady) {
		this.dateTimeReady = dateTimeReady;
	}

	public Timestamp getDateTimeCompleted() {
		return dateTimeCompleted;
	}

	public void setDateTimeCompleted(Timestamp dateTimeCompleted) {
		this.dateTimeCompleted = dateTimeCompleted;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID: ").append(id).append('\t');
		sb.append("CUSTOMER_ID: ").append(customerId).append('\t');
		sb.append("METHOD: ").append(method).append('\t');
		sb.append("TOTAL: ").append(total).append('\t');
		sb.append("STATUS: ").append(status).append('\t');
		sb.append("DATE_TIME_PLACED: ").append(dateTimePlaced).append('\t');
		sb.append("DATE_TIME_DUE: ").append(dateTimeDue).append('\t');
		sb.append("DATE_TIME_READY: ").append(dateTimeReady).append('\t');
		sb.append("DATE_TIME_COMPLETED: ").append(dateTimeCompleted).append('\t');

		return sb.toString();
	}

	@Override
	public OrdersVo clone() {
		OrdersVo ov = new OrdersVo();
		ov.setCustomerId(customerId);
		ov.setDateTimeCompleted(dateTimeCompleted);
		ov.setDateTimeDue(dateTimeDue);
		ov.setDateTimePlaced(dateTimePlaced);
		ov.setDateTimeReady(dateTimeReady);
		ov.setId(id);
		ov.setMethod(method);
		ov.setStatus(status);
		ov.setTotal(total);

		return ov;
	}

	@Override
	public int compareTo(OrdersVo ov) {
		if(ov == null) {
			return 1;
		}

		return (ov.getId().compareTo(ov.getId()));
	}
}