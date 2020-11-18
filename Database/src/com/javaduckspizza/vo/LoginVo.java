package com.javaduckspizza.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="LOGIN", schema="public", uniqueConstraints= {
		@UniqueConstraint(columnNames="CUSTOMER_ID")})
public class LoginVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	@Column(name="PASSWORD")
	private String password;

	public LoginVo() {
		
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public LoginVo clone() {
		LoginVo login = new LoginVo();
		login.setCustomerId(getCustomerId());
		login.setPassword(getPassword());
		return login;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
}
