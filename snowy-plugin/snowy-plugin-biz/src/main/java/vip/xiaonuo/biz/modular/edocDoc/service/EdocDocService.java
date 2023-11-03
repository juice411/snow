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
package vip.xiaonuo.biz.modular.edocDoc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.edocDoc.entity.EdocDoc;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocAddParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocEditParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocIdParam;
import vip.xiaonuo.biz.modular.edocDoc.param.EdocDocPageParam;

import java.util.List;

/**
 * 档案Service接口
 *
 * @author juice
 * @date  2023/10/18 16:20
 **/
public interface EdocDocService extends IService<EdocDoc> {

    /**
     * 获取档案分页
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    Page<EdocDoc> page(EdocDocPageParam edocDocPageParam);

    /**
     * 添加档案
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    void add(EdocDocAddParam edocDocAddParam);

    /**
     * 编辑档案
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    void edit(EdocDocEditParam edocDocEditParam);

    /**
     * 删除档案
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    void delete(List<EdocDocIdParam> edocDocIdParamList);

    /**
     * 获取档案详情
     *
     * @author juice
     * @date  2023/10/18 16:20
     */
    EdocDoc detail(EdocDocIdParam edocDocIdParam);

    /**
     * 获取档案详情
     *
     * @author juice
     * @date  2023/10/18 16:20
     **/
    EdocDoc queryEntity(String id);
}
