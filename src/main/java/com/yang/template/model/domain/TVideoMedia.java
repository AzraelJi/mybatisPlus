package com.yang.template.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TVideoMedia对象", description="")
public class TVideoMedia extends Model<TVideoMedia> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("categoryid")
    private String categoryid;

    @TableField("mediauuid")
    private String mediauuid;

    @ApiModelProperty(value = "1:视频；2：音频")
    @TableField("mediatype")
    private Boolean mediatype;

    @TableField("mediaorginalname")
    private String mediaorginalname;

    @ApiModelProperty(value = "视频大小，单位MB")
    @TableField("mediaorginalsize")
    private Double mediaorginalsize;

    @ApiModelProperty(value = "视频点播消耗流量")
    @TableField("media_played_size")
    private Double mediaPlayedSize;

    @TableField("mediaorginaltype")
    private String mediaorginaltype;

    @TableField("mediaorginalpath")
    private String mediaorginalpath;

    @TableField("uploadtime")
    private LocalDateTime uploadtime;

    @TableField("uploaduser")
    private String uploaduser;

    @ApiModelProperty(value = "1:已经转码；0：未转码；-1：转码失败；")
    @TableField("istranscode")
    private Boolean transcode;

    @TableField("mediatspath")
    private String mediatspath;

    @TableField("mediam3u8path")
    private String mediam3u8path;

    @TableField("mediaplayurl")
    private String mediaplayurl;

    @TableField("meidiaduration")
    private Integer meidiaduration;

    @ApiModelProperty(value = "1:正常；0：回收站")
    @TableField("meidiastatus")
    private Boolean meidiastatus;

    @ApiModelProperty(value = "视频说明")
    @TableField("mediaremark")
    private String mediaremark;

    @ApiModelProperty(value = "封面图片")
    @TableField("mediaphoto")
    private String mediaphoto;

    @TableField("create_time")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
