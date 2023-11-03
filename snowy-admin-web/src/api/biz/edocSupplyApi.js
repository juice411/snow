import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/edocSupply/` + url, ...arg)

/**
 * 档案来源Api接口管理器
 *
 * @author juice
 * @date  2023/10/18 15:41
 **/
export default {
	// 获取档案来源分页
	edocSupplyPage(data) {
		return request('page', data, 'get')
	},
	// 提交档案来源表单 edit为true时为编辑，默认为新增
	edocSupplySubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除档案来源
	edocSupplyDelete(data) {
		return request('delete', data)
	},
	// 获取档案来源详情
	edocSupplyDetail(data) {
		return request('detail', data, 'get')
	},
	// 获取档案来源选择器
	supplySelector(data) {
		return request('supplySelector', data, 'get')
	}
}
