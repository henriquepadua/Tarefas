Projeto de Gerenciamento de Tarefas
Este projeto é um sistema de gerenciamento de tarefas que permite criar, atualizar, excluir e listar tarefas, categorias e rótulos. Ele utiliza estruturas de dados como Árvores B+ e Hash Extensível para gerenciar os dados de forma eficiente.

Estrutura do Projeto
aed3/
    Arquivo.class
    Arquivo.java
    ArvoreBMais.class
    ArvoreBMais.java
    ArvoreBMais$Pagina.class
    ElementoLista.class
    ElementoLista.java
    HashExtensivel.class
    HashExtensivel.java
    HashExtensivel$Cesto.class
    HashExtensivel$Diretorio.class
    ListaInvertida.class
    ListaInvertida.java
    ListaInvertida$Bloco.class
    ParIDEndereco.class
    ParIDEndereco.java
    Registro.class
    Registro.java
    RegistroArvoreBMais.class
Arquivos/
    ArquivoCategoria.java
    ArquivoRotulo.java
    ArquivoTarefa.java
Classes/
compilar
dados/
IO.class
IO.java
Menus/
    MenuRotulo.java
Pares/
    ParNomeIdRotulo.java
    ParNomeIdTarefa.java
Relacoes/
    TarefaRotulo.java
testes/
    readme.txt
///////////////////////////////////////////////////////////////////////////////////

Funcionalidades:

Tarefas:
- Criar Tarefa: Cria uma nova tarefa e a armazena no sistema.
- Atualizar Tarefa: Atualiza os dados de uma tarefa existente.
- Excluir Tarefa: Exclui uma tarefa do sistema.
- Listar Tarefas: Lista todas as tarefas armazenadas.

Categorias:
- Criar Categoria: Cria uma nova categoria e a armazena no sistema.
- Atualizar Categoria: Atualiza os dados de uma categoria existente.
- Excluir Categoria: Exclui uma categoria do sistema.
- Listar Categorias: Lista todas as categorias armazenadas.

Rótulos:
- Criar Rótulo: Cria um novo rótulo e o armazena no sistema.
- Atualizar Rótulo: Atualiza os dados de um rótulo existente.
- Excluir Rótulo: Exclui um rótulo do sistema.
- Listar Rótulos: Lista todos os rótulos armazenados.

///////////////////////////////////////////////////////////////////////
Classes Principais

aed3.Arquivo
- Classe genérica que gerencia operações de CRUD (Create, Read, Update, Delete) em arquivos.

aed3.ArvoreBMais
- Implementação de uma Árvore B+ para gerenciar índices de forma eficiente.

aed3.HashExtensivel
- Implementação de um Hash Extensível para gerenciar índices de forma eficiente.

Arquivos.ArquivoTarefa
Gerencia operações de CRUD para tarefas.

Arquivos.ArquivoCategoria
- Gerencia operações de CRUD para categorias.

Arquivos.ArquivoRotulo
- Gerencia operações de CRUD para rótulos.

  Pares.ParNomeIdRotulo
- Classe que representa a relação entre o nome e o ID de um rótulo.

Relacoes.TarefaRotulo
- Classe que representa a relação entre tarefas e rótulos.

aed3.ParIDEndereco
- Classe que representa a relação entre o ID e o endereço de um registro.

IO
- Classe principal que contém o menu de navegação do sistema.

Requisitos:
 - Java 8 ou superior

Contribuição:
- Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

Licença:
Este projeto está licenciado sob a MIT License.

/////////////////////////////////////////////////////////////////////////

Explicação das últimas classes criadas: 

ArquivoRotulo:
- Verificação de Tarefas Associadas: Antes de excluir um rótulo, o código verifica se existem tarefas associadas a ele. Se existirem, a exclusão é impedida e uma mensagem é exibida.
- Exclusão do Rótulo: Se não houver tarefas associadas, o rótulo é excluído do sistema. O código também remove o rótulo da Árvore B+ e imprime mensagens de sucesso ou erro conforme o resultado da operação.

