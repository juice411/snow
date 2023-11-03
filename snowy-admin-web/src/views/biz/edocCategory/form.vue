<template>
	<xn-form-container
		:title="formData.id ? '编辑档案类目' : '增加档案类目'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
<!--			<a-form-item label="父id：" name="parentId">
				<a-input v-model:value="formData.parentId" placeholder="请输入父id" allow-clear />
			</a-form-item>-->
			<a-form-item label="上级类目：" name="parentId">
				<a-tree-select
					v-model:value="formData.parentId"
					style="width: 100%"
					:dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
					placeholder="请选择上级类目"
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
			<a-form-item label="名称：" name="name">
				<a-input v-model:value="formData.name" placeholder="请输入名称" allow-clear />
			</a-form-item>
			<a-form-item label="编码：" name="code">
				<a-input v-model:value="formData.code" placeholder="请输入编码" allow-clear />
			</a-form-item>
			<a-form-item label="排序码：" name="sortCode">
				<a-input v-model:value="formData.sortCode" placeholder="请输入排序码" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="edocCategoryForm">
import {cloneDeep} from 'lodash-es'
import {required} from '@/utils/formRules'
import edocCategoryApi from '@/api/biz/edocCategoryApi'
// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	// 定义类目元素
	const treeData = ref([])

	// 打开抽屉
	const onOpen = (record) => {
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}

		// 获取类目树并加入顶级
		edocCategoryApi.edocCategoryTreeSelector().then((res) => {
			treeData.value = [
				{
					id: 0,
					parentId: '-1',
					name: '顶级',
					children: res
				}
			]
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
		parentId: [required('请选择父类目')],
		name: [required('请输入名称')],

	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			edocCategoryApi
				.edocCategorySubmitForm(formDataParam, formDataParam.id)
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
