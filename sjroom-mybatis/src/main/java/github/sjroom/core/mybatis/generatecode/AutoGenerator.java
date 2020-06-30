package github.sjroom.core.mybatis.generatecode;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import github.sjroom.core.mybatis.enums.SystemEnum;
import github.sjroom.core.utils.Exceptions;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 15-14
 */
public class AutoGenerator {
	private Map<String, Object> data;

	public AutoGenerator(Map<String, Object> data) {
		this.data = data;
		this.config = new ConfigGenerator();
	}

	public AutoGenerator(ConfigGenerator config) {
		this.config = config;
	}

	private ConfigGenerator config;

	/**
	 * run 执行
	 */
	public void run() {

		/**
		 * 检查文件夹是否存在
		 */
		File gf = new File(config.getSaveDir());
		if (!gf.exists()) {
			gf.mkdirs();
		}

		/**
		 * 开启生成映射关系
		 */
		generateCode();

		System.out.println(" generate success! ");

	}


	public void generateCode() {

		DBHelper.getConnection(config
			.getDbDriverName(), config.getDbUrl(), config.getDbUser(), config.getDbPassword());

		String tableNames = config.getGenerateTableName();

		for (String tableName : tableNames.split(StringPool.COMMA)) {
			tableName = tableName.trim();
			System.out.println("开始生成表" + tableName);

			//设置返回的时的数据
			this.data = createRenderData(tableName);
			//根据需求生成，不需要的注掉，模板有问题的话可以自己修改。

			buildClass("controller", "controller.java", "Controller");
			buildClass("service", "service.java", "Service");
			buildClass("service.impl", "service-impl.java", "ServiceImpl");
			buildClass("service", "service-comp.java", "ServiceComp");
			buildClass("service.comp", "service-comp-impl.java", "ServiceCompImpl");
			buildClass("bean.entity", "entity.java", "");
			buildClass("bean.vo", "entity-request.java", "ReqVo");
			buildClass("bean.vo", "entity-page.java", "PageReqVo");
			buildClass("bean.vo", "entity-response.java", "RespVo");
			buildClass("bean.bo", "entity-bo.java", "Bo");
			buildClass("dao", "dao.java", "Dao");
			buildClass("dao", "dao-comp.java", "DaoComp");
			buildClass("dao.comp", "dao-comp-impl.java", "DaoCompImpl");
			if (this.config.getXmlPaging()) {
				buildClass("dao", "dao.xml", "Dao");
			}

			System.out.println(tableName + "生成完成");
		}
	}

	/**
	 * 生成buildClass类
	 *
	 * @param fileSuffix 文件的后缀名如果为-1,直接自己取名称
	 * @param fileName   自定义文件名称
	 * @packageName 包名
	 * @templateName 模板地址, 默认前缀有: resources/template/
	 */
	public void buildClass(String packageName, String templateName, String fileName, String fileSuffix) {
		data.put("currentPackage", config.getBasePackage() + "." + packageName);
//        System.out.println("path: " + config.getBasePackage() + "." + packageName +  ": end");
		String upperModelName = "";
		if (fileName.equals("-1")) {
			upperModelName = String.valueOf(data.get("upperModelName"));
		} else {
			upperModelName = fileName;
		}

		data.put("fileSuffix", fileSuffix);

		//如果是接口的化,前面加个前缀I.
		String outputName = "";
		if ((templateName.startsWith("service") && !templateName.endsWith("impl.java"))
			|| templateName.equals("dao.java")
			|| templateName.equals("dao-comp.java")
		) {
			outputName = config.packageConvertPath(config.getBasePackage() + "." + packageName) + "I" + upperModelName + fileSuffix;
		} else if (templateName.equals("dao.xml")) {
			outputName = config.getSaveDir() + "/sqlMapperXml/" + upperModelName + fileSuffix;
		} else {
			outputName = config.packageConvertPath(config.getBasePackage() + "." + packageName) + upperModelName + fileSuffix;
		}

		// 如果模板有后缀,取模板的后缀
		if (templateName.contains(StringPool.DOT)) {
			outputName = outputName + templateName.substring(templateName.lastIndexOf("."));
		}
		Output.createFile(templateName, outputName, data);
	}

