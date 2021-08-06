package com.tencent.tests;

import com.alibaba.fastjson.JSON;
import com.sun.management.OperatingSystemMXBean;
import com.tencent.service.MemService;
import org.junit.jupiter.api.Test;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Test01 {
    @Test
    public void test01() throws Exception {
        File file = new File("E:\\javaCode\\personal\\test\\src\\main\\resources\\static\\info.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file)));

//        String s = br.readLine();
//        System.out.println(s);
//        while (StringUtils.isNotBlank(s)) {
//            s = br.readLine();
//            System.out.println(s);
//        }

        StringTokenizer token = new StringTokenizer("\r\n");
        while (token.hasMoreTokens()) {
            System.out.println(token.nextToken());
        }
//        token.nextToken();
//
//        String memTotal = token.nextToken();
//        token.nextToken();
//        String memFree = token.nextToken();
////        String memAvailable = token.nextToken();
//        System.out.println("memTotal: " + memTotal);
//        System.out.println("memFree: " + memFree);
//        System.out.println("memAvailable: " + memAvailable);

    }


    @Test
    public void test02() throws Exception {
        MemService memService = new MemService();
        memService.queryMemInfo();
    }

    @Test
    public void test03() throws Exception {
        Long memFree = 1048576100L;
        int sizeof30M = 1024 * 1024 * 10;
        Integer count = Math.toIntExact(memFree * 3 / (sizeof30M * 4));
        System.out.println(count);
    }

    @Test
    public void test04() throws Exception {
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            System.out.println(ip4.getHostAddress());
            System.out.println("cpu核数:" + Runtime.getRuntime().availableProcessors());

            OperatingSystemMXBean systemMXBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                    OperatingSystemMXBean.class);
            double systemLoadAverage = systemMXBean.getSystemLoadAverage();
            System.out.println(systemLoadAverage);


            OperatingSystem operatingSystem = new SystemInfo().getOperatingSystem();
            HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
            long available = hardware.getMemory().getAvailable();
            System.out.println("available: "+available);
            double[] systemLoadAverage1 = hardware.getProcessor().getSystemLoadAverage(3);

            System.out.println(JSON.toJSONString(systemLoadAverage1));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
