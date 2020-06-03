package com.javaduckspizza.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TYPES_MODIFIERS_ASSOCIATION")
public class TypesModifiersAssociationVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	@Column(name="TYPES_ID")
	private Long typesId;
	@Column(name="MODIFIERS_ID")
	private Long modifiersId;
	@ManyToOne
	@JoinColumn(name = "TYPES_ID", nullable = false, insertable = false, updatable = false)
	private TypesVo typesVo;
	@ManyToOne
	@JoinColumn(name = "MODIFIERS_ID", nullable = false, insertable = false, updatable = false)
	private ModifiersVo modifiersVo;

	public TypesModifiersAssociationVo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTypeId() {
		return typesId;
	}

	public void setTypeId(Long typeId) {
		this.typesId = typeId;
	}

	public Long getModifiersId() {
		return modifiersId;
	}

	public void setModifiersId(Long modifierTypeId) {
		this.modifiersId = modifierTypeId;
	}

	@Override
	public TypesModifiersAssociationVo clone() {
		TypesModifiersAssociationVo tmav = new TypesModifiersAssociationVo();
		tmav.setTypeId(typesId);
		tmav.setId(id);
		tmav.setModifiersId(modifiersId);

		return tmav;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ID: ").append(id).append("\t");
		sb.append("BASE_TYPE_ID: ").append(typesId).append("\t");
		sb.append("MODIFIERS_TYPE_ID: ").append(modifiersId);
		
		return sb.toString();
	}
}