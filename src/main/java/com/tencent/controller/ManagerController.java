package com.tencent.controller;

import com.tencent.service.MemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private MemService memService;

    @GetMapping("memInfo")
    public Map<String, Long> listMemInfo() {
        return memService.queryMemInfo();
    }


    @GetMapping("consume")
    public String consumeMem() {
        memService.consumeMem();
        return "success";
    }

    @GetMapping("record")
    public String record() {
        memService.recordSystemInfo();
        return "success";
    }
}
