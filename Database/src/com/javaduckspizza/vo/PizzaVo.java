package com.javaduckspizza.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="PIZZA", schema="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID")})
public class PizzaVo implements Serializable, Comparable<PizzaVo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6293857617581676800L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="CRUST")
	private Long crust;
	@Column(name="SAUCE")
	private Long sauce;
	@Column(name="SIZE")
	private Long size;
	@Column(name="ORDER_ID")
	private Long orderId;
	@Column(name="STATUS")
	private Long status;
	@Column(name="PRICE")
	private BigDecimal price;
	//	@OneToMany(mappedBy = "pizza")
	//	private Set<PizzaToppingAssociationVo> toppings;
	@ManyToOne
	@JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)
	private OrdersVo orderVo;

	public PizzaVo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCrust() {
		return crust;
	}

	public void setCrust(Long crust) {
		this.crust = crust;
	}

	public Long getSauce() {
		return sauce;
	}

	public void setSauce(Long sauce) {
		this.sauce = sauce;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public PizzaVo clone() {
		PizzaVo pvClone = new PizzaVo();
		pvClone.setId(getId());
		pvClone.setCrust(getCrust());
		pvClone.setSize(getSize());
		pvClone.setSauce(getSauce());
		pvClone.setOrderId(getOrderId());
		pvClone.setPrice(getPrice());
		pvClone.setStatus(getStatus());

		return pvClone;
	}

	@Override
	public int compareTo(PizzaVo pv) {
		if(pv == null) {
			return 1;
		}

		return 0;
	}
}