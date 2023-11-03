/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.edocMonitor.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.edocMonitor.entity.EdocDocStatusAgg;
import vip.xiaonuo.biz.modular.edocMonitor.service.EdocDocStatusAggService;
import vip.xiaonuo.common.pojo.CommonResult;

import javax.annotation.Resource;

/**
 * 监控控制器
 *
 * @author xuyuxiang
 * @date 2022/6/21 14:57
 **/
@Api(tags = "档案状态统计")
@ApiSupport(author = "SNOWY_TEAM", order = 9)
@RestController
@Validated
public class EdocMonitorStatusController {


    @Resource
    private EdocDocStatusAggService edocDocStatusAggService;


    /**
     * 获取服务器监控信息
     *
     * @author xuyuxiang
     * @date 2022/4/24 20:00
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取档案状态统计信息")
    @GetMapping("/edoc/monitor/edocDocStatusAgg")
    public CommonResult<Page<EdocDocStatusAgg>> page() {
        return CommonResult.data(edocDocStatusAggService.edocDocStatusAggList());
    }
}
