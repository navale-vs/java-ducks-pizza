package com.javaduckspizza.dao.interfaces;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;

import com.javaduckspizza.vo.ModifiersVo;

public interface IModifiersDao {
	public long insert(ModifiersVo cv, Session session);
	public int delete(long id, Session session);
	public int update(ModifiersVo cv, Session session);
	public ModifiersVo get(long id, Session session);
	public List<ModifiersVo> getByTypeId(long typeId, Session session);
	public List<ModifiersVo> getByDateActive(Date expiration, Session session);
	public List<ModifiersVo> getByDateExpired(Date expiration, Session session);
}
