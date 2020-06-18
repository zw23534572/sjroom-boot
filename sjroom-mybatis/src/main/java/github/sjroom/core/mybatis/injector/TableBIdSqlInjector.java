package github.sjroom.core.mybatis.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import github.sjroom.core.mybatis.injector.methods.*;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * SQL 默认注入器
 *
 * @author manson.zhou
 * @since 2019-06-03
 */
public class TableBIdSqlInjector extends AbstractSqlInjector {

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		// 自定义的业务注解处理
		return Stream.of(
			new InsertIgnore(),
			new InsertIgnoreBatch(),
			new Replace(),
			new ReplaceBatch(),
			new InsertBatch(),
			new InsertBizId(),
			new Delete(),
			new DeleteByMap(),
			new DeleteById(),
			new DeleteBatchByIds(),
			new Update(),
			new UpdateById(),
			new UpdateColumnsByBId(),
			new SelectById(),
			new SelectBatchByIds(),
			new SelectByMap(),
			new SelectOne(),
			new SelectCount(),
			new SelectMaps(),
			new SelectMapsPage(),
			new SelectObjs(),
			new SelectList(),
			new SelectPage(),
			new SelectByBizId(),
			new SelectBatchByBizIds(),
			new UpdateByBizId(),
			new UpdateBatchByBizIds(),
			new DeleteByBizId(),
			new DeleteBatchByBizIds()
		).collect(toList());
	}
}
