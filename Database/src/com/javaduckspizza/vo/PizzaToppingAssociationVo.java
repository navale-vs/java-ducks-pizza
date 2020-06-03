package com.javaduckspizza.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PIZZA_TOPPING_ASSOCIATION")
public class PizzaToppingAssociationVo implements Serializable, Comparable<PizzaToppingAssociationVo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 330326951085517936L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false, unique=true)
	private Long id;
	@Column(name="PIZZA_ID", nullable=false)
	private Long pizzaId;
	@Column(name="TOPPINGS_ID", nullable=false)
	private Long toppingsId;
	@Column(name="LEFT_SIDE", nullable=false)
	private boolean left;
	@Column(name="RIGHT_SIDE", nullable=false)
	private boolean right;
	@ManyToOne
	@JoinColumn(name = "PIZZA_ID", nullable = false, insertable = false, updatable = false)
	private	PizzaVo pizza; 

	public PizzaToppingAssociationVo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPizzaId() {
		return pizzaId;
	}

	public void setPizzaId(Long pizzaId) {
		this.pizzaId = pizzaId;
	}

	public Long getToppingsId() {
		return toppingsId;
	}

	public void setToppingsId(Long toppingsId) {
		this.toppingsId = toppingsId;
	}

	public boolean getLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean getRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ID: ").append(id).append('\t');
		sb.append("PizzaId: " ).append(pizzaId).append('\t');
		sb.append("ToppingsId: " ).append(toppingsId).append('\t');
		sb.append("Left: " ).append(left).append('\t');
		sb.append("Right: " ).append(right).append('\t');
		return sb.toString();
	}

	public PizzaToppingAssociationVo clone() {
		PizzaToppingAssociationVo ptav = new PizzaToppingAssociationVo();
		ptav.setId(id);
		ptav.setLeft(left);
		ptav.setPizzaId(pizzaId);
		ptav.setRight(right);
		ptav.setToppingsId(toppingsId);
		
		return ptav;
	}

	@Override
	public int compareTo(PizzaToppingAssociationVo ptav) {
		if(ptav == null) {
			return 1;
		}

		return id.compareTo(ptav.getId());
	}
}