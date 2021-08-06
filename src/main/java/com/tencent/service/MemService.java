package com.tencent.service;


import com.alibaba.fastjson.JSON;
import com.tencent.conf.SysConfig;
import com.tencent.dao.DataDao;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemService {
    @Resource
    private DataDao dataDao;


    @Resource
    private SysConfig sysConfig;

    /**
     * 获取机器的内存信息
     *
     * @return
     */
    public Map<String, Long> queryMemInfo() {
        Map<String, Long> map = new HashMap<>();
        File file = new File("/proc/meminfo");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file)));
            while (true) {
                String str = br.readLine();
                if (null == str) break;
                convertMap(map, str);
            }
        } catch (Exception e) {
            System.out.println("error :" + e.getMessage());
        }
        return map;
    }


    /**
     * 消耗指定内存大小
     */
    public void consumeMem() {
        Map<String, Long> memInfo = queryMemInfo();
        Long memFree = MapUtils.getLong(memInfo, "MemAvailable");
        System.out.println("MemAvailable: " + (memFree / 1024));
        int sizeof100M = 1024 * 100;

        //每次消耗30M 需要count次
        int count = Math.toIntExact(memFree * 3 / (sizeof100M * 4));

        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            byte[] bytes = new byte[1024 * 1024 * 100]; //100M
            list.add(bytes);
            System.out.println("第 " + i + "次分配内存");
            try {
                Thread.sleep(1000 * 5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("list的size: " + count);
    }


    /**
     * 向db中保存系统相关的信息
     */
    public void recordSystemInfo() {

        try {
            Map<String, Object> map = new HashMap<>();
            // ip
            String ip = sysConfig.getIp();
            map.put("ip", ip);

            //cpu个数
            int cpuNum = Runtime.getRuntime().availableProcessors();
            map.put("cpuCount", cpuNum);

            Map<String, Long> memInfo = queryMemInfo();
            Long memTotal = MapUtils.getLong(memInfo, "MemTotal", 0L);
            Long memAvailable = MapUtils.getLong(memInfo, "MemAvailable", 0L);
            Long memFree = MapUtils.getLong(memInfo, "MemFree", 0L);
            map.put("memTotal", memTotal);
            map.put("memAvailable", memAvailable);
            map.put("memFree", memFree);

            //负载
            HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
//            long available = hardware.getMemory().getAvailable();   //单位byte
//            System.out.println("available: " + available);
//            long total = hardware.getMemory().getTotal();  // 单位 byte
//            System.out.println("total: " + total);
            double[] systemLoadAverage = hardware.getProcessor().getSystemLoadAverage(3);
            String systemLoadAverageStr = JSON.toJSONString(systemLoadAverage);
            map.put("loadInfo", systemLoadAverageStr);

            dataDao.recordSystemInfo(map);

        } catch (Exception exception) {
            System.err.println("Error : " + exception.getMessage());
        }


    }


    private void convertMap(Map<String, Long> map, String str) {
        String[] split = str.split(":");
        map.put(split[0].trim(), Long.parseLong(split[1].trim().split(" ")[0].trim()));
    }
}
