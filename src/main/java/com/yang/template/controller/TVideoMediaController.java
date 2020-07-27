package com.yang.template.controller;


import com.yang.template.controller.base.BaseController;
import com.yang.template.model.common.AppResponse;
import com.yang.template.model.domain.BseCamera;
import com.yang.template.service.TVideoMediaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author generator
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/t-video-media")
public class TVideoMediaController extends BaseController {

    @Resource
    private TVideoMediaService tVideoMediaService;

    @GetMapping("/test")
    @ApiOperation("测试")
    public AppResponse test() {
        return AppResponse.newResponseEntity(tVideoMediaService.lambdaQuery().list());
    }

}

