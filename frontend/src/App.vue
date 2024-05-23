<template>
  <div id="app">
    <div class="container">
      <ProjectList />
    </div>
  </div>
</template>

<script>
import ProjectList from './components/ProjectList.vue';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

export default {
  components: {
    ProjectList
  }
};
</script>

<style>
#app {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vh;
}
.container {
  max-width: 800px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
}
</style>
