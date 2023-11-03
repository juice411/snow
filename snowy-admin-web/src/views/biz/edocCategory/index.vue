<template>
	<a-row>
		<a-col :span="5">
			<a-card class="cardImp" :bordered="false" :loading="cardLoading">
				<a-tree
					v-if="treeData.length > 0"
					v-model:expandedKeys="defaultExpandedKeys"
					:tree-data="treeData"
					:field-names="treeFieldNames"
					@select="treeSelect"
				>
				</a-tree>
				<a-empty v-else :image="Empty.PRESENTED_IMAGE_SIMPLE" />
			</a-card>
		</a-col>
		<a-col :span="19">
			<a-card :bordered="false" style="margin-bottom: 10px">
				<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
					<a-row :gutter="24">
						<a-col :span="6">
							<a-form-item label="名称" name="name">
								<a-input v-model:value="searchFormState.name" placeholder="请输入名称" />
							</a-form-item>
						</a-col>
						<a-col :span="6">
							<a-form-item label="编码" name="code">
								<a-input v-model:value="searchFormState.code" placeholder="请输入编码" />
							</a-form-item>
						</a-col>
						<a-col :span="6">
							<a-button type="primary" @click="table.refresh(true)">查询</a-button>
							<a-button style="margin: 0 8px" @click="reset">重置</a-button>
						</a-col>
					</a-row>
				</a-form>
			</a-card>
			<a-card :bordered="false">
				<s-table
					ref="table"
					:columns="columns"
					:data="loadData"
					:alert="options.alert.show"
					bordered
					:row-key="(record) => record.id"
					:tool-config="toolConfig"
					:row-selection="options.rowSelection"
				>
					<template #operator class="table-operator">
						<a-space>
							<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('edocCategoryAdd')">
								<template #icon><plus-outlined /></template>
								新增
							</a-button>
							<xn-batch-delete
								v-if="hasPerm('edocCategoryBatchDelete')"
								:selectedRowKeys="selectedRowKeys"
								@batchDelete="deleteBatchEdocCategory"
							/>
						</a-space>
					</template>
					<template #bodyCell="{ column, record }">
                        <template v-if="column.dataIndex === 'parent'">
                            <a-tag color="blue" v-if="record.parent">{{ record.parent }}</a-tag>
                            <a-tag color="green" v-else>一级类目</a-tag>
                        </template>
						<template v-if="column.dataIndex === 'action'">
							<a-space>
								<a @click="formRef.onOpen(record)" v-if="hasPerm('edocCategoryEdit')">编辑</a>
								<a-divider type="vertical" v-if="hasPerm(['edocCategoryEdit', 'edocCategoryDelete'], 'and')" />
								<a-popconfirm title="确定要删除吗？" @confirm="deleteEdocCategory(record)">
									<a-button type="link" danger size="small" v-if="hasPerm('edocCategoryDelete')">删除</a-button>
								</a-popconfirm>
							</a-space>
						</template>
					</template>
				</s-table>
			</a-card>
		</a-col>
	</a-row>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="edoccategory">
import Form from './form.vue'
import edocCategoryApi from '@/api/biz/edocCategoryApi'
import {isEmpty} from "lodash-es";
import {Empty} from "ant-design-vue";

let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '名称',
			dataIndex: 'name',
			/*ellipsis: true*/
		},
		{
			title: '编码',
			dataIndex: 'code',
			/*ellipsis: true*/
		},
		/*{
			title: '排序码',
			dataIndex: 'sortCode'
		},*/
		{
			title: '上级类目',
			dataIndex: 'parent',
			/*ellipsis: true*/
		},
	]
	// 默认展开的节点
	const defaultExpandedKeys = ref([])
	const treeData = ref([])
	const cardLoading = ref(true)
	// 替换treeNode 中 title,key,children
	const treeFieldNames = { children: 'children', title: 'name', key: 'id' }
	// 操作栏通过权限判断是否显示
	if (hasPerm(['edocCategoryEdit', 'edocCategoryDelete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: '150px'
		})
	}
	const selectedRowKeys = ref([])
	// 列表选择配置
	const options = {
		// columns数字类型字段加入 needTotal: true 可以勾选自动算账
		alert: {
			show: true,
			clear: () => {
				selectedRowKeys.value = ref([])
			}
		},
		rowSelection: {
			onChange: (selectedRowKey, selectedRows) => {
				selectedRowKeys.value = selectedRowKey
			}
		}
	}
	const loadData = (parameter) => {
		loadTreeData()
		const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
		return edocCategoryApi.edocCategoryPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteEdocCategory = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		edocCategoryApi.edocCategoryDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchEdocCategory = (params) => {
		edocCategoryApi.edocCategoryDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const loadTreeData = () => {
		edocCategoryApi
			.edocCategoryTree()
			.then((res) => {
				cardLoading.value = false
				if (res !== null) {
					treeData.value = res
					if (isEmpty(defaultExpandedKeys.value)) {
						// 默认展开2级
						treeData.value.forEach((item) => {
							// 因为0的顶级
							if (item.parentId === '0') {
								defaultExpandedKeys.value.push(item.id)
								// 取到下级ID
								if (item.children) {
									item.children.forEach((items) => {
										defaultExpandedKeys.value.push(items.id)
									})
								}
							}
						})
					}
				}
			})
			.finally(() => {
				cardLoading.value = false
			})
	}
	// 点击树查询
	const treeSelect = (selectedKeys) => {
		//console.log(selectedKeys.toString())
		if (selectedKeys.length > 0) {
			//console.log(selectedKeys.toString())
			searchFormState.parentId = selectedKeys.toString()
		} else {
			delete searchFormState.parentId
		}
		table.value.refresh(true)
	}
</script>
<style scoped>
.cardImp {
	margin-right: 10px;
}
.ant-form-item {
	margin-bottom: 0 !important;
}
.primaryAdd {
	margin-right: 10px;
}
.snowy-buttom-left {
	margin-left: 8px;
}
</style>
