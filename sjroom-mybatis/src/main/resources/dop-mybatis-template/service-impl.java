package ${currentPackage};

import ${config.boPackage}.${upperModelName}Bo;
{config.entityPackage}.${upperModelName};
{config.daoPackage}.I${upperModelName}Dao;
{config.servicePackage}.I${upperModelName}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
@Slf4j
@Service
@Validated
public class ${upperModelName}ServiceImpl extends BaseServiceImpl<I${upperModelName}Dao, ${upperModelName}> implements I${upperModelName}Service {

	@Autowired
	I${upperModelName}Dao i${upperModelName}Dao;

	@Override
	public ${upperModelName}Bo findByBId(Long bid) {
		log.info("${upperModelName}ServiceImpl findByBId params:{}", bid);
		${upperModelName} ${lowerModelName} = super.getByBId(bid);
		return BeanUtil.copy(${lowerModelName}, ${upperModelName}Bo.class);
	}

	@Override
	public List<${upperModelName}Bo> findByBIds(Set<Long> ${lowerModelName}Ids) {
		log.info("${upperModelName}ServiceImpl findByBIds params:{}", ${lowerModelName}Ids);
		List<${upperModelName}> ${lowerModelName}s = super.getBatchBIds(${lowerModelName}Ids);
		return BeanUtil.copy(${lowerModelName}s, ${upperModelName}Bo.class);
	}

	@Override
	public List<${upperModelName}Bo> findList(${upperModelName}Bo ${lowerModelName}Bo) {
		log.info("${upperModelName}ServiceImpl findList params:{}", ${lowerModelName}Bo);
		List<${upperModelName}> ${lowerModelName}s = super.list(this.query(${lowerModelName}Bo));
		return BeanUtil.copy(${lowerModelName}s, ${upperModelName}Bo.class);
	}

	@Override
	public Map<Long, ${upperModelName}Bo> findMap(${upperModelName}Bo ${lowerModelName}Bo) {
		log.info("${upperModelName}ServiceImpl findMap params:{}", ${lowerModelName}Bo);
		List<${upperModelName}Bo> ${lowerModelName}Bos = this.findList(${lowerModelName}Bo);
		if (CollectionUtil.isEmpty(${lowerModelName}Bos)) {
			log.warn("${upperModelName}ServiceImpl find ${lowerModelName}Bos is empty");
			return Collections.emptyMap();
		}
		return ${lowerModelName}Bos.stream().collect(Collectors.toMap(${upperModelName}Bo::get${upperModelName}Id, Function.identity()));
	}

	@Override
	public IPage<${upperModelName}Bo> findPage(${upperModelName}Bo model) {
		log.info("${upperModelName}ServiceImpl findPage params:{}", model);
		<% if (config.xmlPaging == true) { %>
		IPage<${upperModelName}> modelPage = i${upperModelName}Dao.findPage(PageUtil.toPage(model), model);
		<% } else { %>
		IPage<${upperModelName}> modelPage = super.page(PageUtil.toPage(model), this.query(model));
		<% } %>
		return PageUtil.toPage(modelPage, ${upperModelName}Bo.class);
	}

	private LambdaQueryWrapper<${upperModelName}> query(${upperModelName}Bo model) {
	    LambdaQueryWrapper<${upperModelName}> wrapper = new LambdaQueryWrapper<${upperModelName}>();
		<% for(var item in dbTableFieldInfoList) {
			var isIgore=false;
			for(var igoreitem in config.ignoreFieldArr){
				if(item.column==igoreitem){
					isIgore = true;
					break;
				}
			}
			if(isIgore) continue;
			var isInteger = item.propertyType=='Long' || item.propertyType=='Integer' || item.propertyType=='Byte' || item.propertyType=='Date';
			%>
			wrapper.eq(${isInteger?"Object":"String"}Util.isNot${isInteger?"Null":"Blank"}(model.get${sputil.capitalize(item.property)}()), ${upperModelName}::get${sputil.capitalize(item.property)}, model.get${sputil.capitalize(item.property)}());
		<% } %>
		return wrapper;
	}
}
