<template>
  <div class="projects">
    <div class="header">
      <h1><span class="symbol">&lt;TEAMTRACK - PROJETOS/&gt;</span></h1>
      <p>Status: <span class="highlight">"EM_ABERTO"</span></p>
    </div>
    <div v-if="isLoading" class="loading">Loading...</div>
    <div v-else>
      <ProjectAccordion :projetos="projetos" />
    </div>
  </div>
</template>

<script>
import ProjectAccordion from './ProjectAccordion.vue';
import ApiService from '../services/ApiService.js';

export default {
  data() {
    return {
      projetos: [],
      isLoading: true
    };
  },
  mounted() {
    this.fetchProjetos();
  },
  methods: {
    async fetchProjetos() {
      try {
        this.isLoading = true;
        this.projetos = await ApiService.getProjetosEmAberto();
      } catch (error) {
        console.error('Error fetching projects:', error);
      } finally {
        this.isLoading = false;
      }
    }
  },
  components: {
    ProjectAccordion
  }
};
</script>

<style scoped>
.projects {
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.header h1 {
  font-size: 24px;
  font-weight: bold;
}
.symbol {
  font-size: 32px;
  font-weight: 900;
  color: #1f2457;
}
.header p {
  font-size: 18px;
  color: #a5a5ad;
}
.highlight {
  color: #1f7c1f;
  font-weight: bold;
}
.loading {
  font-size: 20px;
  color: #888;
  text-align: center;
  margin-top: 20px;
}
</style>
