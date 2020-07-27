package com.yang.template.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author jjyy
 * @apiNote 分页基类
 * @since 2019/9/4
 */
@Data
@ApiModel("分页基类")
public class Pageable {

    private static final long serialVersionUID = 1L;
    /**
     * 页数
     */
    @NotNull(message = "page 不能为空")
    @Min(value = 1, message = "page最少为1")
    @ApiModelProperty(value = "页面数", dataType = "int")
    private Integer page;

    /**
     * 每页大小
     */
    @NotNull(message = "pageSize 不能为空")
    @Min(value = 1, message = "pageSize最少为1")
    @ApiModelProperty(value = "页面大小", dataType = "int")
    private Integer pageSize;

    /**
     * 排序规则
     */
    private List<String> orderBy;

}
