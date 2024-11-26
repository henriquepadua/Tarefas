package Arquivos;
import aed3.*;

import java.io.RandomAccessFile;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import Classes.Tarefa;
import Pares.ParNomeIdTarefa;
import Relacoes.CategoriaTarefa;
import Relacoes.RotuloTarefa;
import Relacoes.TarefaRotulo;

public class ArquivoTarefa extends aed3.Arquivo<Tarefa> {

    /*
     * ATRIBUTOS
     */

    int quantidadeTarefas = 0;
    ArvoreBMais<ParNomeIdTarefa> arvoreBMais;
    Arquivo<Tarefa> arqTarefas;
    ArvoreBMais<CategoriaTarefa> arvoreCT;
    ArvoreBMais<RotuloTarefa> arvoreRT;
    ArvoreBMais<TarefaRotulo> arvoreTR;
    Scanner console = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    ArquivoCategoria arquivoCategoria = new ArquivoCategoria("categorias.db", 5);
    ArquivoRotulo arquivoRotulo = new ArquivoRotulo("rotulos.db", 5);
    ArrayList<String> stopWords = new ArrayList<String>();
    ListaInvertida listaInvertida;

    /*
     * FUNCOES
     */

    /** Construtor padrao */
    public ArquivoTarefa(String nomeArquivo, int ordemArvore) throws Exception {
        // super pra construtor padrao da classe pai
        super(nomeArquivo, Tarefa.class.getConstructor());

        // inicializacao de arquivos
        arqTarefas = new Arquivo<>(nomeArquivo, Tarefa.class.getConstructor());

        // inicializacao das arvores
        arvoreCT = new ArvoreBMais<>(CategoriaTarefa.class.getConstructor(), 5, "dados/CategoriaTarefa.db");
        arvoreRT = new ArvoreBMais<>(RotuloTarefa.class.getConstructor(), 5, "dados/RotuloTarefa.db");
        arvoreTR = new ArvoreBMais<>(TarefaRotulo.class.getConstructor(), 5, "dados/TarefaRotulo.db");
        arvoreBMais = new ArvoreBMais<>(ParNomeIdTarefa.class.getConstructor(), ordemArvore, "dados/ParNomeIdTarefa.db");

        // carregar stop-words
        gerarLista();
    }

    @Override
    public int create(Tarefa tarefa) throws Exception {
        int id = arqTarefas.create(tarefa);
        arvoreCT.create(new CategoriaTarefa(tarefa.idCategoria, tarefa.getId()));
        
        for (int i = 0; i < tarefa.idRotulo.size(); i++) {
            arvoreRT.create(new RotuloTarefa(tarefa.idRotulo.get(i), tarefa.getId()));
        }
        for (int i = 0; i < tarefa.idRotulo.size(); i++) {
            arvoreTR.create(new TarefaRotulo( tarefa.getId(), tarefa.idRotulo.get(i)));
        }
        
        boolean arvoreSucesso = arvoreBMais.create(new ParNomeIdTarefa(tarefa.nome , tarefa.getId()));
        if (!arvoreSucesso) {
            // Tratamento se a inserção na árvore falhar
            System.err.println("Erro ao inserir na Árvore B+.");
        }
        
        adicionarLista(tarefa, id);
        quantidadeTarefas++;
        System.out.println(quantidadeTarefas);
        return id;
    }

    public Tarefa read(int idCategoria) throws Exception {
        ArrayList<CategoriaTarefa> ict = arvoreCT.read(new CategoriaTarefa(idCategoria, -1));
        for (int i = 0; i < ict.size(); i++) {
            System.out.println(ict.get(i).toString());
        }
        System.out.println("Resposta: ");
        System.out.println();

        if (ict.isEmpty()) {
            System.out.println("Categoria não encontrada");
            return null;
        }

        Tarefa tarefa = null;
        for (int i = 0; i < ict.size(); i++) {
            if (tarefa != null) {
                tarefa = arqTarefas.read(ict.get(i).idTarefa);
                System.out.println(tarefa.toString() + " ");
                System.out.println();
            }
        }

        return tarefa;
    }

    public void mostrarTarefas() throws Exception {
        ArrayList<CategoriaTarefa> ict = arvoreCT.read(null);

        System.out.println("\nLista de Tarefas: ");

        for (int i = 0; i < ict.size(); i++) {
            Tarefa tarefa = arqTarefas.read(ict.get(i).idTarefa);
            if (tarefa != null) {
                System.out.println(tarefa.toString(arquivoRotulo, arquivoCategoria) + " ");
                System.out.println();
            }
        }
    }

