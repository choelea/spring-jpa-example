package com.joe.springjpaexample.controller;

import com.joe.springjpaexample.service.ManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Joe
 * @date : 2022/3/28
 */
@RestController("/admin")
public class AdminManagementController {

    private ManagementService managementService;

    public AdminManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @PostMapping("/actions/_init_data")
    public ResponseEntity<?> initData(){
        managementService.initData();
        return ResponseEntity.ok("success");
    }

}
