<template>
	<a-spin :spinning="spinning">
		<a-card title="档案统计信息" :bordered="false" class="mb-2">
			<a-descriptions size="middle" :column="3" bordered>
				<a-descriptions-item label="档案架">{{ edocAgg.edocSpot }}个</a-descriptions-item>
				<a-descriptions-item label="档案类目">{{ edocAgg.edocCategory }}个</a-descriptions-item>
				<a-descriptions-item label="档案总数">{{ edocAgg.edocDoc }}件</a-descriptions-item>
				<a-descriptions-item label="档案附件总数">{{ edocAgg.edocFile }}个</a-descriptions-item>
				<a-descriptions-item label="注册关联机构总数">{{ edocAgg.edocSupply }}个</a-descriptions-item>
			</a-descriptions>
		</a-card>
		<a-row :gutter="[10, 10]" class="mb-2">
			<a-col :span="6">
				<a-card title="档案类目" :bordered="false">
<!--					<MyEChart style="height: 400px" :echart_id="categoryChart" :option="categoryChartOption"/>-->

					<MyEChart style="height: 300px" :option="categoryChartOption"></MyEChart>
				</a-card>
			</a-col>
			<a-col :span="6">
				<a-card title="档案状态" :bordered="false">
<!--					<MyEChart style="height: 400px"  :option="statusChartOption"/>-->
					<MyEChart style="height: 300px" :option="statusChartOption"></MyEChart>
				</a-card>
			</a-col>
			<a-col :span="6">
				<a-card title="附件类型" :bordered="false">
<!--					<MyEChart style="height: 400px" :option="fileTypeChartOption" />-->
					<MyEChart style="height: 300px" :option="fileTypeChartOption"></MyEChart>
				</a-card>
			</a-col>
			<a-col :span="6">
				<a-card title="存储占用" :bordered="false" class="monitor-center-row-col-card">
					<MyEChart style="height: 300px" :option="storeChartOption"></MyEChart>
<!--					<a-tooltip>
						<template #title>
							<div>存储总量：{{ devMonitorStorageInfo.storageTotal }}</div>
							<div>存储已用：{{ devMonitorStorageInfo.storageUsed }}</div>
							<div>存储剩余：{{ devMonitorStorageInfo.storageFree }}</div>
							<div>存储使用率：{{ devMonitorStorageInfo.storageUseRate }}%</div>
						</template>
						<a-progress
							type="dashboard"
							style="height: 300px"
							:stroke-color="getProgressColor(devMonitorStorageInfo.storageUseRate)"
							:percent="devMonitorStorageInfo.storageUseRate"
						/>
					</a-tooltip>-->
<!--					<div>存储使用率</div>-->
				</a-card>
			</a-col>

		</a-row>
	</a-spin>
</template>

<script setup name="devMonitor">
import tool from '@/utils/myTool'
import {onMounted} from 'vue'
import monitorApi from '@/api/biz/monitorServerInfoApi'
import MyEChart from "@/components/Chart/eCBingZhuangTu/MyEChart.vue";
import edocMonitorApi from "@/api/biz/edocMonitorApi";
import edocSpotApi from "../../api/biz/edocSpotApi";
import edocCategoryApi from "../../api/biz/edocCategoryApi";
import edocDocApi from "../../api/biz/edocDocApi";
import fileApi from "../../api/biz/fileApi";
import edocSupplyApi from "@/api/biz/edocSupplyApi";

