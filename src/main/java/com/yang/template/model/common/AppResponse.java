package com.yang.template.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("api返回包装类")
public class AppResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "状态", dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "消息")
    private Object msg;

    @ApiModelProperty(value = "模块代码", dataType = "模块代码")
    private Integer code;

    @ApiModelProperty(value = "具体数据")
    private Object data;

    @NotNull
    public static AppResponse newResponseEntity(Object o) {
        AppResponse entity = new AppResponse();
        entity.setCode(0);
        entity.setMsg("ok");
        entity.setStatus(200);
        entity.setData(o);
        return entity;
    }

}
