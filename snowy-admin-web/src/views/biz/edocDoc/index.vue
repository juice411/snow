<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入档案名称"/>
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="编号" name="code">
						<a-input v-model:value="searchFormState.code" placeholder="请输入档案编号"/>
					</a-form-item>
				</a-col>
				<a-col :span="6">
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
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="保密级别" name="secretLevel">
						<a-select v-model:value="searchFormState.secretLevel" placeholder="请选择保密级别"
								  :options="secretLevelOptions"/>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="标签" name="tag">
						<a-input v-model:value="searchFormState.tag" placeholder="请输入标签"/>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="来源" name="supplyId">
						<a-select v-model:value="searchFormState.supplyId" placeholder="请选择档案来源"
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
					<a-form-item label="档案架" name="spotId">
						<a-select v-model:value="searchFormState.spotId" placeholder="请选择档案架"
								  :filter-option="
                        (input, option) => {
                          if (option && option.name) {
                            return option.name.indexOf(input) >= 0
                          }
                        }
                      "
								  :allowClear="true"
								  :fieldNames="{ label: 'name', value: 'id', options: 'options' }"
								  :options="spotList"/>
					</a-form-item>
				</a-col>
				<!--				<a-col :span="6" v-show="advanced">
									<a-form-item label="部门id,默认取CREATE_USER所在部门" name="orgId">
										<a-select v-model:value="searchFormState.orgId" placeholder="请选择部门id,默认取CREATE_USER所在部门" :options="orgIdOptions" />
									</a-form-item>
								</a-col>-->
				<a-col :span="6" v-show="advanced">
					<a-form-item label="状态" name="status">
						<a-select v-model:value="searchFormState.status" placeholder="请选择档案状态"
								  :options="statusOptions"/>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="保存周期" name="keepPeriod">
						<a-input v-model:value="searchFormState.keepPeriod" placeholder="请输入保存周期，单位：年"/>
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="销毁时间" name="destroyTime">
						<a-range-picker v-model:value="searchFormState.destroyTime" show-time/>
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
          <a-form-item label="借出周期" name="borrowPeriod">
            <a-input v-model:value="searchFormState.borrowPeriod" placeholder="请输入借出周期，单位：天"/>
          </a-form-item>
        </a-col>
        <a-col :span="6" v-show="advanced">
          <a-form-item label="借出时间" name="borrowTime">
            <a-range-picker v-model:value="searchFormState.borrowTime" show-time/>
          </a-form-item>
        </a-col>
        <a-col :span="6" v-show="advanced">
          <a-form-item label="归还时间" name="returnTime">
            <a-range-picker v-model:value="searchFormState.returnTime" show-time/>
          </a-form-item>
        </a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="归档时间" name="createTime">
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
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('edocDocAdd')">
						<template #icon>
							<plus-outlined/>
						</template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('edocDocBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchEdocDoc"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
<!--				<template v-if="column.dataIndex === 'categoryId'">
					{{ $TOOL.dictTypeData('ORG_CATEGORY', record.categoryId) }}
				</template>-->
				<template v-if="column.dataIndex === 'secretLevel'">
          <a-tag color="green" v-if="record.secretLevel==='SECRET_LEVEL_ONE'">{{ $TOOL.dictTypeData('SECRET_LEVEL', record.secretLevel) }}</a-tag>
          <a-tag color="yellow" v-if="record.secretLevel==='SECRET_LEVEL_TWO'">{{ $TOOL.dictTypeData('SECRET_LEVEL', record.secretLevel) }}</a-tag>
          <a-tag color="red" v-if="record.secretLevel==='SECRET_LEVEL_THREE'">{{ $TOOL.dictTypeData('SECRET_LEVEL', record.secretLevel) }}</a-tag>
				</template>
<!--				<template v-if="column.dataIndex === 'supplyId'">
					{{ $TOOL.dictTypeData('POSITION_CATEGORY', record.supplyId) }}
				</template>
				<template v-if="column.dataIndex === 'spotId'">
					{{ $TOOL.dictTypeData('POSITION_CATEGORY', record.spotId) }}
				</template>-->
<!--				<template v-if="column.dataIndex === 'orgId'">
					{{ $TOOL.dictTypeData('ORG_CATEGORY', record.orgId) }}
				</template>-->
				<template v-if="column.dataIndex === 'status'">
          <a-tag color="green" v-if="record.status==='DOC_STATUS_GATHER'">{{ $TOOL.dictTypeData('DOC_STATUS', record.status) }}</a-tag>
          <a-tag color="yellow" v-if="record.status==='DOC_STATUS_BORROW'">{{ $TOOL.dictTypeData('DOC_STATUS', record.status) }}</a-tag>
          <a-tag color="blue" v-if="record.status==='DOC_STATUS_RETURN'">{{ $TOOL.dictTypeData('DOC_STATUS', record.status) }}</a-tag>
          <a-tag color="red" v-if="record.status==='DOC_STATUS_DESTROY'">{{ $TOOL.dictTypeData('DOC_STATUS', record.status) }}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('edocDocEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['edocDocEdit', 'edocDocDelete'], 'and')"/>
						<a-popconfirm title="确定要删除吗？" @confirm="deleteEdocDoc(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('edocDocDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)"/>
</template>

<script setup name="edocDoc">
import tool from '@/utils/myTool'
import Form from './form.vue'
import edocDocApi from '@/api/biz/edocDocApi'
import edocSpotApi from "@/api/biz/edocSpotApi";
import edocSupplyApi from "@/api/biz/edocSupplyApi";
import edocCategoryApi from "@/api/biz/edocCategoryApi";
import dayjs from "dayjs";

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
const secretLevelOptions = tool.dictList('SECRET_LEVEL')
const statusOptions = tool.dictList('DOC_STATUS')
// 定义类目元素
const treeData = ref([])
const supplyList = ref([])
const spotList = ref([])

const columns = [
	{
		title: '名称',
		dataIndex: 'name',
		ellipsis: true
	},
	{
		title: '编号',
		dataIndex: 'code',
		ellipsis: true
	},
	{
		title: '类目',
		dataIndex: 'categoryName',
		ellipsis: true
	},
	{
		title: '保密级别',
		dataIndex: 'secretLevel'
	},
	{
		title: '标签',
		dataIndex: 'tag',
		ellipsis: true
	},
	{
		title: '来源',
		dataIndex: 'supplyName',
		ellipsis: true
	},
	{
		title: '档案架',
		dataIndex: 'spotName',
		ellipsis: true
	},
	{
		title: '状态',
		dataIndex: 'status'
	},
	{
		title: '保存周期(年)',
		dataIndex: 'keepPeriod'
	},
	/*{
		title: '所属组织',
		dataIndex: 'orgName',
		ellipsis: true
	},*/
  {
    title: '借出单位',
    dataIndex: 'borrowName'
  },
  {
    title: '借出周期(天)',
    dataIndex: 'borrowPeriod'
  },
  {
    title: '归还时间',
    dataIndex: 'returnTime'
  },
  {
    title: '借出时间',
    dataIndex: 'borrowTime'
  },
	{
		title: '归档人',
		dataIndex: 'creatName'
	},
	{
		title: '销毁时间',
		dataIndex: 'destroyTime'
	},
	{
		title: '归档时间',
		dataIndex: 'createTime'
	},
]
// 操作栏通过权限判断是否显示
if (hasPerm(['edocDocEdit', 'edocDocDelete'])) {
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
	const params = {
		pageNo: 1,
		pageSize: -1
	}
	edocSpotApi.edocSpotPage(params).then((res) => {
		spotList.value = res.records
	})
	edocSupplyApi.edocSupplyPage(params).then((res) => {
		supplyList.value = res.records
	})
	edocCategoryApi.edocCategoryTreeSelector().then((res) => {
		treeData.value = res
	})

	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	//dayjs解决utc标准时间到本地时间格式的转换
	// destroyTime范围查询条件重载
	if (searchFormParam.destroyTime) {
		searchFormParam.startDestroyTime = dayjs(searchFormParam.destroyTime[0]).format('YYYY-MM-DD HH:mm:ss')
		searchFormParam.endDestroyTime = dayjs(searchFormParam.destroyTime[1]).format('YYYY-MM-DD HH:mm:ss')
		delete searchFormParam.destroyTime
	}
	// createTime范围查询条件重载
	if (searchFormParam.createTime) {
		searchFormParam.startCreateTime = dayjs(searchFormParam.createTime[0]).format('YYYY-MM-DD HH:mm:ss')
		searchFormParam.endCreateTime = dayjs(searchFormParam.createTime[1]).format('YYYY-MM-DD HH:mm:ss')
		delete searchFormParam.createTime
	}

  // borrowTime范围查询条件重载
  if (searchFormParam.borrowTime) {
    searchFormParam.startBorrowTime = dayjs(searchFormParam.borrowTime[0]).format('YYYY-MM-DD HH:mm:ss')
    searchFormParam.endBorrowTime = dayjs(searchFormParam.borrowTime[1]).format('YYYY-MM-DD HH:mm:ss')
    delete searchFormParam.borrowTime
  }

  // returnTime范围查询条件重载
  if (searchFormParam.returnTime) {
    searchFormParam.startReturnTime = dayjs(searchFormParam.returnTime[0]).format('YYYY-MM-DD HH:mm:ss')
    searchFormParam.endReturnTime = dayjs(searchFormParam.returnTime[1]).format('YYYY-MM-DD HH:mm:ss')
    delete searchFormParam.returnTime
  }

	return edocDocApi.edocDocPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})

}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteEdocDoc = (record) => {
	let params = [
		{
			id: record.id
		}
	]
	edocDocApi.edocDocDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchEdocDoc = (params) => {
	edocDocApi.edocDocDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}

</script>
