<template>
	<div ref="chart" :style="{width: '100%', height: '100%'}"></div>
</template>

<script>
import {onMounted, ref, watch} from 'vue'
import * as echarts from 'echarts'

export default {
	name: 'EChartsComponent',
	props: {
		option: {
			type: Object,
			required: true,
		},
	},
	setup(props) {
		const chart = ref(null)
		let chartInstance = null

		onMounted(() => {
			chartInstance = echarts.init(chart.value)
			chartInstance.setOption(props.option)
		})

		watch(() => props.option, (newOption) => {
			chartInstance.setOption(newOption)
		})

		return { chart }
	},
}
</script>
