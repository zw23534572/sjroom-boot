package github.sjroom.core.mybatis.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import github.sjroom.core.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * page 工具类
 *
 * @author manson.zhou
 */
public class PageUtil {
	/**
	 * 过滤 sql，由于 mybatis-plus 的 order 排序字段为直接拼接，有注入风险
	 */
	private final static Pattern SQL_REGEX = Pattern.compile("[ '\",;`()-+#]");

	/**
	 * 将前端的 PageResult 转换成 mybatis-plus 的page
	 *
	 * @param <T> 泛型
	 * @return Page
	 */
	public static <T> Page<T> toPage(PageParam pageParam) {
		Page<T> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
		if (pageParam instanceof PageReqParam) {
			PageReqParam pageReqParam = (PageReqParam) pageParam;
			List<OrderItem> orders = new ArrayList<>();
			orders.addAll(OrderItem.ascs(pageReqParam.getAscs().toArray(new String[pageReqParam.getAscs().size()])));
			orders.addAll(OrderItem.descs(pageReqParam.getDescs().toArray(new String[pageReqParam.getDescs().size()])));
			page.setOrders(orders);
		}
		return page;
	}


	/**
	 * 2个 IPage 转 Page
	 *
	 * @param page   IPage
	 * @param target 需要copy转换的类型
	 * @param <T>    泛型
	 * @return PageResult
	 */
	public static <T> Page<T> toPage(IPage<?> page, Class<T> target) {
		Page<T> pageResult = new Page<>();
		pageResult.setCurrent(page.getCurrent());
		pageResult.setSize(page.getSize());
		pageResult.setPages(page.getPages());
		pageResult.setTotal(page.getTotal());
		pageResult.setRecords(BeanUtil.copy(page.getRecords(), target));
		return pageResult;
	}

	/**
	 * 2个 IPage 转 Page
	 *
	 * @param page    IPage
	 * @param records 转换过的list模型
	 * @param <T>     泛型
	 * @return PageResult
	 */
	public static <T> Page<T> toPage(IPage<?> page, List<T> records) {
		Page<T> pageResult = new Page<>();
		pageResult.setCurrent(page.getCurrent());
		pageResult.setSize(page.getSize());
		pageResult.setPages(page.getPages());
		pageResult.setTotal(page.getTotal());
		pageResult.setRecords(records);
		return pageResult;
	}

	/**
	 * 将 page 转为 PageResult
	 *
	 * @param page mybati-plus page
	 * @param <T>  泛型
	 * @return PageResult
	 */
	public static <T> PageResult<T> toPageResult(IPage<T> page) {
		PageResult<T> respParam = new PageResult<>();
		respParam.setPageNum(page.getCurrent());
		respParam.setPageSize(page.getSize());
		respParam.setPageCount(page.getPages());
		respParam.setTotalCount(page.getTotal());
		respParam.setRecords(page.getRecords());
		return respParam;
	}

	/**
	 * 将 page 转为 PageResult
	 *
	 * @param page   mybati-plus page
	 * @param target 需要copy转换的类型
	 * @param <T>    泛型
	 * @return PageResult
	 * @Deprecated 尽量公用bean，vo对象，不符合需求再新建vo,不推荐bean.copy,
	 * @see {@link #toPageResult(com.baomidou.mybatisplus.core.metadata.IPage, java.util.function.Function)}
	 */
	public static <T> PageResult<T> toPageResult(IPage<?> page, Class<T> target) {
		PageResult<T> pageResult = new PageResult<>();
		pageResult.setPageNum(page.getCurrent());
		pageResult.setPageSize(page.getSize());
		pageResult.setPageCount(page.getPages());
		pageResult.setTotalCount(page.getTotal());
		pageResult.setRecords(BeanUtil.copy(page.getRecords(), target));
		return pageResult;
	}


	/**
	 * 将 page 转为 PageResult
	 *
	 * @param page     mybati-plus page
	 * @param function 需要转换的类型
	 * @param <T>      泛型
	 * @return PageResult
	 */
	public static <T, R> PageResult<R> toPageResult(IPage<T> page, Function<T, R> function) {
		PageResult<R> pageResult = new PageResult<>();
		pageResult.setPageNum(page.getCurrent());
		pageResult.setPageSize(page.getSize());
		pageResult.setPageCount(page.getPages());
		pageResult.setTotalCount(page.getTotal());

		if (!CollectionUtil.isEmpty(page.getRecords())) {
			pageResult.setRecords(page.getRecords().stream().map(function).collect(Collectors.toList()));
		}
		return pageResult;
	}


	/**
	 * 把SQL关键字替换为空字符串
	 *
	 * @param paramList 参数
	 * @return 参数列表
	 */
	public static List<String> filter(List<String> paramList) {
		if (ObjectUtil.isEmpty(paramList)) {
			return paramList;
		}
		return paramList.stream()
			.map(PageUtil::filter)
			.collect(Collectors.toList());
	}

	/**
	 * 把SQL关键字替换为空字符串
	 *
	 * @param param 参数
	 * @return string
	 */
	public static String filter(String param) {
		if (StringUtil.isBlank(param)) {
			return param;
		}
		return SQL_REGEX.matcher(param).replaceAll(StringPool.EMPTY);
	}
}
