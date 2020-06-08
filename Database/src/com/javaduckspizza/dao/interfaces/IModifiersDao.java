package com.javaduckspizza.dao.interfaces;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.ModifierVo;

public interface IModifiersDao {
	public long insert(ModifierVo cv, Session session);
	public int delete(long id, Session session);
	public int update(ModifierVo cv, Session session);
	public ModifierVo get(long id, Session session);
	public List<ModifierVo> getByTypeId(long typeId, Session session);
	public List<ModifierVo> getByDateActive(Date expiration, Session session);
	public List<ModifierVo> getByDateExpired(Date expiration, Session session);
}