	public void buildClass(String packageName, String templateName, String fileSuffix) {
		buildClass(packageName, templateName, "-1", fileSuffix);
	}

	/**
	 * 生成build enum Class类
	 *
	 * @packageName 包名
	 * @templateName 模板地址, 默认前缀有: resources/template/
	 */
	public void buildEnumClass() {
		String basePackage = (String) data.get("basePackage");
		String className = (String) data.get("className");

		String outputName = config.packageConvertPath("." + basePackage) + className + ".java";
		Output.createFile("enum.java", outputName, data);
	}

	/**
	 * 渲染模板的数据
	 *
	 * @param tableName
	 * @return
	 */
	public Map<String, Object> createRenderData(String tableName) {
		Map<String, Object> data = new HashMap<>();
		//赋值表信息
		DbTableInfo dbTableInfo = getTableInfo(config.getDbSchema(), tableName);
		data.put("dbTableInfo", dbTableInfo);

		//赋值字段信息
		List<DbTableFieldInfo> dbTableFieldInfoList = getEntityList(tableName, dbTableInfo);
		data.put("dbTableFieldInfoList", dbTableFieldInfoList);

		data.put("dbTableName", tableName);

		// 去掉表名的前缀
		tableName = tableName.replace(config.getPrefixTableName(), "");
		// 如果表名前缀去掉以后,还有_的情况下,直接截掉
		if (tableName.startsWith("_")) {
			tableName = tableName.substring(1, tableName.length());
		}

		//设置业务主键
		for (DbTableFieldInfo dbTableFieldInfo : dbTableFieldInfoList) {
			if (dbTableFieldInfo.getComment().equals("业务主键")
				|| dbTableFieldInfo.getColumn().equals(tableName + "_id")) {
				dbTableFieldInfo.setRelated(true);
				break;
			}
		}

		data.put("tableName", tableName);
		data.put("date", new SimpleDateFormat("yyyy-MM-dd HH:ss").format(new java.util.Date()));
		data.put("upperModelName", convertUpperCamel(tableName));
		data.put("lowerModelName", tableNameConvertLowerCamel(tableName));
		data.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableName));
		data.put("config", config);
		data.put("sys", Optional.ofNullable(config.getSystemEnum()).map(s -> s.getValue()).orElse(SystemEnum.BASE.getValue()));
		data.put("orderBy", "${orderBy}");
		data.put("asc", "${asc}");
		return data;
	}

	/**
	 * 表名大小写转换
	 *
	 * @param dbName
	 * @return
	 */
	private String tableNameConvertLowerCamel(String dbName) {
		StringBuilder result = new StringBuilder();
		if (dbName != null && dbName.length() > 0) {
			dbName = dbName.toLowerCase();
			boolean flag = false;
			for (int i = 0; i < dbName.length(); i++) {
				char ch = dbName.charAt(i);
				if ("_".charAt(0) == ch) {
					flag = true;
				} else {
					if (flag) {
						result.append(Character.toUpperCase(ch));
						flag = false;
					} else {
						result.append(ch);
					}
				}
			}
		}
		return result.toString();
	}

	private String convertUpperCamel(String dbName) {
		return convertUpperCamel(dbName, true);
	}

	private String convertUpperCamel(String dbName, boolean firstUpper) {
		String camel = tableNameConvertLowerCamel(dbName);
		if (firstUpper) {
			return camel.substring(0, 1).toUpperCase() + camel.substring(1);
		} else {
			return camel.substring(0, 1).toLowerCase() + camel.substring(1);
		}
	}

	private String tableNameConvertMappingPath(String tableName) {
		tableName = tableName.toLowerCase();
		return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
	}


	public DbTableInfo getTableInfo(String schemaName, String tableName) {

		String sql = String.format("SELECT * FROM `information_schema`.`TABLES` WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME ='%s'", schemaName, tableName);
		ResultSet rs = DBHelper.execQuery(sql);

		//数据库读取表的结构
		DbTableInfo dbTableInfo = new DbTableInfo();
		try {
			while (rs.next()) {
				String comment = rs.getString("TABLE_COMMENT");
				dbTableInfo.setComment(comment);

				dbTableInfo.setNamespace(config.getEntityPackage());
			}
		} catch (SQLException e) {
			throw Exceptions.unchecked(e);
		}
		return dbTableInfo;
	}


	public List<DbTableFieldInfo> getEntityList(String tableName, DbTableInfo dbTableInfo) {

		String sql = String.format("SHOW FULL FIELDS FROM %s", tableName);
		ResultSet rs = DBHelper.execQuery(sql);

		//数据库读取表的结构
		List<DbTableFieldInfo> entityList = new ArrayList<>();
		DbTableFieldInfo dbTableFieldInfo;
		try {
			while (rs.next()) {
				String columnName = rs.getString("field");
				String columnType = rs.getString("type");
				String comment = rs.getString("comment");
				String isNullable = rs.getString("null");
				String key = rs.getString("key");

				boolean firstUpper = false;
				dbTableFieldInfo = new DbTableFieldInfo();
				dbTableFieldInfo.setColumn(columnName);
				dbTableFieldInfo.setColumnType(columnType.toUpperCase());

				convertColumnLength(dbTableFieldInfo);

				dbTableFieldInfo.setProperty(convertUpperCamel(columnName, firstUpper));
				dbTableFieldInfo.setPropertyType(dataTypeConvertJavaType(dbTableFieldInfo.getColumnType()));
				dbTableFieldInfo.setComment(comment);

				//忽略字段不做validation操作
				if (!ignoreField(columnName)) {
					if ("YES".equals(isNullable)) {
						dbTableFieldInfo.setIsNull(Boolean.TRUE);
					} else {
						dbTableFieldInfo.setIsNull(Boolean.FALSE);
					}
				}

				//设置主键
				if ("PRI".equals(key) && dbTableInfo.getKeyColumn() == null) {
					dbTableInfo.setKeyColumn(dbTableFieldInfo.getColumn());
					dbTableInfo.setKeyProperty(dbTableFieldInfo.getProperty());
				}

				entityList.add(dbTableFieldInfo);
			}
			dbTableInfo.setDbTableFieldInfoList(entityList);
		} catch (SQLException e) {
			throw Exceptions.unchecked(e);
		}
		return entityList;
	}

	/**
	 * 获取字段的长度
	 *
	 * @return
	 */
	public void convertColumnLength(DbTableFieldInfo dbTableFieldInfo) {
		if (dbTableFieldInfo.getColumnType().contains("(")) {
			String columnType = dbTableFieldInfo.getColumnType().substring(0, dbTableFieldInfo.getColumnType().indexOf("("));
			String columnLengh = dbTableFieldInfo.getColumnType().substring(dbTableFieldInfo.getColumnType().indexOf("("), dbTableFieldInfo.getColumnType().indexOf(")"));
			dbTableFieldInfo.setColumnType(columnType);
			dbTableFieldInfo.setColumnLength(columnLengh);
		}
	}

	/**
	 * 将dbType 转换为 javaType
	 *
	 * @return
	 */
	private static String dataTypeConvertJavaType(String dbType) {
		//全部变成小写开始
		dbType = dbType.toLowerCase();
		if ("bigint".equals(dbType)) {
			return "Long";
		} else if ("varchar".equals(dbType) || "text".equals(dbType) || "char".equals(dbType)) {
			return "String";
		} else if ("int".equals(dbType) || "tinyint".equals(dbType)) {
			return "Integer";
		} else if ("datetime".equals(dbType) || "timestamp".equals(dbType) || dbType.startsWith("date")) {
			return "Date";
		} else if ("bit".equals(dbType)) {
			return "Boolean";
		} else if (dbType.indexOf("decimal") > -1) {
			return "java.math.BigDecimal";
		} else if (dbType.indexOf("blob") > -1) {
			return "byte[]";
		} else if (dbType.indexOf("float") > -1) {
			return "Float";
		} else if (dbType.indexOf("double") > -1) {
			return "Double";
		}
		return "other";
	}

	/**
	 * 忽略的字段
	 *
	 * @param fieldName
	 * @return
	 */
	private boolean ignoreField(String fieldName) {
		for (String item : config.getIgnoreFieldArr()) {
			if (item.equals(fieldName)) {
				return true;
			}
		}
		return false;
	}


}
