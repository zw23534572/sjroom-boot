## sjroom-mybatis 简介.
java 是专门针对web入口进行封装的基础组件，让使用者尽量少配置就可以达到开箱即用。
> Mybatis 增强工具包 - 只做增强不做改变，简化CRUD操作。再mybatis -plus 上进行了封装。

## 项目特性
- 无侵入：Mybatis-Plus 在 Mybatis 的基础上进行扩展，只做增强不做改变，引入 Mybatis-Plus 不会对您现有的 Mybatis 构架产生任何影响，而且 MP 支持所有 Mybatis 原生的特性
- 依赖少：仅仅依赖 Mybatis 以及 Mybatis-Spring
- 损耗小：启动即会自动注入基本CURD，性能基本无损耗，直接面向对象操作
- 通用CRUD操作：内置通用 Mapper、通用 Service，仅仅通过少量配置即可实现单表大部分 CRUD 操作，更有强大的条件构造器，满足各类使用需求
- 支持代码生成：采用代码或者 Maven 插件可快速生成 Mapper 、 Model 层代码，支持模板引擎.

## 代码生成方式
```java
Properties props = getProperties();
ConfigGenerator configGenerator = new ConfigGenerator();
configGenerator.setDbUrl("jdbc:mysql://xxxx:3306/demo");
configGenerator.setDbUser("demo");
configGenerator.setDbPassword("123456");
configGenerator.setDbSchema("demo");
configGenerator.setAuthor("user.name");


// 包名
configGenerator.setBasePackage("github.sjroom.dop.bc.message");
// 需要生成的表,多个为 "sys_msg_info,sys_msg_info12,sys_msg_info33"
configGenerator.setGenerateTableName("plat_account");
// 替换表名前缀
configGenerator.setPrefixTableName("plat");
new AutoGenerator(configGenerator).run();
```
### 生成代码步骤
#### 第一，执行。
将上述代码复制到工程中，进行执行
![Image text](../res/5.jpg)

#### 第二，copy代码。
copy图下红框的代码

![Image text](../res/6.jpg)

粘贴到自己的项目的对应的命名空间下

![Image text](../res/7.jpg)

#### 已完成。
进行代码编译，看是否有报错，如果报错请参考该文档【常见问题】

#### 生成的代码结构如下. 以(plat_account为例)
```xml
├──gen_code
│   ├─github.sjroom.dop.bc.message
│      ├─bean
│         ├─bo
│           ├─AccountBo.java   #传输对象
│         ├─entity
│           ├─Account.java   #实体对象
│         └─vo
│             ├─AccountPageReqVo.java   #分页对象
│             ├─AccountReqVo.java   #入参对象
│             └─AccountRespVo.java  #返参对象
│      ├─controller
│         └─AccountController.java  #访问层
│      ├─dao
│         └─IAccountDao.java  #数据层
│      ├─dao
│         └─impl
│            └─AccountServiceCompImpl.java #逻辑层接口：组合逻辑，复杂查询都这此实现接口
│      ├─AccountServiceImpl.java  #逻辑层接口：单表的增删改查
│      └─IAccountServiceComp.java #逻辑层接口：组合逻辑，复杂查询都这此定义接口
```

