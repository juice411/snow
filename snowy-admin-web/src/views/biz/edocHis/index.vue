<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="档案名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入档案名称"/>
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="档案编号" name="code">
						<a-input v-model:value="searchFormState.code" placeholder="请输入档案编号"/>
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="类目" name="categoryId">
						<!--						<a-select v-model:value="searchFormState.categoryId" placeholder="请选择类目" :options="categoryIdOptions" />-->
						<a-form-item label="类目" name="categoryId">
							<!--						<a-select v-model:value="searchFormState.categoryId" placeholder="请选择类目" :options="categoryIdOptions" />-->
							<a-tree-select
								v-model:value="searchFormState.categoryId"
								style="width: 100%"
								:dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
								placeholder="请选择档案类目"
								allow-clear
								tree-default-expand-all
								:tree-data="treeData"
								:field-names="{
                  children: 'children',
                  label: 'name',
                  value: 'id'
                }"
								selectable="false"
								tree-line
							/>
						</a-form-item>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="档案状态" name="status">
						<a-select v-model:value="searchFormState.status" placeholder="请选择档案状态"
								  :options="statusOptions"/>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="借出单位" name="borrowId">
						<a-select v-model:value="searchFormState.borrowId" placeholder="请选择借出单位"
								  :filter-option="
                        (input, option) => {
                          if (option && option.name) {
                            return option.name.indexOf(input) >= 0
                          }
                        }
                      "
								  :allowClear="true"
								  :fieldNames="{ label: 'name', value: 'id', options: 'options' }"
								  :options="supplyList"/>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="操作时间" name="createTime">
						<a-range-picker v-model:value="searchFormState.createTime" show-time/>
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
					<a @click="toggleAdvanced" style="margin-left: 8px">
						{{ advanced ? '收起' : '展开' }}
						<component :is="advanced ? 'up-outlined' : 'down-outlined'"/>
					</a>
				</a-col>
			</a-row>
		</a-form>
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
			<!--			<template #operator class="table-operator">
							<a-space>
								<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('edocHisAdd')">
									<template #icon><plus-outlined /></template>
									新增
								</a-button>
								<xn-batch-delete
									v-if="hasPerm('edocHisBatchDelete')"
									:selectedRowKeys="selectedRowKeys"
									@batchDelete="deleteBatchEdocHis"
								/>
							</a-space>
						</template>-->
			<template #bodyCell="{ column, record }">
				<!--				<template v-if="column.dataIndex === 'categoryId'">
									{{ $TOOL.dictTypeData('DOC_STATUS', record.categoryId) }}
								</template>-->
				<template v-if="column.dataIndex === 'status'">
					{{ $TOOL.dictTypeData('DOC_STATUS', record.status) }}
				</template>
				<!--				<template v-if="column.dataIndex === 'action'">
									<a-space>
										<a @click="formRef.onOpen(record)" v-if="hasPerm('edocHisEdit')">编辑</a>
										<a-divider type="vertical" v-if="hasPerm(['edocHisEdit', 'edocHisDelete'], 'and')" />
										<a-popconfirm title="确定要删除吗？" @confirm="deleteEdocHis(record)">
											<a-button type="link" danger size="small" v-if="hasPerm('edocHisDelete')">删除</a-button>
										</a-popconfirm>
									</a-space>
								</template>-->
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)"/>
</template>

<script setup name="edocHis">
import tool from '@/utils/tool'
import Form from './form.vue'
import edocHisApi from '@/api/biz/edocHisApi'
import edocCategoryApi from "@/api/biz/edocCategoryApi";
import dayjs from "dayjs";
import edocSupplyApi from "@/api/biz/edocSupplyApi";

let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = {refresh: true, height: true, columnSetting: true, striped: false}
// 查询区域显示更多控制
const advanced = ref(false)
const toggleAdvanced = () => {
	advanced.value = !advanced.value
}
// 定义类目元素
const treeData = ref([])
const supplyList = ref([])
const statusOptions = tool.dictList('DOC_STATUS')
const columns = [
	{
		title: '档案名称',
		dataIndex: 'name'
	},
	{
		title: '档案编号',
		dataIndex: 'code'
	},
	{
		title: '类目',
		dataIndex: 'categoryName'
	},
	{
		title: '档案状态',
		dataIndex: 'status'
	},
	{
		title: '借出单位',
		dataIndex: 'borrowName'
	},
	{
		title: '备注',
		dataIndex: 'remarks',
		ellipsis: true
	},
	{
		title: '操作时间',
		dataIndex: 'createTime'
	},
	{
		title: '操作用户',
		dataIndex: 'creatName'
	},
]
// 操作栏通过权限判断是否显示
/*if (hasPerm(['edocHisEdit', 'edocHisDelete'])) {
	columns.push({
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '150px'
	})
}*/
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
	const params = {
		pageNo: 1,
		pageSize: -1
	}
	edocSupplyApi.edocSupplyPage(params).then((res) => {
		supplyList.value = res.records
	})
	edocCategoryApi.edocCategoryTreeSelector().then((res) => {
		treeData.value = res
	})
	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	// createTime范围查询条件重载
	if (searchFormParam.createTime) {
		searchFormParam.startCreateTime = dayjs(searchFormParam.createTime[0]).format('YYYY-MM-DD HH:mm:ss')
		searchFormParam.endCreateTime = dayjs(searchFormParam.createTime[1]).format('YYYY-MM-DD HH:mm:ss')
		delete searchFormParam.createTime
	}
	return edocHisApi.edocHisPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteEdocHis = (record) => {
	let params = [
		{
			id: record.id
		}
	]
	edocHisApi.edocHisDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchEdocHis = (params) => {
	edocHisApi.edocHisDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
</script>
