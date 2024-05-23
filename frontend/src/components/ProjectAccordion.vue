<template>
  <div>
    <div v-for="(projeto, index) in projetos" :key="index" class="accordion">
      <div class="accordion-item">
        <h2 class="accordion-header">
          <button class="accordion-button" type="button" data-bs-toggle="collapse" :data-bs-target="'#collapse' + index">
            <div class="project-info">
              <span class="project-name">{{ projeto.nomeProjeto }}</span>
              <span class="project-cliente">({{ projeto.nomeCliente }})</span>
            </div>
          </button>
        </h2>
        <div :id="'collapse' + index" class="accordion-collapse collapse">
          <div class="accordion-body">
            <ol>
              <li v-for="(atividade, index) in projeto.atividades" :key="index">{{ atividade.descricaoAtividade }}</li>
            </ol>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    projetos: {
      type: Array,
      required: true
    }
  }
};
</script>

<style scoped>
.accordion-button {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
}
.project-info {
  display: flex;
  flex-direction: column;
}
.project-name {
  font-size: 18px;
  font-weight: bold;
}
.project-cliente {
  font-size: 14px;
  color: #888888;
}
.accordion-body {
  padding: 1rem 1.5rem;
}
</style>
