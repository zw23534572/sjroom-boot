package github.sjroom.core.mybatis.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @Author: manson.zhou
 * @Date: 2019/6/4 20:37
 * spring
 */
@Slf4j
public class TransactionEnhance {


    /**
     * 解决在同一个class内部的 @Transactional失效
     * @param runnable
     */
    @Transactional
    public void transactional(Runnable runnable) {
        runnable.run();
    }


    /**
     * 已判断当前调用是否在事务环境中
     * 确保spring事务提交后执行,
     * 适用于commit后刷新缓存,发送消息等场景
     *
     * @param runnable
     */
    public static void transactionAfter(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            //事务提交后处理发送事件
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }

    }
}
