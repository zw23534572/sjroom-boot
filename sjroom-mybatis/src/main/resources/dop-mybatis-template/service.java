package ${currentPackage};

import ${config.boPackage}.${upperModelName}Bo;
{config.entityPackage}.${upperModelName};

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
public interface I${upperModelName}Service extends BaseService<${upperModelName}> {

	/**
	 * 查看
	 *
	 * @param ${lowerModelName}Id 业务主键Id
	 * @return
	 */
	${upperModelName}Bo findByBId(Long ${lowerModelName}Id);

	/**
	 * 列表
	 *
	 * @param ${lowerModelName}Ids 业务主键Id
	 * @return
	 */
	List<${upperModelName}Bo> findByBIds(Set<Long> ${lowerModelName}Ids);

	/**
	 * 列表
	 *
	 * @param ${lowerModelName}Bo 业务model
	 * @return
	 */
	List<${upperModelName}Bo> findList(${upperModelName}Bo ${lowerModelName}Bo);

	/**
	 * 列表
	 *
	 * @param ${lowerModelName}Bo 业务model
	 * @return 键值对
	 */
	Map<Long, ${upperModelName}Bo> findMap(${upperModelName}Bo ${lowerModelName}Bo);

	/**
	 * 分页
	 *
	 * @param ${lowerModelName}Bo
	 * @return
	 */
	IPage<${upperModelName}Bo> findPage(${upperModelName}Bo ${lowerModelName}Bo);
}
