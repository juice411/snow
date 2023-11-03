<template>
	<xn-form-container
		:title="formData.id ? '编辑档案' : '增加档案'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="名称：" name="name">
				<a-input v-model:value="formData.name" placeholder="请输入档案名称" allow-clear />
			</a-form-item>
			<a-form-item label="编号：" name="code">
				<a-input v-model:value="formData.code" placeholder="请输入档案编号" allow-clear />
			</a-form-item>
			<a-form-item label="类目：" name="categoryId">
				<a-tree-select
					v-model:value="formData.categoryId"
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
			<a-form-item label="保密级别：" name="secretLevel">
				<a-select v-model:value="formData.secretLevel" style="width: 100%" placeholder="请选择保密级别" :allowClear="true" :options="secretLevelOptions" />
			</a-form-item>
			<a-form-item label="档案架：" name="spotId">
				<a-select v-model:value="formData.spotId" show-search style="width: 100%" placeholder="请选择档案存放位置"
						  :filter-option="
								(input, option) => {
									if (option && option.name) {
										return option.name.indexOf(input) >= 0
									}
								}
							"
						  :allowClear="true"
						  :fieldNames="{ label: 'name', value: 'id', options: 'options' }"
						  :options="spotList" />
			</a-form-item>
			<a-form-item label="档案来源：" name="supplyId">
				<a-select v-model:value="formData.supplyId" show-search style="width: 100%" placeholder="请选择来源"
						  :filter-option="
								(input, option) => {
									if (option && option.name) {
										return option.name.indexOf(input) >= 0
									}
								}
							"
						  :allowClear="true"
						  :fieldNames="{ label: 'name', value: 'id', options: 'options' }"
						  :options="supplyList" />
			</a-form-item>
			<a-form-item label="请输入保存周期（年）：" name="keepPeriod">
				<a-input v-model:value="formData.keepPeriod" placeholder="请输入保存周期，单位：年" allow-clear />
			</a-form-item>
			<a-form-item label="档案状态：" name="status">
				<a-select v-model:value="formData.status" placeholder="请选择档案状态" style="width: 100%" :allowClear="true" :options="statusOptions" />
			</a-form-item>
      <a-form-item label="借出单位：" name="borrowId">
        <a-select v-model:value="formData.borrowId" show-search style="width: 100%" placeholder="请选择借出单位"
                  :filter-option="
								(input, option) => {
									if (option && option.name) {
										return option.name.indexOf(input) >= 0
									}
								}
							"
                  :allowClear="true"
                  :fieldNames="{ label: 'name', value: 'id', options: 'options' }"
                  :options="supplyList" />
      </a-form-item>
      <a-form-item label="请输入借出周期（天）：" name="borrowPeriod">
        <a-input v-model:value="formData.borrowPeriod" placeholder="请输入借出周期，单位：天" allow-clear />
      </a-form-item>
			<a-form-item label="标签：" name="tag">
				<a-input v-model:value="formData.tag" placeholder="请输入标签" allow-clear />
			</a-form-item>
<!--			<a-form-item label="附件：" name="fileIds" :rules="[{ required: true, message: '请上传档案附件!' }]">
				<CustomUpload v-model:fileList="formData.fileIds" uploadMode="drag" />
			</a-form-item>-->
			<a-form-item label="附件：" name="fileIds">
				<CustomUpload v-model:fileList="formData.fileIds" uploadMode="drag" />
			</a-form-item>
			<a-form-item label="备注：" name="remarks">
				<a-textarea v-model:value="formData.remarks" :rows="4" />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="edocDocForm">
import tool from '@/utils/myTool'
import {cloneDeep} from 'lodash-es'
import {required, rules} from '@/utils/formRules'
import edocDocApi from '@/api/biz/edocDocApi'
import edocSupplyApi from "@/api/biz/edocSupplyApi"
import CustomUpload from '@/components/CustomUpload/index.vue'
import edocSpotApi from "@/api/biz/edocSpotApi";
import edocCategoryApi from "@/api/biz/edocCategoryApi";
// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	// 定义类目元素
	const treeData = ref([])
	const secretLevelOptions = ref([])
	const supplyList = ref([])
	const spotList = ref([])
	const statusOptions = ref([])

	// 打开抽屉
	const onOpen =async (record) => {
		visible.value = true
		if (record) {
      //下面的写法一直报错
			/*let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
			formData.fileIds= await tool.idStrToFileList(formData.fileIds)*/

      // 中间变量 // record为表格行数据，所以不能直接修改
      const midData = Object.assign({}, record)
      // 数据的二次处理
      midData.fileIds = await tool.idStrToFileList(midData.fileIds)

      formData.value = midData

		}

		secretLevelOptions.value = tool.dictList('SECRET_LEVEL')
		statusOptions.value = tool.dictList('DOC_STATUS')

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
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}

	// 默认要校验的
	const formRules = {
		name: [required('请输入名称')],
		categoryId: [required('请选择类目')],
		secretLevel: [required('请选择保密级别')],
		supplyId: [required('请选择来源')],
		spotId: [required('请选择档案架')],
		keepPeriod: [required('请输入保存期限'),rules.number],
		status: [required('请选择状态')],
		// fileIds:[{ required: true, message: '请上传档案附件!' }],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			/*const midData = Object.assign({}, formData.value)
			midData.fileIds = tool.fileListToIdStr(formData.value.fileIds)*/
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			formDataParam.fileIds=tool.fileListToIdStr(formDataParam.fileIds)
			edocDocApi
				.edocDocSubmitForm(formDataParam, formDataParam.id)
				.then(() => {
					onClose()
					emit('successful')
				})
				.finally(() => {
					submitLoading.value = false
				})
		})
	}
	// 抛出函数
	defineExpose({
		onOpen
	})
</script>
