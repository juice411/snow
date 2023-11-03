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
package vip.xiaonuo.biz.modular.edocSpot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.edocDoc.entity.EdocDoc;
import vip.xiaonuo.biz.modular.edocDoc.service.EdocDocService;
import vip.xiaonuo.biz.modular.edocSpot.entity.EdocSpot;
import vip.xiaonuo.biz.modular.edocSpot.mapper.EdocSpotMapper;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotAddParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotEditParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotIdParam;
import vip.xiaonuo.biz.modular.edocSpot.param.EdocSpotPageParam;
import vip.xiaonuo.biz.modular.edocSpot.service.EdocSpotService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * 档案架Service接口实现类
 *
 * @author juice
 * @date  2023/10/18 15:37
 **/
@Service
public class EdocSpotServiceImpl extends ServiceImpl<EdocSpotMapper, EdocSpot> implements EdocSpotService {

    @Resource
    private EdocDocService edocDocService;
    @Override
    public Page<EdocSpot> page(EdocSpotPageParam edocSpotPageParam) {
        QueryWrapper<EdocSpot> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(edocSpotPageParam.getName())) {
            queryWrapper.lambda().like(EdocSpot::getName, edocSpotPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(edocSpotPageParam.getCode())) {
            queryWrapper.lambda().like(EdocSpot::getCode, edocSpotPageParam.getCode());
        }
        if(ObjectUtil.isAllNotEmpty(edocSpotPageParam.getSortField(), edocSpotPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(edocSpotPageParam.getSortOrder());
            queryWrapper.orderBy(true, edocSpotPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(edocSpotPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EdocSpot::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EdocSpotAddParam edocSpotAddParam) {
        if (this.count(new LambdaQueryWrapper<EdocSpot>()
                .eq(EdocSpot::getName, edocSpotAddParam.getName()))> 0) {
            throw new CommonException("存在重复的档案架名称，名称为：{}", edocSpotAddParam.getName());
        }
        EdocSpot edocSpot = BeanUtil.toBean(edocSpotAddParam, EdocSpot.class);
        this.save(edocSpot);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EdocSpotEditParam edocSpotEditParam) {
        if (this.count(new LambdaQueryWrapper<EdocSpot>()
                .eq(EdocSpot::getName, edocSpotEditParam.getName())
                .ne(EdocSpot::getId, edocSpotEditParam.getId())) > 0) {
            throw new CommonException("存在重复的档案架名称，名称为：{}", edocSpotEditParam.getName());
        }
        EdocSpot edocSpot = this.queryEntity(edocSpotEditParam.getId());
        BeanUtil.copyProperties(edocSpotEditParam, edocSpot);
        this.updateById(edocSpot);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EdocSpotIdParam> edocSpotIdParamList) {
        List<String> edocSpotIdList = CollStreamUtil.toList(edocSpotIdParamList, EdocSpotIdParam::getId);
        // 档案架下有档案不能删除
        boolean hasOrgUser = edocDocService.count(new LambdaQueryWrapper<EdocDoc>().in(EdocDoc::getSpotId, edocSpotIdList)) > 0;
        if(hasOrgUser) {
            throw new CommonException("请先删除档案架下的档案");
        }
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(edocSpotIdParamList, EdocSpotIdParam::getId));
    }

    @Override
    public EdocSpot detail(EdocSpotIdParam edocSpotIdParam) {
        return this.queryEntity(edocSpotIdParam.getId());
    }

    @Override
    public EdocSpot queryEntity(String id) {
        EdocSpot edocSpot = this.getById(id);
        if(ObjectUtil.isEmpty(edocSpot)) {
            throw new CommonException("档案架不存在，id值为：{}", id);
        }
        return edocSpot;
    }

}
