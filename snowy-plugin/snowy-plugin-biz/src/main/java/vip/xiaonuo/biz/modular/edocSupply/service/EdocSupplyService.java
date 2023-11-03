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
package vip.xiaonuo.biz.modular.edocSupply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.edocSupply.entity.EdocSupply;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyAddParam;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyEditParam;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyIdParam;
import vip.xiaonuo.biz.modular.edocSupply.param.EdocSupplyPageParam;

import java.util.List;

/**
 * 档案来源Service接口
 *
 * @author juice
 * @date  2023/10/18 15:41
 **/
public interface EdocSupplyService extends IService<EdocSupply> {

    /**
     * 获取档案来源分页
     *
     * @author juice
     * @date  2023/10/18 15:41
     */
    Page<EdocSupply> page(EdocSupplyPageParam edocSupplyPageParam);

    /**
     * 添加档案来源
     *
     * @author juice
     * @date  2023/10/18 15:41
     */
    void add(EdocSupplyAddParam edocSupplyAddParam);

    /**
     * 编辑档案来源
     *
     * @author juice
     * @date  2023/10/18 15:41
     */
    void edit(EdocSupplyEditParam edocSupplyEditParam);

    /**
     * 删除档案来源
     *
     * @author juice
     * @date  2023/10/18 15:41
     */
    void delete(List<EdocSupplyIdParam> edocSupplyIdParamList);

    /**
     * 获取档案来源详情
     *
     * @author juice
     * @date  2023/10/18 15:41
     */
    EdocSupply detail(EdocSupplyIdParam edocSupplyIdParam);

    /**
     * 获取档案来源详情
     *
     * @author juice
     * @date  2023/10/18 15:41
     **/
    EdocSupply queryEntity(String id);
}
