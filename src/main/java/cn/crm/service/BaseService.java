package cn.crm.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {
	
	public String save(T entity);
	
	public String delete(Object key);
	
	public String update(T entity);
	
	public T findByPrimaryKey(Object key);

	public T findByObject(T entity);
	
	public List<T> queryExampleForList(Object example);
	
	public List<T> queryObjectForList(T entity);
	public PageInfo<T> queryPageForList();
	public PageInfo<T> queryPageForList(T entity);
	public PageInfo<T> queryPageForListByEX(Object example);

	public String deleteByExample(T entity);


	public List<T> queryAll();

}
