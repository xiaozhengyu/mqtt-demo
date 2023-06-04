package com.xzy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 说明：用户坐标
 *
 * @author xzy
 * @date 2023/6/4  16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationDTO {
    /**
     * X坐标
     */
    private Double x;
    /**
     * Y坐标
     */
    private Double y;
    /**
     * Z坐标
     */
    private Double z;
    /**
     * 楼层
     */
    private Integer f;
    /**
     * 发生时间
     */
    private LocalDateTime rt;
    /**
     * 传感器SN
     */
    private String sn;
}
