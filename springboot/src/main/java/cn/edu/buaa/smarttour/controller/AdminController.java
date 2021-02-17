package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.mappers.ZoneMapper;
import cn.edu.buaa.smarttour.model.Customer;
import cn.edu.buaa.smarttour.model.Zone;
import cn.edu.buaa.smarttour.service.AdminService;
import cn.edu.buaa.smarttour.utils.Response;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Api(tags = {"管理员相关接口"})
public class AdminController {

    private AdminService adminService;
    @Autowired
    private ZoneMapper zoneMapper;

    @GetMapping("/zone")
    @ApiOperation("/获取所有景区信息")
    public Map<String, Object> getAllZones(){
        Map<String, Object> map = new HashMap<>();
        List<Zone> zonelist = new ArrayList<Zone>();
        AdminService adminService = new AdminService(zoneMapper);
        zonelist = adminService.allZones();
        if(zonelist == null){
            map.put("response", "failed");
            map.put("msg", "get all zones failed");
            return map;
        }
        map.put("response", "success");
        map.put("msg", "Search success");
        map.put("info", zonelist);
        return map;
    }

    @PostMapping("/zone")
    @ApiOperation("/添加新的景区")
    @ApiImplicitParams({

    })
    public Map<String, Object> addNewZones(Zone zone){
        Map<String, Object> map = new HashMap<>();
        AdminService adminService = new AdminService(zoneMapper);
        int result = adminService.addZone(zone);
        if(result <= 0){
            map.put("response", "failed");
            map.put("msg", "add zone failed.");
            return map;
        }
        else{
            map.put("response", "success");
            map.put("msg", "add zone successful.");
            return map;
        }
    }

    @PutMapping("/zone")
    @ApiOperation("/修改景区信息")
    @ApiImplicitParams({

    })
    public Map<String, Object> editZone(Zone zone){
        Map<String, Object> map = new HashMap<>();
        AdminService adminService = new AdminService(zoneMapper);
        int res = adminService.editZone(zone);
        if(res <= 0){
            map.put("response", "failed");
            map.put("msg", "edit zone failed.");
            return map;
        }
        else{
            map.put("response", "success");
            map.put("msg", "edit zone successful.");
            return map;
        }
    }

    @DeleteMapping("/zone")
    @ApiOperation("/删除景区")
    @ApiImplicitParams({

    })
    public Map<String, Object> deleteZone(Zone zone){
        Map<String, Object> map = new HashMap<>();
        int res = adminService.deleteZone(zone);
        if(res <= 0){
            map.put("response", "failed");
            map.put("msg", "edit zone failed.");
            return map;
        }
        else{
            map.put("response", "success");
            map.put("msg", "edit zone successful.");
            return map;
        }
    }

}
