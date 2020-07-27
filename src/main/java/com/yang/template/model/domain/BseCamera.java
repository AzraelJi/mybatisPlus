package com.yang.template.model.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author generator
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(schema = "dwb")
@ApiModel(value = "BseCamera对象", description = "")
public class BseCamera extends Model<BseCamera> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "摄像头名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "摄像头型号")
    @TableField("model")
    private Integer model;

    @ApiModelProperty(value = "摄像头的IP地址")
    @TableField("ip_addr")
    private String ipAddr;

    @ApiModelProperty(value = "NVR开放的rtsp流地址")
    @TableField("rtsp_stream")
    private String rtspStream;

    @ApiModelProperty(value = "摄像头所在高速公路ID")
    @TableField("highway_id")
    private Integer highwayId;

    @ApiModelProperty(value = "是否是高清摄像头")
    @TableField("is_hd")
    private Boolean hd;

    @ApiModelProperty(value = "摄像头所在高速公路桩号")
    @TableField("stake_number")
    private BigDecimal stakeNumber;

    @ApiModelProperty(value = "摄像头所在高德经度坐标")
    @TableField("gd_longitude")
    private BigDecimal gdLongitude;

    @ApiModelProperty(value = "摄像头所在高德纬度坐标")
    @TableField("gd_latitude")
    private BigDecimal gdLatitude;

    @ApiModelProperty(value = "摄像头所在道路方向 0 上行  1 下行")
    @TableField("direction")
    private Integer direction;

    @ApiModelProperty(value = "摄像头的状态，1 正在进行视频分析，即画面正常，且处于预置位正在进行视频结构化分析的摄像机； 2 未在进行视频分析，即画面正常，但不处于预置位的摄像机，可能是由于监控人员正在对某些事件进行重点关注，调整了摄像机拍摄角度和缩放； 3 	状态异常（无信号、无画面）的摄像机。")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "摄像头登录用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "摄像头登录密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "摄像头登录端口")
    @TableField("port")
    private Integer port;

    @ApiModelProperty(value = "摄像头操作通道")
    @TableField("channel")
    private Integer channel;

    @ApiModelProperty(value = "摄像头扭转的预设点")
    @TableField("preset")
    private Integer preset;

    @ApiModelProperty(value = "摄像头预设点的P坐标，通过海康接口获取")
    @TableField("p_pos")
    private Integer pPos;

    @ApiModelProperty(value = "摄像头预设点的T坐标，通过海康接口获取")
    @TableField("t_pos")
    private Integer tPos;

    @ApiModelProperty(value = "摄像头预设点的Z坐标，通过海康接口获取")
    @TableField("z_pos")
    private Integer zPos;

    @ApiModelProperty(value = "摄像头监控点位的车道数量（除去应急车道），默认3")
    @TableField("lane_num")
    private Integer laneNum;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("delete_state")
    private Boolean deleteState;

    @ApiModelProperty(value = "摄像头状态0正常登陆1不正常登陆2 正在视频分析状态")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "是否锁定了摄像头")
    @TableField("lock")
    private Boolean lock;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
