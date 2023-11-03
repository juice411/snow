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
package vip.xiaonuo.biz.modular.edocHis.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.edocHis.entity.EdocHis;
import vip.xiaonuo.biz.modular.edocHis.mapper.EdocHisMapper;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisAddParam;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisEditParam;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisIdParam;
import vip.xiaonuo.biz.modular.edocHis.param.EdocHisPageParam;
import vip.xiaonuo.biz.modular.edocHis.service.EdocHisService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;

import java.util.List;

/**
 * 档案历史Service接口实现类
 *
 * @author juice
 * @date  2023/10/29 19:11
 **/
@Service
public class EdocHisServiceImpl extends ServiceImpl<EdocHisMapper, EdocHis> implements EdocHisService {

    @Override
    public Page<EdocHis> page(EdocHisPageParam edocHisPageParam) {
        QueryWrapper<EdocHis> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(edocHisPageParam.getName())) {
            queryWrapper.lambda().like(EdocHis::getName, edocHisPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(edocHisPageParam.getCode())) {
            queryWrapper.lambda().like(EdocHis::getCode, edocHisPageParam.getCode());
        }
        if(ObjectUtil.isNotEmpty(edocHisPageParam.getCategoryId())) {
            queryWrapper.lambda().eq(EdocHis::getCategoryId, edocHisPageParam.getCategoryId());
        }
        if(ObjectUtil.isNotEmpty(edocHisPageParam.getStatus())) {
            queryWrapper.lambda().eq(EdocHis::getStatus, edocHisPageParam.getStatus());
        }
        if(ObjectUtil.isNotEmpty(edocHisPageParam.getBorrowId())) {
            queryWrapper.lambda().eq(EdocHis::getBorrowId, edocHisPageParam.getBorrowId());
        }
        if(ObjectUtil.isNotEmpty(edocHisPageParam.getStartCreateTime()) && ObjectUtil.isNotEmpty(edocHisPageParam.getEndCreateTime())) {
            queryWrapper.lambda().between(EdocHis::getCreateTime, edocHisPageParam.getStartCreateTime(), edocHisPageParam.getEndCreateTime());
        }
        if(ObjectUtil.isAllNotEmpty(edocHisPageParam.getSortField(), edocHisPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(edocHisPageParam.getSortOrder());
            queryWrapper.orderBy(true, edocHisPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(edocHisPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(EdocHis::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(EdocHisAddParam edocHisAddParam) {
        EdocHis edocHis = BeanUtil.toBean(edocHisAddParam, EdocHis.class);
        this.save(edocHis);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(EdocHisEditParam edocHisEditParam) {
        EdocHis edocHis = this.queryEntity(edocHisEditParam.getId());
        BeanUtil.copyProperties(edocHisEditParam, edocHis);
        this.updateById(edocHis);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<EdocHisIdParam> edocHisIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(edocHisIdParamList, EdocHisIdParam::getId));
    }

    @Override
    public EdocHis detail(EdocHisIdParam edocHisIdParam) {
        return this.queryEntity(edocHisIdParam.getId());
    }

    @Override
    public EdocHis queryEntity(String id) {
        EdocHis edocHis = this.getById(id);
        if(ObjectUtil.isEmpty(edocHis)) {
            throw new CommonException("档案历史不存在，id值为：{}", id);
        }
        return edocHis;
    }
}
