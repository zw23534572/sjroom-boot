package ${currentPackage};

import ${config.daoPackage}.I${upperModelName}DaoComp;
import ${config.daoPackage}.I${upperModelName}Dao;
import ${config.beanPackage}.entity.${upperModelName};
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Service
@Slf4j
public class ${upperModelName}${fileSuffix} implements I${upperModelName}DaoComp {

    @Autowired
    private I${upperModelName}Dao ${lowerModelName}Dao;

}