const spinning = ref(false)
	// edocAgg信息
	const edocAgg = ref({})
	// 储存信息
	const devMonitorStorageInfo = ref({})

	const categoryChartOption=ref({})

	const statusChartOption = ref({})
	const fileTypeChartOption=ref({})

	const storeChartOption = ref({})


	onMounted(() => {
		getMonitorServerInfo()
		getCategoryChartOption()
		getStatusChartOption()
		getFileTypeChartOption()
		getEdocAgg()
	})

	const getEdocAgg=()=>{
		edocSpotApi.edocSpotPage().then((data)=>{edocAgg.value.edocSpot=data.total
			//console.log(edocAgg.value.edocSpot)
		})

		edocCategoryApi.edocCategoryPage().then((data)=>{edocAgg.value.edocCategory=data.total
			//console.log(edocAgg.value.edocCategory)
		})

		edocDocApi.edocDocPage().then((data)=>{edocAgg.value.edocDoc=data.total
			//console.log(edocAgg.value.edocDoc)
		})

		fileApi.filePage().then((data)=>{edocAgg.value.edocFile=data.total
			//console.log(edocAgg.value.edocFile)
		})

		edocSupplyApi.edocSupplyPage().then((data)=>{edocAgg.value.edocSupply=data.total
			//console.log(edocAgg.value.edocFile)
		})

	}

	const getMonitorServerInfo = () => {
		spinning.value = true
		monitorApi
			.monitorServerInfo()
			.then((data) => {
				/*devMonitorCpuInfo.value = data.devMonitorCpuInfo
				devMonitorMemoryInfo.value = data.devMonitorMemoryInfo*/
				devMonitorStorageInfo.value = data.devMonitorStorageInfo
				/*devMonitorServerInfo.value = data.devMonitorServerInfo
				devMonitorJvmInfo.value = data.devMonitorJvmInfo*/

				getStoreOption(devMonitorStorageInfo.value)

			})
			.finally(() => {
				spinning.value = false
			})
	}

	const getCategoryChartOption = () => {
		edocMonitorApi
			.edocDocCategoryAgg()
			.then((data) => {
				let option={
					title: {
						text: '',
						subtext: '',
						left: 'center'
					},
					tooltip: {
						trigger: 'item'
					},
					legend: {
						orient: 'vertical',
						left: 'right'
					},
					series: [
						{
							name: '档案类目',
							type: 'pie',
							radius: '45%',
							data: data.records,
							emphasis: {
								itemStyle: {
									shadowBlur: 10,
									shadowOffsetX: 0,
									shadowColor: 'rgba(0, 0, 0, 0.5)'
								}
							}
						}
					]
				}
				categoryChartOption.value=option

			})

	}
	const getStatusChartOption = () => {
		edocMonitorApi
			.edocDocStatusAgg()
			.then((data) => {
				let result = []
				data.records.forEach((item) => {
					result.push({
						value: item.value,
						name: item.statusName
					})
				})
				let option={
					tooltip: {
						trigger: 'item'
					},
					legend: {
						top: '5%',
						left: 'center'
					},
					series: [
						{
							name: '档案状态',
							type: 'pie',
							radius: ['25%', '50%'],
							avoidLabelOverlap: false,
							itemStyle: {
								borderRadius: 10,
								borderColor: '#fff',
								borderWidth: 2
							},
							label: {
								show: false,
								position: 'center'
							},
							emphasis: {
								label: {
									show: true,
									fontSize: '40',
									fontWeight: 'bold'
								}
							},
							labelLine: {
								show: false
							},
							data:result
						}
					]
				}
				statusChartOption.value=option

			})

	}
	const getFileTypeChartOption = () => {
		edocMonitorApi
			.edocDocFileTypeAgg()
			.then((data) => {
				let result = []
				data.records.forEach((item) => {
					result.push({
						value: item.value,
						name: item.suffix
					})
				})

				let option={
					legend: {
						top: 'bottom'
					},
					toolbox: {
						show: true,
						feature: {
							mark: { show: true },
							dataView: { show: true, readOnly: false },
							restore: { show: false },
							saveAsImage: { show: false }
						}
					},
					series: [
						{
							name: 'Nightingale Chart',
							type: 'pie',
							radius: [10, 60],
							center: ['50%', '50%'],
							roseType: 'area',
							itemStyle: {
								borderRadius: 8
							},
							data: result
						}
					]
				}
				fileTypeChartOption.value=option

			})

	}
	const getStoreOption = (param) => {

		let option={
			title: {
				text: '',
				subtext: '',
				left: 'center'
			},
			tooltip: {
				trigger: 'item',
				formatter: '{a} <br/>{b}: {c}GB ({d}%)'
			},
			legend: {
				orient: 'vertical',
				left: 'left',
				data: ['已使用', '未使用']
			},
			series: [
				{
					name: '存储空间占用',
					type: 'pie',
					radius: ['25%', '35%'],
					avoidLabelOverlap: false,
					label: {
						show: false,
						position: 'center'
					},
					emphasis: {
						label: {
							show: true,
							fontSize: '30',
							fontWeight: 'bold'
						}
					},
					labelLine: {
						show: false
					},
					data: [
						{value: tool.convertToGB(param.storageUsed), name: '已使用'},
						{value: tool.convertToGB(param.storageFree), name: '未使用'}
					]
				}
			]
		}

		storeChartOption.value=option
	}

	// 获取颜色 30以下绿色，30-80蓝色， 80往上红色
	const getProgressColor = (value) => {
		const values = Number(value)
		if (values <= 30) {
			return '#49aa19'
		} else if ((values > 30) & (values <= 80)) {
			return '#1890fe'
		} else if (values > 80) {
			return '#e60000'
		}
	}
</script>

<style scoped>
	.monitor-center-row-col-card {
		text-align: center;
		height: 100%;
	}
	:deep(.ant-card-extra) {
		padding: 8px 0 !important;
	}
</style>
