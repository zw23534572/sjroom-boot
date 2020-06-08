package ${currentPackage};

{config.servicePackage}.I${upperModelName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${config.beanPackage}.entity.${upperModelName};
{config.daoPackage}.I${upperModelName}Dao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * <B>说明：${dbTableInfo.comment}服务实现</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0
 * @since ${date}
 */
@Slf4j
@Service
@Validated
public class ${upperModelName}${fileSuffix} extends BaseServiceImpl<I${upperModelName}Dao, ${upperModelName}> implements I${upperModelName}Service {
    @Autowired
    private I${upperModelName}Dao ${lowerModelName}Dao;

}

