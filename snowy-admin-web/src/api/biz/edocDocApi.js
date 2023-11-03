import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/edocDoc/` + url, ...arg)

/**
 * 档案Api接口管理器
 *
 * @author juice
 * @date  2023/10/18 16:20
 **/
export default {
	// 获取档案分页
	edocDocPage(data) {
		return request('page', data, 'get')
	},
	// 提交档案表单 edit为true时为编辑，默认为新增
	edocDocSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除档案
	edocDocDelete(data) {
		return request('delete', data)
	},
	// 获取档案详情
	edocDocDetail(data) {
		return request('detail', data, 'get')
	}
}
