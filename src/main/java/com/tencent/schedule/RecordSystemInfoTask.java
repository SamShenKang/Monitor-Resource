package com.tencent.schedule;

import com.tencent.service.MemService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableAsync
public class RecordSystemInfoTask {

    @Resource
    private MemService memService;

    /**
     * 定时记录机器信息
     */
    @Async
    @Scheduled(cron = "0 0 * * * ?") //每小时
    public void recordSystemInfo() {
        System.out.println("准备记录系统信息");
        memService.recordSystemInfo();
        System.out.println("记录系统信息完毕");
    }

    @Async
    @Scheduled(cron = "0 0 */12 * * ?") //每12个小时
    public void consumeMem() {
        System.out.println("准备消耗机器资源");
        memService.consumeMem();
        System.out.println("消耗机器资源至释放");
    }

}
