import tool from '@/utils/tool'
import fileApi from '@/api/biz/fileApi'

// 将以;分割的id字符串转化成FileList(异步改成同步)
tool.idStrToFileList = async (idStr) => {
	let result = []
	if (idStr) {
		await fileApi.fileList({ idStr: idStr }).then((res) => {
			if (res && res.length > 0) {
				res.forEach((item) => {
					result.push({
						uid: item.id,
						name: item.name,
						url: item.downloadPath,
						status: 'done',
						response: {
							data: item.id
						}
					})
				})
			}
		})
	}
	return result
}
// 将FileList转化成以;分割的id字符串
tool.fileListToIdStr = (fileList) => {
	let result = ''
	if (fileList && fileList.length > 0) {
		fileList.forEach((item, index) => {
			result += item.response.data
			if (fileList.length !== index + 1) {
				result += ';'
			}
		})
	}
	return result
}
// 创建转换函数
tool.convertToGB = (capacity) => {
	// 提取字符串中的数字部分
	let number = Number(capacity.replace(/[^0-9.]/g, ''));

	// 判断提取到的数字的单位，并进行相应的转换
	const unit = capacity.match(/[TMGK]/g);
	if (unit && unit[0] === 'T') {
		return number * 1024; // 1 TB = 1024^2 GB
	} else if (unit && unit[0] === 'G') {
		return number; // GB单位无需转换
	} else if (unit && unit[0] === 'M') {
		return number / 1024; // 1 MB = 1/1024 GB
	} else if (unit && unit[0] === 'K') {
		return number / 1024 / 1024; // 1 KB = 1/1024 MB
	} else {
		return null; // 如果无法识别单位，返回null或抛出错误
	}
}

export default tool
