package cn.edu.buaa.smarttour.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Spot {
    private String name;
    private String info;
    private Long aid;
    private Long zid;
}
