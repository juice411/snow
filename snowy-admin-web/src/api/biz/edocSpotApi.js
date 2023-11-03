import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/edocSpot/` + url, ...arg)

/**
 * 档案架Api接口管理器
 *
 * @author juice
 * @date  2023/10/18 15:37
 **/
export default {
	// 获取档案架分页
	edocSpotPage(data) {
		return request('page', data, 'get')
	},
	// 提交档案架表单 edit为true时为编辑，默认为新增
	edocSpotSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除档案架
	edocSpotDelete(data) {
		return request('delete', data)
	},
	// 获取档案架详情
	edocSpotDetail(data) {
		return request('detail', data, 'get')
	}
}
