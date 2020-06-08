package com.javaduckspizza.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CUSTOMER")
public class CustomerVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="MIDDLE_NAME")
	private String middleName;
	@Column(name="LAST_NAME")
	private String lastName;
	@Column(name="STREET_ADDRESS_1")
	private String streetAddress1;
	@Column(name="STREET_ADDRESS_2")
	private String streetAddress2;
	@Column(name="CITY")
	private String city;
	@Column(name="ZIPCODE_5")
	private String zipcode5;
	@Column(name="ZIPCODE_4")
	private String zipcode4;
	@Column(name="PHONE")
	private String phone;
	@Column(name="STATE")
	private Long state;
	@Column(name="EMAIL")
	private String email;

	public CustomerVo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress1() {
		return streetAddress1;
	}

	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}

	public String getStreetAddress2() {
		return streetAddress2;
	}

	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode5() {
		return zipcode5;
	}

	public void setZipcode5(String zipcode5) {
		this.zipcode5 = zipcode5;
	}

	public String getZipcode4() {
		return zipcode4;
	}

	public void setZipcode4(String zipcode4) {
		this.zipcode4 = zipcode4;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public CustomerVo clone() {
		CustomerVo cv = new CustomerVo();
		cv.setCity(city);
		cv.setEmail(email);
		cv.setFirstName(firstName);
		cv.setId(id);
		cv.setLastName(lastName);
		cv.setMiddleName(middleName);
		cv.setPhone(phone);
		cv.setState(state);
		cv.setStreetAddress1(streetAddress1);
		cv.setStreetAddress2(streetAddress2);
		cv.setZipcode4(zipcode4);
		cv.setZipcode5(zipcode5);

		return cv;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID: ").append(id).append('\t');
		sb.append("FIRST_NAME: ").append(firstName).append('\t');
		sb.append("MIDDLE_NAME: ").append(middleName).append('\t');
		sb.append("LAST_NAME: ").append(lastName).append('\t');
		sb.append("PHONE: ").append(phone).append('\t');
		sb.append("STREET_ADDRESS_1: ").append(streetAddress1).append('\t');
		sb.append("STREET_ADDRESS_2: ").append(streetAddress2).append('\t');
		sb.append("CITY: ").append(city).append('\t');
		sb.append("STATE: ").append(state).append('\t');
		sb.append("ZIPCODE_5: ").append(zipcode5).append('\t');
		sb.append("ZIPCODE_4: ").append(zipcode4).append('\t');
		sb.append("E-MAIL: ").append(email).append('\t');
		return sb.toString();
	}
}