//package com.originaltek.template.controller;
//
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.originaltek.template.annotation.JsonParam;
//import com.originaltek.template.controller.base.BaseController;
//import com.originaltek.template.model.common.AppResponse;
//import com.originaltek.template.model.domain.BseCamera;
//import com.originaltek.template.model.query.TestQueryParam;
//import com.originaltek.template.service.BseCameraService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.validation.Valid;
//import java.time.LocalDateTime;
//
///**
// * <p>
// * 前端控制器
// * </p>
// *
// * @author generator
// * @since 2019-09-05
// */
//@RestController
//@RequestMapping("/bse-camera")
//@Api("摄像头信息接口")
//public class BseCameraController extends BaseController {
//
//    @Resource
//    private BseCameraService bseCameraService;
//
//    @GetMapping("/test")
//    @ApiOperation("测试")
//    public AppResponse test() {
//        return AppResponse.newResponseEntity(bseCameraService.lambdaQuery().eq(BseCamera::getId, 1).list());
//    }
//
//    @GetMapping("/test1")
//    @ApiOperation("测试1")
//    public AppResponse test1() {
//        return AppResponse.newResponseEntity(bseCameraService.lambdaQuery().page(new Page<>(1, 3, true)));
//    }
//
//    @PostMapping("/test3")
//    @ApiOperation("测试3")
//    public AppResponse foo(@RequestBody @Valid TestQueryParam param) {
//        return AppResponse.newResponseEntity("test");
//    }
//
//    @PostMapping("/test4")
//    @ApiOperation("测试4")
//    public AppResponse bar(@JsonParam @Valid TestQueryParam param) {
//        return AppResponse.newResponseEntity(LocalDateTime.now());
//    }
//
//    @GetMapping("/test5")
//    @ApiOperation("测试5")
//    public AppResponse bar2() {
//        return AppResponse.newResponseEntity(LocalDateTime.now());
//    }
//
//}
//
