package io.anshily.base.core;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        mapper.insertSelective(model);
    }

    public void save(List<T> models) {
        mapper.insertList(models);
    }

    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    public List<T> findAll() {
        return mapper.selectAll();
    }
    public List<T> select(T record){
        return mapper.select(record);
    };//根据实体中的属性值进行查询，查询条件使用等号
    public List<T> selectAll(){
        return mapper.selectAll();
    };//查询全部结果，select(null)方法能达到同样的效果
    public T selectOne(T record){
        return mapper.selectOne(record);
    };//根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
    public int selectCount(T record){
        return mapper.selectCount(record);
    };//根据实体中的属性查询总数，查询条件使用等号
    public int delete(T record){
        return mapper.delete(record);
    };//根据实体属性作为条件进行删除，查询条件使用等号
    public List<T> selectByCondition(Object condition){
        return mapper.selectByCondition(condition);
    };//根据Condition条件进行查询
    public int updateByConditionSelective(@Param("record") T record, @Param("example") Object condition){
        return mapper.updateByConditionSelective(record,condition);
    };//根据Condition条件更新实体record包含的不是null的属性值
    public int deleteByCondition(Object condition){
        return mapper.deleteByCondition(condition);
    };//根据Condition条件删除数据

    public List<T> selectByCSql(String sql){
        Condition condition = new Condition(modelClass);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andCondition(sql);
        return mapper.selectByCondition(condition);
    };//根据Condition 规则的sql进行查询
    public int deleteByCSql(String sql){
        Condition condition = new Condition(modelClass);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andCondition(sql);
        return mapper.deleteByCondition(condition);
    };//根据Condition 规则的sql进行删除

    public boolean judgeById(Integer id){
        T model = findById(id);
        return model != null;
    };//通过ID查找该id的数据是否存在

}
