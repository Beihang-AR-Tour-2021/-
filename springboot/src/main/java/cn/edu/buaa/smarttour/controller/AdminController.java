package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.service.AdminService;
import cn.edu.buaa.smarttour.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@Api(tags = {"管理员相关接口"})
public class AdminController {

    private AdminService adminService;

    @GetMapping("/zone")
    @ApiOperation("/景区管理操作")
    public Map<String, Object> getAllSpots(){
        return null;
    }

}