    public void mostrarTarefasCategoria(int idCategoria) throws Exception {
        ArrayList<CategoriaTarefa> ict = arvoreCT.read(null);
        for (int i = 0; i < ict.size(); i++) {
            Tarefa tarefa = arqTarefas.read(ict.get(i).idTarefa);
            if ((tarefa != null) && (tarefa.idCategoria == idCategoria)) {
                System.out.println(tarefa.toString() + " ");
                System.out.println();
            }
        }
    }

    public void mostrarTarefasRotulos(int idRotulo) throws Exception {
        ArrayList<RotuloTarefa> ict = arvoreRT.read(null);
        ArrayList<Integer> tarefasEncontradas = new ArrayList<Integer>(0);

        for (int i = 0; i < ict.size(); i++) {
            Tarefa tarefa = arqTarefas.read(ict.get(i).idTarefa);
            // System.out.println("IMPRESSAO PADRAO-----------\n" + tarefa.toString());
            for (int j = 0; j < tarefa.idRotulo.size(); j++) {
                if ((tarefa != null) && (!tarefasEncontradas.contains(tarefa.id))
                        && (tarefa.idRotulo.get(j) == idRotulo)) {
                    System.out.println(tarefa.toString(arquivoRotulo, arquivoCategoria) + " ");
                    System.out.println();
                    tarefasEncontradas.add(tarefa.id);
                }
            }
        }
    }

    public void delete() throws Exception {
        boolean resp = false;
        String entrada;
        System.out.println("Você realmente deseja apagar alguma tarefa? (S/N)");
        char caracter = console.nextLine().charAt(0);
        if (caracter == 'S' || caracter == 's') {
            mostrarTarefas();
            System.out.println("Entre com o nome da tarefa a ser deletada: ");
            entrada = console.nextLine();
            ArrayList<CategoriaTarefa> ict = arvoreCT.read(null);
            Tarefa tarefa = null;
            for (int i = 0; i < ict.size(); i++) {
                tarefa = arqTarefas.read(ict.get(i).idTarefa);
                if (tarefa != null) {
                    if (tarefa.nome.equals(entrada)) {
                        int idTarefa = tarefa.getId();
                        resp = arqTarefas.delete(idTarefa);
                    }
                }
            }
            /*
            EXCLUSÃO NA ARVORE EM DESENVOLVIMENTO
            if ( resp == true ) {
            ArrayList<ParNomeIdTarefa> ictT = arvoreBMais.read(null);
                    for (int i = 0; i < ictT.size(); i++) {
                        if (ictT.get(i).nomeTarefa.equals(entrada)) {
                            System.out.println("Tarefa: " + entrada);
                        }
                    }

                    resp = arvoreBMais.delete(new ParNomeIdRotulo(ictT.get(i).nomeTarefa, -1));
                }
            */
            if (resp == true) {
                System.out.println("Tarefa apagada com sucesso!");
            }
        }
    }

    public void buscarTarefas() throws Exception {
        String entrada;

        System.out.println("Você deseja pesquisar alguma tarefa? (S/N)");
        char caracter = console.nextLine().trim().toUpperCase().charAt(0); // Melhorado para ser case insensitive

        if (caracter == 'S') {
            System.out.println("Mostrando as tarefas existentes...");
            mostrarTarefas(); // Presumo que você tenha esse método implementado

            System.out.println("Entre com o nome da tarefa a ser retornada: ");
            entrada = console.nextLine();

            ArrayList<CategoriaTarefa> ict = arvoreCT.read(null); // Certifique-se de que arvoreCT não retorne null
            if (ict != null) {
                Tarefa tarefa = null;
                for (CategoriaTarefa categoriaTarefa : ict) {
                    tarefa = arqTarefas.read(categoriaTarefa.idTarefa); // Lendo a tarefa a partir do ID da categoria
                    if (tarefa != null && tarefa.nome.equals(entrada)) { // Simplificado
                        System.out.println(tarefa.toString());
                    }
                }
            } else {
                System.out.println("Nenhuma tarefa encontrada.");
            }
        }
    }

