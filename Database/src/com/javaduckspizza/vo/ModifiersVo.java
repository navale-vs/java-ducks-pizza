package com.javaduckspizza.vo;

import java.math.BigDecimal;
import java.sql.Date;

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
@Table(name="MODIFIERS", schema="public", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ID")})
public class ModifiersVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="TYPE_ID")
	private Long typeId;
	@Column(name="VALUE")
	private BigDecimal value;
	@Column(name="DATE_ACTIVE")
	private Date dateActive;
	@Column(name="DATE_EXPIRED")
	private Date dateExpired;
	@ManyToOne
	@JoinColumn(name = "TYPE_ID", nullable = false, insertable = false, updatable = false)
	private TypesVo typesVo;

	public ModifiersVo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getDateActive() {
		return dateActive;
	}

	public void setDateActive(Date dateActive) {
		this.dateActive = dateActive;
	}

	public Date getDateExpired() {
		return dateExpired;
	}

	public void setDateExpired(Date dateExpired) {
		this.dateExpired = dateExpired;
	}

	public TypesVo getTypesVo() {
		return typesVo;
	}

	public void setTypesVo(TypesVo typesVo) {
		this.typesVo = typesVo;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ID: ").append(getId()).append('\t');
		sb.append("TYPE_ID: ").append(getTypeId()).append('\t');
		sb.append("VALUE: ").append(getValue()).append('\t');
		sb.append("DATE_ACTIVE: ").append(getDateActive()).append('\t');
		sb.append("DATE_EXPIRED: ").append(getDateExpired());
		
		return sb.toString();
	}

	@Override()
	public ModifiersVo clone() {
		ModifiersVo mvClone = new ModifiersVo();
		mvClone.setDateActive(dateActive);
		mvClone.setDateExpired(dateExpired);
		mvClone.setId(id);
		mvClone.setTypeId(typeId);
		mvClone.setTypesVo(typesVo);
		mvClone.setValue(value);

		return mvClone;
	}
}