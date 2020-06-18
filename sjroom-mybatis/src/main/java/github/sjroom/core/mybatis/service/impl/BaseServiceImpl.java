package github.sjroom.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.StringUtil;
import github.sjroom.core.mybatis.injector.BizSqlMethod;
import github.sjroom.core.mybatis.mapper.IMapper;
import github.sjroom.core.mybatis.service.BaseService;
import github.sjroom.core.mybatis.util.IdUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * BaseService 实现类（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 *
 * @author manson.zhou
 * @since 2019-06-03
 */
public class BaseServiceImpl<M extends IMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

	@Override
	public T getByBId(Serializable id) {
		return baseMapper.selectByBId(id);
	}

	@Override
	public List<T> getBatchBIds(Collection<? extends Serializable> idList) {
		return baseMapper.selectBatchBIds(idList);
	}

	@Override
	public boolean saveIgnore(T entity) {
		return retBool(baseMapper.insertIgnore(entity));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveIgnoreBatch(Collection<T> entityList, int batchSize) {
		return saveBatch(entityList, batchSize, BizSqlMethod.INSERT_IGNORE_ONE);
	}

	private boolean saveBatch(Collection<T> entityList, int batchSize, BizSqlMethod sqlMethod) {
		String sqlStatement = dopSqlStatement(sqlMethod);
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			for (T anEntity : entityList) {
				batchSqlSession.insert(sqlStatement, anEntity);
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}
		return true;
	}

	/**
	 * 获取 dopSqlStatement
	 *
	 * @param sqlMethod ignore
	 * @return sql
	 */
	protected String dopSqlStatement(BizSqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
	}

	@Override
	public boolean updateByBId(T entity) {
		return SqlHelper.retBool(baseMapper.updateByBId(entity));
	}

	@Override
	public boolean updateColumnsByBId(T entity, SFunction<T, ?>... columns) {
		return SqlHelper.retBool(baseMapper.updateColumnsByBId(entity, columns));
	}

	@Override
	public boolean updateBatchByBIds(List<T> entityList) {
		return SqlHelper.retBool(baseMapper.updateBatchByBIds(entityList));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateBatchByBId(List<T> entityList) {
		return updateBatchByBId(entityList, 1000);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateBatchByBId(Collection<T> entityList, int batchSize) {
		Assert.notEmpty(entityList, "error: entityList must not be empty");
		String sqlStatement = SqlHelper.table(currentModelClass()).getSqlStatement(BizSqlMethod.UPDATE_BY_BID.getMethod());
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			T entity = null;
			String bIdFieldName = null;
			for (T bIdEntity : entityList) {
				if (entity == null) {
					entity = bIdEntity;
					bIdFieldName = IdUtil.getBIdFieldName(entity.getClass());
				}
				if (StringUtil.isBlank(bIdFieldName)) {
					throw new MybatisPlusException("not found @TableBId in entity");
				}
				// 获取 bizId 的值
				Serializable bId = (Serializable) BeanUtil.getProperty(bIdEntity, bIdFieldName);
				if (bId == null) {
					throw new MybatisPlusException("@TableBId bizId is null in entityList");
				}
				MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
				param.put(Constants.ENTITY, bIdEntity);
				batchSqlSession.update(sqlStatement, param);
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}
		return true;
	}

	@Override
	public boolean removeByBId(Serializable id) {
		return SqlHelper.retBool(baseMapper.deleteByBId(id));
	}

	@Override
	public boolean removeBatchBIds(Collection<? extends Serializable> idList) {
		return SqlHelper.retBool(baseMapper.deleteBatchBIds(idList));
	}
}
