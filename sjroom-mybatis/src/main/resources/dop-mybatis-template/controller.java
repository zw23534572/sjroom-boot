package ${currentPackage};

{config.beanPackage}.vo.*;
import ${config.basePackage}.service.I${upperModelName}Service;
{config.basePackage}.service.I${upperModelName}ServiceComp;
import github.sjroom.web.center.client.vo.BatchOperationVo;
import github.sjroom.web.center.client.vo.BatchStatusOperationVo;
import github.sjroom.web.center.client.vo.OperationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <B>说明：${dbTableInfo.comment} 控制器</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0
 * @since ${date}
 */
@Slf4j
@Validated
@RestController
@RequestMapping("${controllerMappingHyphen}")
@Api("${dbTableInfo.comment} 控制器")
public class ${upperModelName}Controller {
    @Autowired
    private I${upperModelName}Service ${lowerModelName}Service;
    @Autowired
    private I${upperModelName}ServiceComp ${lowerModelName}ServiceComp;

    @ApiOperation(value = "查看详情", notes = "传入id")
    @PostMapping("find")
    public ${upperModelName}RespVo find(@RequestBody @Validated OperationVo operationVo) {
        return new ${upperModelName}RespVo();
    }

    @ApiOperation("查看分页")
    @PostMapping("page")
    public PageResult<${upperModelName}RespVo> page(@RequestBody ${upperModelName}PageReqVo reqVo) {
        return new PageResult<>();
    }

    @ApiOperation("创建")
    @PostMapping("add")
    public Long add(@RequestBody @Validated ${upperModelName}ReqVo ${lowerModelName}ReqVo) {
    }

    @ApiOperation("更新")
    @PostMapping("modify")
    public void modify(@RequestBody @Validated ${upperModelName}ReqVo ${lowerModelName}ReqVo) {
    }

    @ApiOperation(value = "删除", notes = "传入id")
    @PostMapping("remove")
    public void remove(@RequestBody @Validated BatchOperationVo batchOperationVo) {
    }

    @ApiOperation(value = "批量禁用/启用", notes = "传入id")
    @PostMapping("status/modify")
    public void statusModify(@RequestBody @Validated BatchStatusOperationVo statusOperationVo) {

    }
}
