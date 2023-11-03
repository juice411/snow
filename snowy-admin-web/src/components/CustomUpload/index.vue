<template>
  <a-upload-dragger
      v-bind="$attrs"
      :multiple="true"
	  :showUploadList="uploadList"
      :custom-request="customRequest"
      @preview="preview"
      @remove="del"
  >
    <p class="ant-upload-drag-icon">
      <inbox-outlined></inbox-outlined>
    </p>
    <p class="ant-upload-text">单击或拖动文件到此区域上传</p>
    <p class="ant-upload-hint"></p>
<!--    <template #itemRender="{ file, actions }">
      <a-space>
        &lt;!&ndash;				<span :style="file.status === 'error' ? 'color: red' : ''">{{ file.name }}</span>&ndash;&gt;
        <a href="javascript:;" @click="actions.preview">{{ file.name }}</a>
        <a href="javascript:;" @click="actions.download">download</a>
        <a href="javascript:;" @click="actions.remove">delete</a>
      </a-space>
    </template>-->
  </a-upload-dragger>
</template>

<script setup name="uploadIndex">
import fileApi from '@/api/biz/fileApi'
import {getCurrentInstance} from 'vue'
import {Base64} from 'js-base64'
import tool from '@/utils/myTool'

const { proxy } = getCurrentInstance()

//const fileList = ref([])
const emit = defineEmits({ uploadDone: null })

/*const props = defineProps({
  isPreview: {
    type: Boolean,
    default: false,
    required: false
  }
})*/

const uploadList = ref({
	showPreviewIcon: true,
	showRemoveIcon: true,
	showDownloadIcon: true })

const customRequest = async ({
                               action,
                               data,
                               file,
                               filename,
                               headers,
                               onError,
                               onProgress,
                               onSuccess,
                               withCredentials
                             }) => {
  const formData = new FormData()
  formData.append('file', file)

  const fileId = await fileApi.fileUploadDynamicReturnId(formData)
  const res = await fileApi.fileDetail({ id: fileId })

  emit('uploadDone', fileId)
  onSuccess({ data: fileId }, file)
}
const preview = (file) => {
  //let url=file.url
  let url =
      'http://10.50.144.39:8012/onlinePreview?url=' +
      encodeURIComponent(
          Base64.encode(
              file.url +
              '&token=' +
              tool.data.get('TOKEN') +
              '&fullfilename=' +
              file.name
          )
      ) +
      '&watermarkTxt=' +
      encodeURIComponent('e档管家文件预览')
  window.open(url)

}
/*const download = (file) => {
  window.open(file.url)
}*/
const del = (file) => {
  fileApi.fileDelete([{ id: file.response.data }]).then((res) => {})
}
</script>
