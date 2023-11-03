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
					<a-form-item label="位置编号" name="code">
						<a-input v-model:value="searchFormState.code" placeholder="请输入位置编号" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('edocSpotAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('edocSpotBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchEdocSpot"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('edocSpotEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['edocSpotEdit', 'edocSpotDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteEdocSpot(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('edocSpotDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="edocSpot">
import Form from './form.vue'
import edocSpotApi from '@/api/biz/edocSpotApi'

let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '名称',
			dataIndex: 'name',
			ellipsis: true
		},
		{
			title: '位置编号',
			dataIndex: 'code',
			ellipsis: true
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['edocSpotEdit', 'edocSpotDelete'])) {
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
		return edocSpotApi.edocSpotPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteEdocSpot = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		edocSpotApi.edocSpotDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchEdocSpot = (params) => {
		edocSpotApi.edocSpotDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
