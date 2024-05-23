const API_BASE_URL = 'http://localhost:8080';

export default {
  async getProjetosEmAberto() {
    try {
      const response = await fetch(`${API_BASE_URL}/projetos/em_aberto`, {
        method: 'GET',
        headers: {
            'Accept': '*/*',
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': 'http://localhost:5173'
        }
      });
      if (!response.ok) {
        throw new Error('Failed to fetch projects');
      }
      return await response.json();
    } catch (error) {
      console.error('Error fetching projects:', error);
      throw error;
    }
  }
};
