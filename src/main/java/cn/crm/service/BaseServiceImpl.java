package cn.crm.service;

import java.util.List;

import cn.crm.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;


import com.github.pagehelper.PageInfo;

@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {



	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
    
    
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    protected Mapper<T> mapper;
    
	@Override
	public String save(T entity) {
		int count = 0;
		if(null != entity) {
			count = mapper.insertSelective(entity);
		}		
		return count > 0 ? SUCCESS : ERROR ;
	}

	@Override
	public String delete(Object key) {
		int count = mapper.deleteByPrimaryKey(key);
		return count > 0 ? SUCCESS : ERROR ;
	}

	@Override
	public String update(T entity) {
		int count = mapper.updateByPrimaryKeySelective(entity);
		return count > 0 ? SUCCESS : ERROR ;
	}

	@Override
	public T findByPrimaryKey(Object key) {
		
		return mapper.selectByPrimaryKey(key);
	}

	@Override
	public T findByObject(T entity) {		
		try {
			return mapper.selectOne(entity);
		} catch (Exception e) {			
			logger.error("错误的查询,检查是否返回多个结果集!", e);
		}
		return null;
	}

	@Override
	public List<T> queryExampleForList(Object example) {
		
		return mapper.selectByExample(example);
	}

	@Override
	public List<T> queryObjectForList(T entity) {
		
		return mapper.select(entity);
	}

	@Override
	public PageInfo<T> queryPageForList() {
		return queryPageForList(null);
	}

	@Override
	public PageInfo<T> queryPageForList(T entity) {
		PageUtil.startPage();
		List<T> list = mapper.select(entity);
		return new PageInfo<T>(list);
	}
	
	@Override
	public PageInfo<T> queryPageForListByEX(Object example){
		PageUtil.startPage();
		List<T> list = mapper.selectByExample(example);
		return new PageInfo<T>(list);
	}

	@Override
	public String deleteByExample(T entity) {
		int delete = mapper.delete(entity);
		return delete >0 ? SUCCESS : ERROR ;
	}


	@Override
	public List<T> queryAll() {
		return mapper.selectAll();
	}
}
