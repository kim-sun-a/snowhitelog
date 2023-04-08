<script setup lang="ts">
import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true
  },
});

const post = ref({
  id: 0,
  title : "",
  content: ""
});

const router = useRouter();

const moveToEdit = () => {
  router.push({name: "edit", params:{postId: props.postId}})
}

onMounted(() => {
  axios.get(`/api/posts/${props.postId}`).then(response => {
        post.value = response.data;
      })
})
</script>
<template>
  <el-row>
    <el-col>
      <h2 class="title">{{ post.title }}</h2>
      <div class="d-flex sub">
        <div class="category">개발</div>
        <div class="regDate">2023-4-8 12:33:23</div>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">
        {{ post.content }}
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.content {
  font-size: 0.95rem;
  margin-top: 4px;
  color: #5a5a5a;
  white-space: break-spaces;
  line-height: 1.5;
}

.sub {
  margin-top: 6px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: rgba(107, 107, 107, 0.76);
  }
}

</style>