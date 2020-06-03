package com.javaduckspizza.ftesters;

public abstract class BasicDaoFt {
	public BasicDaoFt() {
		super();
	}

	protected abstract void runTests();

	protected abstract void testInsert();

	protected abstract void testGetByPrimaryKey();

	protected abstract void testUpdate();

	protected abstract void testDelete();
}