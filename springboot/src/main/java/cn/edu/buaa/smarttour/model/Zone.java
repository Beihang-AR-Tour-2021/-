package cn.edu.buaa.smarttour.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Zone {
    private Long zid;
    private String name;
    private String info;
    private String address;
    private String time;
    private String photo_url;
}