    public int selecionaTarefa() throws Exception {
        int idTarefa = -1;
        String nomeTarefa;
        ArrayList<ParNomeIdTarefa> list = arvoreBMais.read(null);

        for (ParNomeIdTarefa item : list) {
            System.out.println(" - " + item.nomeTarefa);
        }
        System.out.print("-> ");

        nomeTarefa = console.nextLine();

        for (ParNomeIdTarefa item : list) {
            if (nomeTarefa.equals(item.nomeTarefa)) {
                idTarefa = item.idTarefa;
                break; // Encontrou a rotulo, sair do loop
            }
        }
        return idTarefa;
    }

    public void update() throws Exception {
        String entrada;
        boolean resp = false;
        System.out.println("Você deseja atualizar alguma tarefa? (S/N)");
        char caracter = console.nextLine().trim().toUpperCase().charAt(0);

        if (caracter == 'S') {
            System.out.println("Mostrando as tarefas existentes...");
            mostrarTarefas(); // Presumo que você tenha esse método implementado

            System.out.println("Entre com o nome da tarefa a ser atualizada: ");
            entrada = console.nextLine();

            // Certifique-se de que 'arvoreCT' não retorne null
            ArrayList<CategoriaTarefa> ict = arvoreCT.read(null);

            if (ict != null) {
                for (CategoriaTarefa categoriaTarefa : ict) {
                    Tarefa tarefa = arqTarefas.read(categoriaTarefa.idTarefa); // Lendo a tarefa a partir do ID da
                                                                               // categoria

                    if (tarefa != null && tarefa.nome.equals(entrada)) {
                        System.out.println("Entre com o novo nome da tarefa: ");
                        String nome = console.nextLine();
                        System.out.println("Entre com a nova data de criação da tarefa (formato: yyyy-MM-dd): ");
                        LocalDate dataCriacao = LocalDate.parse(console.nextLine(), formatter);
                        System.out.println("Entre com a nova data de conclusão da tarefa (formato: yyyy-MM-dd): ");
                        LocalDate dataConclusao = LocalDate.parse(console.nextLine(), formatter);
                        System.out.println("Entre com o novo status da tarefa: ");
                        String status = console.nextLine();
                        System.out.println("Entre com a nova prioridade da tarefa: ");
                        String prioridade = console.nextLine();
                        System.out.println("Entre com a nova categoria da tarefa:");
                        tarefa.idCategoria = arquivoCategoria.mostraCategorias2();

                        int idLido;
                        do {
                            System.out.println("Entre com um novo rotulo ou insira algo invalido para parar:");
                            idLido = arquivoRotulo.selecionaRotulos();
                            if (idLido != -1)
                                tarefa.idRotulo.add(idLido);
                        } while (idLido != -1);

                        Tarefa testeTarefa = new Tarefa(tarefa.getId(), nome, dataCriacao, dataConclusao, status,
                                prioridade, tarefa.idCategoria, tarefa.idRotulo);
                        resp = arqTarefas.update(testeTarefa);
                        // Atribuindo resp como true após a atualização

                        if (resp) {
                            System.out.println("Tarefa atualizada com sucesso!");
                        }
                    }
                }
            } else {
                System.out.println("Nenhuma tarefa encontrada.");
            }
        }

    }

    public String removerAcentos(String texto) {
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        // Remover caracteres não ASCII (acentos e cedilha)
        return textoNormalizado.replaceAll("[^\\p{ASCII}]", "");
    }

    public int qntTarefaCategoria(int idCategoria) throws Exception {
        int n = 0;
        ArrayList<CategoriaTarefa> ict = arvoreCT.read(null);
        for (int i = 0; i < ict.size(); i++) {
            Tarefa tarefa = arqTarefas.read(ict.get(i).idTarefa);
            if ((tarefa != null) && (tarefa.idCategoria == idCategoria)) {
                n++;
            }
        }
        return n;
    }

    public int qntTarefaRotulo(int idRotulo) throws Exception {
        int n = 0;
        ArrayList<RotuloTarefa> ict = arvoreRT.read(null);
        for (int i = 0; i < ict.size(); i++) {
            Tarefa tarefa = arqTarefas.read(ict.get(i).idTarefa);
            if ((tarefa != null) && (tarefa.existIdRotulo(idRotulo))) {
                n++;
            }
        }
        return n;
    }

    /** Carregar arquivo de stop-words */
    public void gerarLista() throws Exception {
        // abrir
        RandomAccessFile raf = new RandomAccessFile(".\\dados\\stopWords.txt", "r");

        // ler
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            stopWords.add(raf.readLine());
        }