Rotulo:
- A classe Rotulo é uma representação de um rótulo no sistema de gerenciamento de tarefas. Ela contém atributos para armazenar o id e o nome do rótulo, e possui construtores para inicializar objetos Rotulo com diferentes conjuntos de dados. Além disso, ela implementa a interface Registro, o que implica que deve fornecer implementações para os métodos definidos nessa interface.

MenuRotulo: 
- A classe MenuRotulo gerencia o menu de operações relacionadas aos rótulos no sistema de gerenciamento de tarefas. Ela inicializa as instâncias necessárias para manipular rótulos, tarefas e categorias, e exibe um menu de opções para o usuário, permitindo a inclusão, alteração, exclusão e listagem de rótulos, entre outras operações.

RotuloTarefa:
A classe RotuloTarefa representa a relação entre rótulos e tarefas no sistema de gerenciamento de tarefas. Ela contém atributos para armazenar os identificadores de rótulo e tarefa, e possui construtores para inicializar objetos RotuloTarefa com diferentes conjuntos de dados. Além disso, a classe implementa a interface aed3.RegistroArvoreBMais<RotuloTarefa> para permitir o armazenamento em uma árvore B+ e a interface Comparable<RotuloTarefa> para permitir a comparação entre objetos. O método clone permite criar cópias dos objetos RotuloTarefa.

Funções da Lista Invertida na Classe: 

ArquivoTarefa
A classe ArquivoTarefa utiliza uma lista invertida para gerenciar a indexação e busca de tarefas com base em termos específicos. Aqui está um resumo das funções relacionadas à lista invertida:

gerarLista() : 
- Descrição: Carrega uma lista de stop-words a partir de um arquivo de texto.
- Uso: As stop-words são palavras comuns que são removidas durante a indexação para melhorar a relevância das buscas.

removerStopWords(String[] nomeTarefa):
- Descrição: Remove as stop-words de um array de palavras.
- Uso: Utilizado para limpar os nomes das tarefas antes de indexá-los na lista invertida.

calcularFrequencia(String[] words, String word): 
- Descrição: Calcula a frequência de uma palavra em um array de palavras.
- Uso: Utilizado para determinar a relevância de uma palavra em uma tarefa.

adicionarLista():
- Descrição: Adiciona todas as tarefas à lista invertida.
- Uso: Utilizado para indexar todas as tarefas existentes no sistema.

adicionarLista(Tarefa tarefa, int idTarefa): 
- Descrição: Adiciona uma tarefa específica à lista invertida.
- Uso: Utilizado para indexar uma nova tarefa ou atualizar a indexação de uma tarefa existente.

idSomados(ArrayList<ElementoLista> arrayList):
- Descrição: Soma as frequências de termos para cada ID de tarefa.
- Uso: Utilizado para combinar os resultados de múltiplos termos de busca.

imprimirLista(ArrayList<ElementoLista> arrayList):
- Descrição: Imprime a lista de tarefas resultante de uma busca.
- Uso: Utilizado para exibir os resultados de uma busca ao usuário.

pesquisaPorTermo(String chave):
- Descrição: Realiza uma busca por um termo específico na lista invertida.
- Uso: Utilizado para encontrar tarefas que correspondem a um termo de busca fornecido pelo usuário.


Resumo:
- As funções relacionadas à lista invertida na classe ArquivoTarefa são responsáveis por carregar stop-words, remover stop-words de termos de busca, calcular a frequência de termos, adicionar tarefas à lista invertida, somar frequências de termos por ID de tarefa, imprimir resultados de busca e realizar buscas por termos específicos.

-  Essas funções permitem uma indexação eficiente e uma busca rápida e relevante de tarefas no sistema.

/////////////////////////////////////////////////////////////////////////
Perguntas e respostas:

01) O índice invertido com os termos das tarefas foi criado usando a classe ListaInvertida? SIM
02) O CRUD de rótulos foi implementado? SIM
03) No arquivo de tarefas, os rótulos são incluídos, alterados e excluídos em uma árvore B+?  SIM
04) É possível buscar tarefas por palavras usando o índice invertido? SIM
05) É possível buscar tarefas por rótulos usando uma árvore B+? SIM
06) O trabalho está completo? SIM
07) O trabalho é original e não a cópia de um trabalho de um colega? SIM
