package cn.edu.buaa.smarttour.controller;

import cn.edu.buaa.smarttour.model.Zone;
import cn.edu.buaa.smarttour.service.ZoneService;
import cn.edu.buaa.smarttour.utils.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZoneController {

    private ZoneService zoneService;

    @GetMapping("/zonefinder")
    @ApiOperation("模糊查询景区信息")
    public ResponseEntity<Response> findZones(String keyword){
        Map<String, Object> map = new HashMap<>();
        List<Zone> zonelist = zoneService.findZones(keyword);
        if(zonelist == null){
            return ResponseEntity.ok(new Response(404, "未查询到相关景区信息！"));
        }
        return ResponseEntity.ok(new Response(zonelist));
    }


}
