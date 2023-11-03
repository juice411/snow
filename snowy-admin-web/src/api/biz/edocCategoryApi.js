import {baseRequest} from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/edocCategory/` + url, ...arg)

/**
 * 档案类目Api接口管理器
 *
 * @author juice
 * @date  2023/10/17 16:55
 **/
export default {
	// 获取档案类目分页
	edocCategoryPage(data) {
		return request('page', data, 'get')
	},
	// 提交档案类目表单 edit为true时为编辑，默认为新增
	edocCategorySubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除档案类目
	edocCategoryDelete(data) {
		return request('delete', data)
	},
	// 获取档案类目详情
	edocCategoryDetail(data) {
		return request('detail', data, 'get')
	},
	// 获取类目树
	edocCategoryTree(data) {
		return request('tree', data, 'get')
	},
	// 获取类目树选择器
	edocCategoryTreeSelector(data) {
		return request('edocCategoryTreeSelector', data, 'get')
	},
}
