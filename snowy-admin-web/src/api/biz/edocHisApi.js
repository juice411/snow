import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/edocHis/` + url, ...arg)

/**
 * 档案历史Api接口管理器
 *
 * @author juice
 * @date  2023/10/29 19:11
 **/
export default {
	// 获取档案历史分页
	edocHisPage(data) {
		return request('page', data, 'get')
	},
	// 提交档案历史表单 edit为true时为编辑，默认为新增
	edocHisSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除档案历史
	edocHisDelete(data) {
		return request('delete', data)
	},
	// 获取档案历史详情
	edocHisDetail(data) {
		return request('detail', data, 'get')
	}
}