        // fechar
        raf.close();
    }

    public String[] removerStopWords(String[] nomeTarefa) {
        ArrayList<String> aux = new ArrayList<>();

        for (String palavra : nomeTarefa) {
            if (!stopWords.contains(palavra)) {
                aux.add(palavra);
            }
        }
        return aux.toArray(new String[0]);
    }

    public static float calcularFrequencia(String[] words, String word) {
        // Conta o número de vezes que a palavra aparece no array
        int count = 0;
        for (String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }
        // Calcula a frequência
        float frequency = (float) count / words.length;
        return frequency;
    }

    public void adicionarLista() throws Exception {

        ArrayList<CategoriaTarefa> ict = arvoreCT.read(null);

        if (ict != null) { // Verifica se a lista retornada não é nula
            for (CategoriaTarefa e : ict) {
                if (e != null) { // Verifica se a categoria não é nula
                    // Lê a tarefa correspondente com base no ID
                    Tarefa tarefa = arqTarefas.read(e.idTarefa);

                    if (tarefa != null) {
                        // Adiciona a tarefa à lista com o ID da tarefa
                        adicionarLista(tarefa, tarefa.getId());
                    }
                }
            }
        } else {
            System.out.println("Erro");
        }
    }

    public void adicionarLista (Tarefa tarefa, int idTarefa) throws Exception {
        float frequencia;
        int i = 0;
        String nomeTarefa = removerAcentos(tarefa.nome);
        String[] vetNome = nomeTarefa.split(" ");
        vetNome = removerStopWords(vetNome);

        // Conjunto para verificar se a palavra já foi processada
        HashSet<String> palavrasProcessadas = new HashSet<>();

        while (i < vetNome.length) {
            String palavraAtual = vetNome[i];

            // Verifica se a palavra já foi processada
            if (!palavrasProcessadas.contains(palavraAtual)) {
                frequencia = calcularFrequencia(vetNome, palavraAtual);
                listaInvertida.create(palavraAtual, new ElementoLista(idTarefa, frequencia));
                // listaInvertida.print();

                // Adiciona a palavra ao conjunto de palavras processadas
                palavrasProcessadas.add(palavraAtual);
            }
            i++;
        }
        listaInvertida.incrementaEntidades();
    }

    public static ArrayList<ElementoLista> idSomados(ArrayList<ElementoLista> arrayList) {
        // Mapa para armazenar a soma dos valores por ID
        Map<Integer, Double> somaPorId = new HashMap<>();

        // Percorre o arrayList e acumula os valores com o mesmo ID
        for (ElementoLista elemento : arrayList) {
            int id = elemento.getId();
            double valor = elemento.getFrequencia(); // Corrigido para acessar o método getter

            somaPorId.put(id, somaPorId.getOrDefault(id, 0.0) + valor);
        }

        // Converte o mapa para um ArrayList de ElementoLista com a soma dos valores
        ArrayList<ElementoLista> resultado = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : somaPorId.entrySet()) {
            resultado.add(new ElementoLista(entry.getKey(), entry.getValue().floatValue()));
        }

        return resultado;
    }

    public void imprimirLista(ArrayList<ElementoLista> arrayList) throws Exception {
        Tarefa tarefa;
        System.out.println("Tarefas retornadas: \n");

        for (ElementoLista e : arrayList) {
            tarefa = arqTarefas.read(e.getId());
            System.out.println(tarefa.toString());
        }
    }

    public void pesquisaPorTermo(String chave) throws Exception {
        listaInvertida = new ListaInvertida(4, "dados/dicionario.listainv.db", "dados/blocos.listainv.db");
        adicionarLista();

        String aux = removerAcentos(chave);
        String[] vet = aux.split(" ");
        vet = removerStopWords(vet);
        float tf;
        int numeroEntidades = listaInvertida.numeroEntidades();
        ArrayList<ElementoLista> arrayList = new ArrayList<>();

        for (int i = 0; i < vet.length; i++) {
            ElementoLista[] teste = listaInvertida.read(vet[i]);

            tf = numeroEntidades / teste.length;

            for (int j = 0; j < teste.length; j++) {
                teste[j].frequencia = teste[j].frequencia * tf;
                arrayList.add(teste[j]);
            }
        }
        arrayList = idSomados(arrayList);
        arrayList.sort(Comparator.comparing(ElementoLista::getFrequencia).reversed());

        imprimirLista(arrayList);

        listaInvertida.DeletarArquivos();
    }

}
