<template>
    <div class="projects">
      <h1>PROJETOS | Status <span class="highlight">"EM_ABERTO"</span></h1>
      <div v-if="isLoading">Loading...</div>
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
          console.log(this.projetos)
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
  

  h1 {
    margin-bottom: 50px;
  }
  .highlight {
    color: #1f7c1f;
    font-weight: 900;
  }

  .project-list {
  text-align: center;
  }

  .loading {
  font-size: 1.2em;
  }
</style>
  