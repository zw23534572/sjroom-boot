package github.sjroom.core.mybatis.mapper;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.StringUtil;
import github.sjroom.core.mybatis.injector.methods.EntityItem;
import github.sjroom.core.mybatis.util.IdUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.io.Serializable;
import java.util.*;

/**
 * 自定义的 Mapper
 *
 * @author manson.zhou
 */
public interface IMapper<T> extends BaseMapper<T> {

	/**
	 * 根据业务 ID 查询
	 *
	 * @param id 主键ID
	 * @return 结果
	 */
	T selectByBId(Serializable id);

	/**
	 * 查询（根据业务ID 批量查询）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 集合
	 */
	List<T> selectBatchBIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

	/**
	 * 插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entity 实体对象
	 * @return 更改的条数
	 */
	int insertIgnore(T entity);

	/**
	 * 插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entityList 实体对象列表
	 * @return 更改的条数
	 */
	int insertIgnoreBatch(@Param(Constants.COLLECTION) Collection<T> entityList);

	/**
	 * 批量保存
	 *
	 * @param entityList 实体列表
	 * @return 结果数
	 */
	int insertBatch(@Param(Constants.COLLECTION) Collection<T> entityList);

	/**
	 * 批量保存
	 *
	 * @param entityList 实体列表
	 * @return 结果数
	 */
	default int insertBatchByBIds(@Param(Constants.COLLECTION) Collection<T> entityList) {
		return insertBatch(entityList);
	}

	/**
	 * 根据业务 ID 修改
	 *
	 * @param entity 实体对象
	 * @return 结果数
	 */
	int updateByBId(@Param(Constants.ENTITY) T entity);

	/**
	 * 根据业务 ID 修改，只更新选定的字段，不做 null 和空的判断
	 *
	 * @param entity 实体对象
	 * @return 结果数
	 */
	default int updateColumnsByBId(@Param(Constants.ENTITY) T entity, SFunction<T, ?>... columns) {
		Assert.notNull(entity, "updateColumnsByBId can not execute, entity is null!");
		Assert.notEmpty(columns, "updateColumnsByBId Method args columns is empty!");
		List<EntityItem> entityItemList = new ArrayList<>();
		for (SFunction<T, ?> columnFunc : columns) {
			SerializedLambda lambda = LambdaUtils.resolve(columnFunc);
			String fieldName = PropertyNamer.methodToProperty(lambda.getImplMethodName());
			Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(lambda.getInstantiatedMethodType());
			ColumnCache columnOfProperty = columnMap.get(LambdaUtils.formatKey(fieldName));
			String column = columnOfProperty.getColumn();
			entityItemList.add(new EntityItem(column, BeanUtil.getProperty(entity, fieldName)));
		}
		return updateColumnsByBId(entity, entityItemList);
	}

	/**
	 * 根据业务 ID 修改，只更新选定的字段，不做 null 和空的判断
	 *
	 * @param entity 实体对象
	 * @return 结果数
	 */
	int updateColumnsByBId(@Param(Constants.ENTITY) T entity, @Param(Constants.COLLECTION) List<EntityItem> entityItemList);

	/**
	 * 根据业务 ID 批量修改
	 *
	 * @param entity 实体
	 * @param idList id 列表
	 * @return 结果数
	 */
	int updateBatchByBIds(@Param(Constants.ENTITY) T entity, @Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

	/**
	 * 根据业务 ID 批量修改
	 *
	 * @param entityList 实体列表
	 * @return 结果数
	 */
	default int updateBatchByBIds(List<T> entityList) {
		T entity = null;
		String bIdFieldName = null;
		Set<Serializable> idList = new HashSet<>();
		for (T e : entityList) {
			if (entity == null) {
				entity = e;
				bIdFieldName = IdUtil.getBIdFieldName(e.getClass());
			}
			if (StringUtil.isBlank(bIdFieldName)) {
				throw new MybatisPlusException("not found @TableBId in entity");
			}
			// 获取 bizId 的值
			Serializable bId = (Serializable) BeanUtil.getProperty(e, bIdFieldName);
			if (bId == null) {
				throw new MybatisPlusException("@TableBId bizId is null in entityList");
			}
			idList.add(bId);
		}
		return updateBatchByBIds(entity, idList);
	}

	/**
	 * 根据业务 ID 删除
	 *
	 * @param id 主键ID
	 * @return 结果数
	 */
	int deleteByBId(Serializable id);

	/**
	 * 删除（根据业务ID 批量删除）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 结果数
	 */
	int deleteBatchBIds(@Param(Constants.COLLECTION) Collection<? extends Serializable> idList);

}
