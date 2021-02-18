package cn.edu.buaa.smarttour.service;

import cn.edu.buaa.smarttour.mappers.ZoneMapper;
import cn.edu.buaa.smarttour.model.Customer;
import cn.edu.buaa.smarttour.model.Zone;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ZoneService {

    @Autowired
    private ZoneMapper zoneMapper;

    /**
     * 模糊查询景区
     * @param keyword
     * @return
     */
    public List<Zone> findZones(String keyword){
        QueryWrapper<Zone> wrapper = new QueryWrapper<>();
        wrapper.like("name", keyword);
        List<Zone> findZones = zoneMapper.selectList(wrapper);
        return findZones;
    }
}
