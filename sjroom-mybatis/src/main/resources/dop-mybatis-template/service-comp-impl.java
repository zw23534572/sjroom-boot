package ${currentPackage};

import ${config.servicePackage}.I${upperModelName}ServiceComp;
import ${config.servicePackage}.I${upperModelName}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ${config.beanPackage}.entity.${upperModelName};


/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Slf4j
@Service
@Validated
public class ${upperModelName}${fileSuffix} implements I${upperModelName}ServiceComp {
    @Autowired
    private I${upperModelName}Service ${lowerModelName}Service;
}
