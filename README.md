Tarefas
Este projeto é uma aplicação Java simples para gerenciar tarefas, permitindo ao usuário adicionar, remover, listar e salvar tarefas em um arquivo de texto. A aplicação utiliza uma estrutura básica de console para interação com o usuário e gravação em arquivos.

Funcionalidades
Adicionar Tarefa: Permite adicionar uma nova tarefa à lista de tarefas.
Remover Tarefa: Remove uma tarefa existente com base no índice informado.
Listar Tarefas: Exibe todas as tarefas armazenadas.
Salvar Tarefas: Salva as tarefas em um arquivo de texto (tarefas.txt) para persistência de dados.
Carregar Tarefas: Carrega as tarefas do arquivo tarefas.txt para a aplicação.
Estrutura do Projeto
O projeto possui uma classe principal chamada Arquivo.java, responsável pela leitura e gravação de tarefas em um arquivo de texto, além de interagir com o usuário através de um menu no console.

Classe Arquivo.java
A classe Arquivo.java possui as seguintes funções principais:

carregarTarefas: Carrega as tarefas de um arquivo de texto (tarefas.txt) e as armazena em uma lista.
salvarTarefas: Salva a lista de tarefas no arquivo de texto (tarefas.txt).
adicionarTarefa: Adiciona uma nova tarefa à lista de tarefas.
removerTarefa: Remove uma tarefa existente da lista com base no índice informado.
listarTarefas: Lista todas as tarefas atualmente armazenadas.
Fluxo da Aplicação
O usuário é apresentado com um menu simples que oferece opções para adicionar, remover, listar ou sair da aplicação.
As tarefas são carregadas automaticamente do arquivo de texto assim que a aplicação inicia.
Após as operações de adicionar ou remover, as tarefas são automaticamente salvas no arquivo para garantir persistência.
Ao sair da aplicação, todas as tarefas atuais são salvas no arquivo tarefas.txt.
Requisitos
Java 8 ou superior para executar o código.
Sistema de arquivos acessível para ler e gravar o arquivo tarefas.txt.
Como Executar
Clone este repositório:

bash
Copiar código
git clone https://github.com/henriquepadua/Tarefas.git
Compile o projeto:

bash
Copiar código
javac Arquivo.java
Execute o projeto:

bash
Copiar código
java Arquivo
Siga as instruções no terminal para adicionar, remover ou listar tarefas.

Exemplo de Uso
shell
Copiar código
Selecione uma opção:
1. Adicionar tarefa
2. Remover tarefa
3. Listar tarefas
4. Sair
Adicionar Tarefa
Selecione a opção 1 e insira a descrição da tarefa. A tarefa será adicionada à lista e salva no arquivo tarefas.txt.

Remover Tarefa
Selecione a opção 2, informe o índice da tarefa que deseja remover, e ela será excluída da lista e do arquivo.

Listar Tarefas
Selecione a opção 3 para visualizar todas as tarefas armazenadas.

Estrutura do Arquivo tarefas.txt
O arquivo tarefas.txt armazena cada tarefa em uma linha separada. A aplicação lê e grava este arquivo para manter a persistência das tarefas inseridas pelo usuário.

Contribuições
Contribuições são bem-vindas! Se você encontrar algum bug ou quiser sugerir melhorias, sinta-se à vontade para abrir uma issue ou enviar um pull request.

Licença
Este projeto está licenciado sob os termos da licença MIT. Consulte o arquivo LICENSE para mais informações.

Esse README abrange as funcionalidades principais e a estrutura do projeto. Se houver mais funcionalidades ou detalhes no código, posso ajustar conforme necessário!

//////////////////////////////////////////////////////////////////////////////////////////
Perguntas e respostas:
1- O trabalho possui um índice direto implementado com a tabela hash extensível?
	NAO
2- A operação de inclusão insere um novo registro no fim do arquivo e no índice e retorna o ID desse registro?
	SIM
3- A operação de busca retorna os dados do registro, após localizá-lo por meio do índice direto?
	NAO
4- A operação de alteração altera os dados do registro e trata corretamente as reduções e aumentos no espaço do registro?
	NAO
5- A operação de exclusão marca o registro como excluído e o remove do índice direto?
	SIM
6- O trabalho está funcionando corretamente?
	SIM
7- O trabalho está completo?
	NAO
8- O trabalho é original e não a cópia de um trabalho de outro grupo?
SIM
