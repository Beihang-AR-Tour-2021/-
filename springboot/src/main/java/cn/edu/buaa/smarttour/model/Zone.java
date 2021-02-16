package cn.edu.buaa.smarttour.model;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Zone {
    private String name;
    private String info;
    private String address;
    private String time;
    private String photo_url;
    private Long zid;
}
