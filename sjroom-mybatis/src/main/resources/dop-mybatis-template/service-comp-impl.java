package ${currentPackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import ${config.boPackage}.${upperModelName}Bo;
import ${config.entityPackage}.${upperModelName};
import ${config.voPackage}.${upperModelName}PageReqVo;
import ${config.voPackage}.${upperModelName}ReqVo;
import ${config.voPackage}.${upperModelName}RespVo;
import ${config.servicePackage}.I${upperModelName}Service;
import ${config.servicePackage}.I${upperModelName}ServiceComp;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.Date;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Slf4j
@Service
@Validated
public class ${upperModelName}ServiceCompImpl implements I${upperModelName}ServiceComp {
	@Autowired
	private I${upperModelName}Service ${lowerModelName}Service;

	@Override
	public ${upperModelName}RespVo find(IdVo<Long> idVo) {
		${upperModelName}Bo ${lowerModelName}Bo = ${lowerModelName}Service.findByBId(idVo.getId());
		return BeanUtil.copy(${lowerModelName}Bo, ${upperModelName}RespVo.class);
	}

	@Override
	public IPage page(${upperModelName}PageReqVo reqVo) {
		IPage<${upperModelName}Bo> ${lowerModelName}BoIPage = ${lowerModelName}Service.findPage(this.buildParams(reqVo));
		this.buildResult(${lowerModelName}BoIPage.getRecords());
		return ${lowerModelName}BoIPage;
	}

	@Override
	public List<${upperModelName}RespVo> list(${upperModelName}ReqVo reqVo) {
		List<${upperModelName}Bo> ${lowerModelName}Bos = ${lowerModelName}Service.findList(BeanUtil.copy(reqVo, ${upperModelName}Bo.class));
		return BeanUtil.copy(${lowerModelName}Bos, ${upperModelName}RespVo.class);
	}

	@Override
	public Long create(${upperModelName}ReqVo reqVo) {
		${upperModelName}Bo ${lowerModelName}Bo = this.validatedParams(reqVo);
		${upperModelName} ${lowerModelName} = this.fetchEntityData(${lowerModelName}Bo);
		${lowerModelName}Service.save(${lowerModelName});
		return ${lowerModelName}.get${upperModelName}Id();
	}

	@Override
	public void update(${upperModelName}ReqVo reqVo) {
		${upperModelName}Bo ${lowerModelName}Bo = this.validatedParams(reqVo);
		${upperModelName} ${lowerModelName} = this.fetchEntityData(${lowerModelName}Bo);
		${lowerModelName}.setUpdatedAt(new Date());
		${lowerModelName}Service.updateByBId(${lowerModelName});
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<${upperModelName}> ${lowerModelName}s = ${lowerModelName}Service.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(${lowerModelName}s)) {
			log.warn("${upperModelName}ServiceCompImpl ${lowerModelName}Bos is empty");
			return;
		}
		${lowerModelName}s.stream().forEach(${lowerModelName} -> {
			${lowerModelName}.setStatus(idStatusListVo.getStatus());
			${lowerModelName}.setUpdatedAt(new Date());
		});
		${lowerModelName}Service.updateBatchByBIds(${lowerModelName}s);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("${upperModelName}ServiceCompImpl removeBatch idListVo is empty");
			return;
		}
	
		List<${upperModelName}Bo> ${lowerModelName}s = ${lowerModelName}Service.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(${lowerModelName}s)) {
			${lowerModelName}s = ${lowerModelName}s.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(${lowerModelName}s.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}
	
		${lowerModelName}Service.removeBatchBIds(idListVo.getIdList());
	}
	
	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private ${upperModelName}Bo buildParams(${upperModelName}PageReqVo reqVo) {
		${upperModelName}Bo ${lowerModelName}Bo = BeanUtil.copy(reqVo, ${upperModelName}Bo.class);
		return ${lowerModelName}Bo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param ${lowerModelName}Bos
	 */
	private void buildResult(List<${upperModelName}Bo> ${lowerModelName}Bos) {
		if (CollectionUtil.isEmpty(${lowerModelName}Bos)) {
			log.warn("${upperModelName}ServiceCompImpl buildResult is empty");
			return;
		}
		// 实现业务逻辑
	}

	/**
	 * 验证参数
	 *
	 * @param reqVo
	 * @return
	 */
	private ${upperModelName}Bo validatedParams(${upperModelName}ReqVo reqVo) {
		${upperModelName}Bo ${lowerModelName}Bo = BeanUtil.copy(reqVo, ${upperModelName}Bo.class);
		return ${lowerModelName}Bo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param ${lowerModelName}Bo
	 * @return
	 */
	private ${upperModelName} fetchEntityData(${upperModelName}Bo ${lowerModelName}Bo) {
		${upperModelName} ${lowerModelName} = BeanUtil.copy(${lowerModelName}Bo, ${upperModelName}.class);
		return ${lowerModelName};
	}
}