### 代码详解
#### controller
```java
@Slf4j
@Validated
@RestController
@RequestMapping("account")
@Api("平台账号表 控制器")
public class AccountController {
	@Autowired
	private IAccountServiceComp iAccountServiceComp;

	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_SELECT')")
	public AccountRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iAccountServiceComp.find(idVo);
	}

	@ApiOperation("分页")
	@PostMapping("page")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_SELECT')")
	public PageResult page(@Validated @RequestBody AccountPageReqVo reqVo) {
		return PageUtil.toPageResult(iAccountServiceComp.page(reqVo), AccountRespVo.class);
	}

	@ApiOperation("列表")
	@PostMapping("list")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_SELECT')")
	public List<AccountRespVo> list(@RequestBody AccountReqVo reqVo) {
		return iAccountServiceComp.list(reqVo);
	}

	@ApiOperation("创建")
	@PostMapping("create")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_CREATE')")
	public Long create(@Validated @RequestBody AccountReqVo accountReqVo) {
		return iAccountServiceComp.create(accountReqVo);
	}

	@ApiOperation("更新")
	@PostMapping("update")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_UPDATE')")
	public void update(@Validated @RequestBody AccountReqVo accountReqVo) {
		iAccountServiceComp.update(accountReqVo);
	}

	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_UPDATE')")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iAccountServiceComp.updateBatch(idStatusListVo);
	}

	@ApiOperation(value = "批量删除", notes = "传入id")
	@PostMapping("batch-remove")
	@PreAuthorize("hasRole('ROLE_PLAT_ACCOUNT_REMOVE')")
	public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
		iAccountServiceComp.removeBatch(idListVo);
	}
}

@Validated
public interface IAccountServiceComp {
	/**
	 * 查看
	 */
	AccountRespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage<AccountRespVo> page(AccountPageReqVo reqVo);

	/**
	 * 列表
	 */
	List<AccountRespVo> list(AccountReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(AccountReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(AccountReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long,Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}

@Slf4j
@Service
@Validated
public class AccountServiceCompImpl implements IAccountServiceComp {
	@Autowired
	private IAccountService accountService;

	@Override
	public AccountRespVo find(IdVo<Long> idVo) {
		AccountBo accountBo = accountService.findByBId(idVo.getId());
		return BeanUtil.copy(accountBo, AccountRespVo.class);
	}

	@Override
	public IPage<AccountRespVo> page(AccountPageReqVo reqVo) {
		IPage<AccountBo> accountBoIPage = accountService.findPage(this.buildParams(reqVo));
		this.buildResult(accountBoIPage.getRecords());
		return PageUtil.toPage(accountBoIPage, AccountRespVo.class);
	}

	@Override
	public List<AccountRespVo> list(AccountReqVo reqVo) {
		List<AccountBo> accountBos = accountService.findList(BeanUtil.copy(reqVo, AccountBo.class));
		return BeanUtil.copy(accountBos, AccountRespVo.class);
	}

	@Override
	public Long create(AccountReqVo reqVo) {
		AccountBo accountBo = this.validatedParams(reqVo);
		Account account = this.fetchEntityData(accountBo);
		accountService.save(account);
		return account.getAccountId();
	}

	@Override
	public void update(AccountReqVo reqVo) {
		AccountBo accountBo = this.validatedParams(reqVo);
		Account account = this.fetchEntityData(accountBo);
		account.setUpdatedAt(new Date());
		accountService.updateByBId(account);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<Account> accounts = accountService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(accounts)) {
			log.warn("AccountServiceCompImpl accountBos is empty");
			return;
		}
		accounts.stream().forEach(account -> {
			account.setStatus(idStatusListVo.getStatus());
			account.setUpdatedAt(new Date());
		});
		accountService.updateBatchByBIds(accounts);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("AccountServiceCompImpl removeBatch idListVo is empty");
			return;
		}

		List<AccountBo> accounts = accountService.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(accounts)) {
			accounts = accounts.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(accounts.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}

		accountService.removeBatchBIds(idListVo.getIdList());
	}

	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private AccountBo buildParams(AccountPageReqVo reqVo) {
		AccountBo accountBo = BeanUtil.copy(reqVo, AccountBo.class);
		return accountBo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param accountBos
	 */
	private void buildResult(List<AccountBo> accountBos) {
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceCompImpl buildResult is empty");
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
	private AccountBo validatedParams(AccountReqVo reqVo) {
		AccountBo accountBo = BeanUtil.copy(reqVo, AccountBo.class);
		return accountBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param accountBo
	 * @return
	 */
	private Account fetchEntityData(AccountBo accountBo) {
		Account account = BeanUtil.copy(accountBo, Account.class);
		return account;
	}
}



public interface IAccountService extends BaseService<Account> {

	/**
	 * 查看
	 *
	 * @param accountId 业务主键Id
	 * @return
	 */
	AccountBo findByBId(Long accountId);

	/**
	 * 列表
	 *
	 * @param accountIds 业务主键Id
	 * @return
	 */
	List<AccountBo> findByBIds(Set<Long> accountIds);

	/**
	 * 列表
	 *
	 * @param accountBo 业务model
	 * @return
	 */
	List<AccountBo> findList(AccountBo accountBo);

	/**
	 * 列表
	 *
	 * @param accountBo 业务model
	 * @return 键值对
	 */
	Map<Long, AccountBo> findMap(AccountBo accountBo);

	/**
	 * 分页
	 *
	 * @param accountBo
	 * @return
	 */
	IPage<AccountBo> findPage(AccountBo accountBo);

	/**
	 * 注解：@fillFieldName，填充字段的专用方法
	 * 如果不需要用该注解，直接删除该方法即可
	 * @param bIds 业务model
	 * @return 键值对
	 */
	Map<Long, String> fillFieldName(Set<Long> bIds);
}

@Slf4j
@Service
@Validated
public class AccountServiceImpl extends BaseServiceImpl<IAccountDao, Account> implements IAccountService {

	@Override
	public AccountBo findByBId(Long bid) {
		Account account = super.getByBId(bid);
		return BeanUtil.copy(account, AccountBo.class);
	}

	@Override
	public List<AccountBo> findByBIds(Set<Long> accountIds) {
		List<Account> accounts = super.getBatchBIds(accountIds);
		return BeanUtil.copy(accounts, AccountBo.class);
	}

	@Override
	public List<AccountBo> findList(AccountBo accountBo) {
		List<Account> accounts = super.list(this.query(accountBo));
		return BeanUtil.copy(accounts, AccountBo.class);
	}

	@Override
	public Map<Long, AccountBo> findMap(AccountBo accountBo) {
		List<AccountBo> accountBos = this.findList(accountBo);
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceImpl find accountBos is empty");
			return Collections.emptyMap();
		}
		return accountBos.stream().collect(Collectors.toMap(AccountBo::getAccountId, Function.identity()));
	}

	@Override
	public IPage<AccountBo> findPage(AccountBo model) {
		IPage<Account> modelPage = super.page(PageUtil.toPage(model), this.query(model));
		return PageUtil.toPage(modelPage, AccountBo.class);
	}

	@Override
	public Map<Long, String> fillFieldName(Set<Long> bIds) {
		List<AccountBo> accountBos = this.findByBIds(bIds);
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceImpl find accountBos is empty");
			return Collections.emptyMap();
		}
		return accountBos.stream().collect(Collectors.toMap(AccountBo::getAccountId, AccountBo::getAccountName));
	}

	private LambdaQueryWrapper<Account> query(AccountBo model) {
	    LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<Account>();
			wrapper.eq(ObjectUtil.isNotNull(model.getAccountId()), Account::getAccountId, model.getAccountId());
			wrapper.eq(ObjectUtil.isNotNull(model.getAccountType()), Account::getAccountType, model.getAccountType());
			wrapper.eq(StringUtil.isNotBlank(model.getMobile()), Account::getMobile, model.getMobile());
			wrapper.eq(StringUtil.isNotBlank(model.getEmail()), Account::getEmail, model.getEmail());
			wrapper.eq(StringUtil.isNotBlank(model.getLoginName()), Account::getLoginName, model.getLoginName());
			wrapper.eq(StringUtil.isNotBlank(model.getPassword()), Account::getPassword, model.getPassword());
			wrapper.eq(StringUtil.isNotBlank(model.getSalt()), Account::getSalt, model.getSalt());
			wrapper.eq(StringUtil.isNotBlank(model.getRealName()), Account::getRealName, model.getRealName());
			wrapper.eq(StringUtil.isNotBlank(model.getAvatar()), Account::getAvatar, model.getAvatar());
			wrapper.eq(ObjectUtil.isNotNull(model.getLanguage()), Account::getLanguage, model.getLanguage());
			wrapper.eq(ObjectUtil.isNotNull(model.getLengthUnit()), Account::getLengthUnit, model.getLengthUnit());
			wrapper.eq(ObjectUtil.isNotNull(model.getVolumeUnit()), Account::getVolumeUnit, model.getVolumeUnit());
			wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), Account::getStatus, model.getStatus());
			wrapper.eq(ObjectUtil.isNotNull(model.getDistribution()), Account::getDistribution, model.getDistribution());
			wrapper.eq(ObjectUtil.isNotNull(model.getLanded()), Account::getLanded, model.getLanded());
			wrapper.eq(ObjectUtil.isNotNull(model.getLoginStatus()), Account::getLoginStatus, model.getLoginStatus());
			wrapper.eq(ObjectUtil.isNotNull(model.getLastLoginTime()), Account::getLastLoginTime, model.getLastLoginTime());
		return wrapper;
	}
```
## 常见问题
### controller 生成代码异常
![Image text](../res/8.jpg)
> 需要引用sjroom-secrity,做权限管控，如果项目不需要，直接删除图上红框相关代码即可。
```xml
<dependency>
    <groupId>github.sjroom</groupId>
    <artifactId>sjroom-secrity</artifactId>
</dependency>
```
### service 生成代码异常
![Image text](../res/9.jpg)

> fillFieldName方法是专门给@fillFieldName注解提供的方法，该注解的作用请参考xxxx。如果不需要直接删除该方法就好，
如果需要，就将该展示的名称对应的字段更改好。
