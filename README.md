# Portfólio Docente - Professora Conceição (Geografia)
**Escola:** Centro Educa Mais Dom Ungarelli (Pinheiro - MA)

## Estrutura de Pastas
- `index.html` - Estrutura principal da página em HTML5.
- `css/style.css` - Estilização responsiva em CSS3.
- `js/app.js` - Integração do formulário com o back-end Java.
- `img/` - Imagens do portfólio (perfil, banner da escola e projetos).
- `java/ServidorPortfolio.java` - Servidor web e API de contato desenvolvidos em Java puro (JDK 11+).

## Como abrir e executar no VS Code
1. Extraia este arquivo `.zip` e abra a pasta no **Visual Studio Code**.
2. Abra o terminal integrado no VS Code (`Ctrl + '`).
3. Compile o código Java:
   ```bash
   javac java/ServidorPortfolio.java
   ```
4. Execute o servidor:
   ```bash
   java -cp java ServidorPortfolio
   ```
5. Abra o navegador e acesse: `http://localhost:8080`
