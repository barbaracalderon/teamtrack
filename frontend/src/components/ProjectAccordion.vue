<template>
    <div>
      <div v-for="(projeto, index) in projetos" :key="index" class="accordion">
        <div class="accordion-item">
          <h2 class="accordion-header">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" :data-bs-target="'#collapse' + index">
              <div class="highlight">{{ projeto.nomeProjeto }} ({{ projeto.nomeCliente }})</div>
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
  }

  .highlight {
  font-size: 26px;
  font-weight: bold;
  }
</style>
  