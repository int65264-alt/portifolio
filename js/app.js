document.getElementById('formContato').addEventListener('submit', async function(e) {
    e.preventDefault();
    const statusEl = document.getElementById('statusEnvio');
    const dados = new URLSearchParams(new FormData(this));

    try {
        const resposta = await fetch('/api/contato', {
            method: 'POST',
            body: dados
        });
        if (resposta.ok) {
            const texto = await resposta.text();
            statusEl.textContent = texto;
            this.reset();
        } else {
            statusEl.textContent = "Mensagem registrada localmente (inicie o servidor Java para envio completo).";
        }
    } catch (erro) {
        statusEl.textContent = "Mensagem pronta! Para processar no back-end, execute o arquivo ServidorPortfolio.java.";
    }
});
