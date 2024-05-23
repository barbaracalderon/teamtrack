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
  padding: 40px;
  min-height: 100%;
  min-width: 100%;
  display: flex;
  flex-direction: column;
}
html, body {
    height: 100%;
    margin: 0;
}
.container {
  width: 100%;
  max-width: 800px;
  padding: 50px;
  background: rgb(232, 236, 241);
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
