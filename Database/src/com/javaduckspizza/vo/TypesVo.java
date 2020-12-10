package com.javaduckspizza.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="TYPES", schema="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID"),
		@UniqueConstraint(columnNames = "SEQUENCE_CODE")})
@XmlRootElement(name = "types")
public class TypesVo implements Serializable, Comparable<TypesVo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1559415544173540893L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	@XmlElement
	private Long id;

	@Column(name="CATEGORY")
	@XmlElement
	private String category;

	@Column(name="DESCRIPTION")
	@XmlElement
	private String description;

	@Column(name="SEQUENCE_CODE")
	@XmlElement
	private String sequenceCode;

	@Column(name="IS_ACTIVE")
	@XmlElement
	private boolean active;

	public TypesVo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String type) {
		this.category = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSequenceCode() {
		return sequenceCode;
	}

	public void setSequenceCode(String code) {
		this.sequenceCode = code;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean equals(Object obj) {
		if((obj != null) && (obj instanceof TypesVo)) {
			TypesVo tv = (TypesVo) obj;
			return (this.id == tv.getId()) &&
					this.getCategory().equals(tv.getCategory()) &&
					this.getDescription().equals(tv.getDescription()) &&
					this.getSequenceCode().equals(tv.getSequenceCode()) &&
					(this.active == tv.isActive());
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ID: ").append(getId()).append('\t');
		sb.append("CATEGORY: ").append(getCategory()).append('\t');
		sb.append("DESCRIPTION: ").append(getDescription()).append('\t');
		sb.append("SEQUENCE_CODE: ").append(getSequenceCode()).append('\t');
		sb.append("IS_ACTIVE: ").append(isActive());
		
		return sb.toString();
	}

	@Override
	public TypesVo clone() {
		TypesVo typesVo = new TypesVo();
		typesVo.setActive(active);
		typesVo.setCategory(category);
		typesVo.setDescription(description);
		typesVo.setId(id);
		typesVo.setSequenceCode(sequenceCode);

		return typesVo;
	}

	@Override
	public int compareTo(TypesVo tv) {
		if((tv == null) || (tv.getDescription() == null)) {
			return 1;
		} else if(description == null) {
			return -1;
		}

		return description.compareTo(tv.getDescription());
	}

}