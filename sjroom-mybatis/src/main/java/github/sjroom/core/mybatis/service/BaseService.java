package github.sjroom.core.mybatis.service;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 顶级 Service
 *
 * @author manson.zhou
 * @since 2019-06-03
 */
public interface BaseService<T> extends IService<T> {

	/**
	 * 根据 ID 查询
	 *
	 * @param id 主键ID
	 */
	T getByBId(Serializable id);

	/**
	 * 查询（根据ID 批量查询）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 * @return 集合
	 */
	List<T> getBatchBIds(Collection<? extends Serializable> idList);

	/**
	 * 插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entity entity
	 * @return 是否成功
	 */
	boolean saveIgnore(T entity);

	/**
	 * 插入（批量）,插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entityList 实体对象集合
	 * @param batchSize  批次大小
	 * @return 是否成功
	 */
	boolean saveIgnoreBatch(Collection<T> entityList, int batchSize);

	/**
	 * 插入（批量）,插入如果中已经存在相同的记录，则忽略当前新数据
	 *
	 * @param entityList 实体对象集合
	 * @return 是否成功
	 */
	@Transactional(rollbackFor = Exception.class)
	default boolean saveIgnoreBatch(Collection<T> entityList) {
		return saveIgnoreBatch(entityList, 1000);
	}

	/**
	 * 根据 ID 修改
	 *
	 * @param entity 实体对象
	 */
	boolean updateByBId(T entity);

	/**
	 * 根据 ID 修改
	 *
	 * @param entity 实体对象
	 */
	boolean updateColumnsByBId(T entity, SFunction<T, ?>... columns);

	/**
	 * 根据 ID LIST 批量修改，采用的是一条语句的形式 update XXX in (idList)
	 *
	 * @param entityList 实体对象
	 */
	boolean updateBatchByBIds(List<T> entityList);

	/**
	 * 根据 业务id 对 entity LIST 批量修改，采用的是执行多条语句的形式
	 *
	 * @param entityList 实体对象
	 */
	boolean updateBatchByBId(List<T> entityList);

	/**
	 * 根据 业务id 对 entity LIST 批量修改，采用的是执行多条语句的形式
	 *
	 * @param entityList 实体对象集合
	 * @param batchSize  更新批次数量
	 */
	boolean updateBatchByBId(Collection<T> entityList, int batchSize);

	/**
	 * 根据 ID 删除
	 *
	 * @param id 主键ID
	 */
	boolean removeByBId(Serializable id);

	/**
	 * 删除（根据ID 批量删除）
	 *
	 * @param idList 主键ID列表(不能为 null 以及 empty)
	 */
	boolean removeBatchBIds(Collection<? extends Serializable> idList);

}
