<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="联系人" name="contactUser">
						<a-input v-model:value="searchFormState.contactUser" placeholder="请输入联系人" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="联系电话" name="tel">
						<a-input v-model:value="searchFormState.tel" placeholder="请输入联系电话" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="创建时间" name="createTime">
						<a-range-picker v-model:value="searchFormState.createTime" show-time />
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
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('edocSupplyAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('edocSupplyBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchEdocSupply"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('edocSupplyEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['edocSupplyEdit', 'edocSupplyDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteEdocSupply(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('edocSupplyDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="edocSupply">
import Form from './form.vue'
import edocSupplyApi from '@/api/biz/edocSupplyApi'

let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	// 查询区域显示更多控制
	const advanced = ref(false)
	const toggleAdvanced = () => {
		advanced.value = !advanced.value
	}
	const columns = [
		{
			title: '名称',
			dataIndex: 'name',
			ellipsis: true
		},
		{
			title: '联系人',
			dataIndex: 'contactUser',
			ellipsis: true
		},
		{
			title: '联系电话',
			dataIndex: 'tel'
		},
		{
			title: '创建时间',
			dataIndex: 'createTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['edocSupplyEdit', 'edocSupplyDelete'])) {
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
		const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
		// createTime范围查询条件重载
		if (searchFormParam.createTime) {
			searchFormParam.startCreateTime = searchFormParam.createTime[0]
			searchFormParam.endCreateTime = searchFormParam.createTime[1]
			delete searchFormParam.createTime
		}
		return edocSupplyApi.edocSupplyPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteEdocSupply = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		edocSupplyApi.edocSupplyDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchEdocSupply = (params) => {
		edocSupplyApi.edocSupplyDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
