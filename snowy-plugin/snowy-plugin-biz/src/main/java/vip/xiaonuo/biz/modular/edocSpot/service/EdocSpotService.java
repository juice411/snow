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
package vip.xiaonuo.biz.modular.edocSpot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.edocSpot.entity.EdocSpot;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotAddParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotEditParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotIdParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotPageParam;

import java.util.List;

/**
 * 档案架Service接口
 *
 * @author juice
 * @date  2023/10/18 15:37
 **/
public interface EdocSpotService extends IService<EdocSpot> {

    /**
     * 获取档案架分页
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    Page<EdocSpot> page(EdocSpotPageParam edocSpotPageParam);

    /**
     * 添加档案架
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    void add(EdocSpotAddParam edocSpotAddParam);

    /**
     * 编辑档案架
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    void edit(EdocSpotEditParam edocSpotEditParam);

    /**
     * 删除档案架
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    void delete(List<EdocSpotIdParam> edocSpotIdParamList);

    /**
     * 获取档案架详情
     *
     * @author juice
     * @date  2023/10/18 15:37
     */
    EdocSpot detail(EdocSpotIdParam edocSpotIdParam);

    /**
     * 获取档案架详情
     *
     * @author juice
     * @date  2023/10/18 15:37
     **/
    EdocSpot queryEntity(String id);
}
