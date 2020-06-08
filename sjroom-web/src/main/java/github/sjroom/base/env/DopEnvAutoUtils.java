package github.sjroom.base.env;

import github.sjroom.core.extension.Holder;
import github.sjroom.core.extension.SpringExtensionLoader;

/**
 * 平台环境工具包
 *
 * @author Katrel.Zhou
 * @version 1.0.0
 * @date 2019/5/17 17:44
 */
public class DopEnvAutoUtils {

    private static final String DOP_ENVIRONMENT_BEAN_NAME = "dopEnvironment";
    private static final Holder<DopEnvironmentAware> DOP_ENVIRONMENT = new Holder<>();

    /**
     * 应用缩写，三位
     *
     * @return
     */
    public static String getServerName() {
        return getDopEnvironment().getServerName();
    }

    /**
     * 获取当前环境配置
     *
     * @return
     */
    public static DopEnvironmentAware getDopEnvironment() {
        if (DOP_ENVIRONMENT.isEmpty()) {
            DOP_ENVIRONMENT.set(SpringExtensionLoader.getSpringBean(DOP_ENVIRONMENT_BEAN_NAME, DopEnvironmentAware.class));
        }
        return DOP_ENVIRONMENT.get();
    }

    /**
     * 获取当前环境的属性值
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return getDopEnvironment().getProperty(key);
    }

    /**
     * 获取当前环境的属性值
     *
     * @param key
     * @param targetType
     * @return
     */
    public static <T> T getProperty(String key, Class<T> targetType) {
        return getDopEnvironment().getProperty(key, targetType);
    }

}
