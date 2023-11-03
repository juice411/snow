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
package vip.xiaonuo.biz.modular.edocDoc.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.biz.modular.edocDoc.entity.EdocDoc;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocAddParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocEditParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocIdParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocPageParam;
import vip.xiaonuo.biz.modular.edocDoc.service.EdocDocService;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 档案控制器
 *
 * @author juice
 * @date  2023/10/18 16:20
 */
@Api(tags = "档案控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class EdocDocController {

    @Resource
    private EdocDocService edocDocService;

    /**
     * 获取档案分页
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取档案分页")
    @SaCheckPermission("/biz/edocDoc/page")
    @GetMapping("/biz/edocDoc/page")
    public CommonResult<Page<EdocDoc>> page(EdocDocPageParam edocDocPageParam) {
        return CommonResult.data(edocDocService.page(edocDocPageParam));
    }

    /**
     * 添加档案
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加档案")
    @CommonLog("添加档案")
    @SaCheckPermission("/biz/edocDoc/add")
    @PostMapping("/biz/edocDoc/add")
    public CommonResult<String> add(@RequestBody @Valid EdocDocAddParam edocDocAddParam) {
        edocDocService.add(edocDocAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑档案
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑档案")
    @CommonLog("编辑档案")
    @SaCheckPermission("/biz/edocDoc/edit")
    @PostMapping("/biz/edocDoc/edit")
    public CommonResult<String> edit(@RequestBody @Valid EdocDocEditParam edocDocEditParam) {
        edocDocService.edit(edocDocEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除档案
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除档案")
    @CommonLog("删除档案")
    @SaCheckPermission("/biz/edocDoc/delete")
    @PostMapping("/biz/edocDoc/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<EdocDocIdParam> edocDocIdParamList) {
        edocDocService.delete(edocDocIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取档案详情
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取档案详情")
    @SaCheckPermission("/biz/edocDoc/detail")
    @GetMapping("/biz/edocDoc/detail")
    public CommonResult<EdocDoc> detail(@Valid EdocDocIdParam edocDocIdParam) {
        return CommonResult.data(edocDocService.detail(edocDocIdParam));
    }
}
