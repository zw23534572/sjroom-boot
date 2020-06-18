package github.sjroom.core.mybatis.injector;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务主键方法
 *
 * @author manson.zhou
 */
@Getter
@AllArgsConstructor
public enum BizSqlMethod {

	/**
	 * 插入如果中已经存在相同的记录，则忽略当前新数据
	 */
	INSERT_IGNORE_ONE("insertIgnore", "插入一条数据（选择字段插入）", "<script>\nINSERT IGNORE INTO %s %s VALUES %s\n</script>"),

	/**
	 * 表示插入替换数据，需求表中有PrimaryKey，或者unique索引，如果数据库已经存在数据，则用新数据替换，如果没有数据效果则和insert into一样；
	 */
	REPLACE_ONE("replace", "插入一条数据（选择字段插入）", "<script>\nREPLACE INTO %s %s VALUES %s\n</script>"),

	/**
	 * 删除
	 */
	DELETE_BY_BID("deleteByBId", "根据ID 删除一条数据", "<script>\nDELETE FROM %s WHERE %s=#{%s}\n</script>"),
	DELETE_BATCH_BY_BIDS("deleteBatchBIds", "根据ID集合，批量删除数据", "<script>\nDELETE FROM %s WHERE %s IN (%s)\n</script>"),

	/**
	 * 逻辑删除
	 */
	LOGIC_DELETE_BY_BID("deleteByBId", "根据ID 逻辑删除一条数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
	LOGIC_DELETE_BATCH_BY_BIDS("deleteBatchBIds", "根据ID集合，批量逻辑删除数据", "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),

	/**
	 * 修改
	 */
	UPDATE_BY_BID("updateByBId", "根据ID 选择修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),
	UPDATE_BATCH_BY_BIDS("updateBatchByBIds", "根据ID 批量修改数据", "<script>\nUPDATE %s %s WHERE %s IN (%s) %s\n</script>"),

	/**
	 * 逻辑删除 -> 修改
	 */
	LOGIC_UPDATE_BY_BID("updateByBId", "根据ID 修改数据", "<script>\nUPDATE %s %s WHERE %s=#{%s} %s\n</script>"),

	/**
	 * 查询
	 */
	SELECT_BY_BID("selectByBId", "根据ID 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s}"),
	SELECT_BATCH_BY_BIDS("selectBatchBIds", "根据ID集合，批量查询数据", "<script>\nSELECT %s FROM %s WHERE %s IN (%s)\n</script>"),

	/**
	 * 逻辑删除 -> 查询
	 */
	LOGIC_SELECT_BY_BID("selectByBId", "根据ID 查询一条数据", "SELECT %s FROM %s WHERE %s=#{%s} %s"),
	LOGIC_SELECT_BATCH_BY_BIDS("selectBatchBIds", "根据ID集合，批量查询数据", "<script>\nSELECT %s FROM %s WHERE %s IN (%s) %s\n</script>");

	private final String method;
	private final String desc;
	private final String sql;

}
