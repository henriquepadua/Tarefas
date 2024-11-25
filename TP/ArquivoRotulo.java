import java.util.ArrayList;
import java.util.Scanner;

import aed3.*;

public class ArquivoRotulo extends aed3.Arquivo<Rotulo> {

    ArvoreBMais<ParNomeIdRotulo> arvoreBMais;
    ArvoreBMais<TarefaRotulo> arvoreTR;
    Arquivo<Rotulo> arquivo;
    Scanner console = new Scanner(System.in);
    // ArquivoTarefa arquivoTarefa;

    public ArquivoRotulo (String nomeArquivo, int ordemArvore) throws Exception {
        super(nomeArquivo, Rotulo.class.getConstructor());
        arquivo = new Arquivo<>(nomeArquivo, Rotulo.class.getConstructor());
        arvoreBMais = new ArvoreBMais<>(ParNomeIdRotulo.class.getConstructor(), ordemArvore, "dados/ParNomeIdRotulo.db");
        arvoreTR = new ArvoreBMais<>(TarefaRotulo.class.getConstructor(), 5, "dados/TarefaRotulo.db");
    }

    @Override
    public int create(Rotulo rotulo) throws Exception {
        int id = super.create(rotulo); // Usar o método da classe pai
        boolean arvoreSucesso = arvoreBMais.create(new ParNomeIdRotulo(rotulo.get(), id));
        if (!arvoreSucesso) {
            // Tratamento se a inserção na árvore falhar
            System.err.println("Erro ao inserir na Árvore B+.");
        }
        return id;
    }

    public void print() throws Exception {
        ArrayList<TarefaRotulo> ict = arvoreTR.read(null);

        System.out.println("\nLista de Tarefas: ");

        for (int i = 0; i < ict.size(); i++) {
            Rotulo rotulo = arquivo.read(ict.get(i).idRotulo);
            if (rotulo != null) {
                System.out.println(rotulo.toString() + " ");
                System.out.println();
            }
        }
    }

    public void printTree() throws Exception {
        ArrayList<ParNomeIdRotulo> list = arvoreBMais.read(null);

        System.out.println("\n TODOS OS ROTULOS \n");
        for (ParNomeIdRotulo item : list) {
            System.out.println(" -> " + item.nomeRotulo);
        }
        System.out.println();
    }

    
    public int selecionaRotulos() throws Exception {
        int idRotulo = -1;
        String nomeRotulo;
        ArrayList<ParNomeIdRotulo> list = arvoreBMais.read(null);

        for (ParNomeIdRotulo item : list) {
            System.out.println(" - " + item.nomeRotulo);
        }
        System.out.print("-> ");

        nomeRotulo = console.nextLine();

        for (ParNomeIdRotulo item : list) {
            if (nomeRotulo.equals(item.nomeRotulo)) {
                idRotulo = item.idRotulo;
                break; // Encontrou a rotulo, sair do loop
            }
        }
        return idRotulo;
    }

    public ArrayList<String> lerRotulosTarefa(int idTarefa) throws Exception {
        // Inicializa a resposta como uma lista vazia
        ArrayList<String> resposta = new ArrayList<>();
    
        if (idTarefa >= 0) {
            // Recupera todos os rótulos
            ArrayList<ParNomeIdRotulo> rotulosDisponiveis = arvoreBMais.read(null);
            if (rotulosDisponiveis == null || rotulosDisponiveis.isEmpty()) {
                System.out.println("Nenhum rótulo disponível no sistema.");
                return resposta; // Retorna lista vazia
            }
    
            // Recupera todas as associações tarefa-rótulo
            ArrayList<TarefaRotulo> associacoes = arvoreTR.read(null);
            if (associacoes == null || associacoes.isEmpty()) {
                System.out.println("Nenhuma associação tarefa-rótulo encontrada.");
                return resposta; // Retorna lista vazia
            }
    
            // Itera pelas associações para encontrar os rótulos associados
            for (TarefaRotulo associacao : associacoes) {
                if (associacao.idTarefa == idTarefa) { // Verifica se o ID da tarefa corresponde
                    for (ParNomeIdRotulo rotulo : rotulosDisponiveis) {
                        if (rotulo.idRotulo == associacao.idRotulo) { // Verifica se o ID do rótulo corresponde
                            resposta.add(rotulo.nomeRotulo); // Adiciona o nome do rótulo à lista de resposta
                        }
                    }
                }
            }
        } else {
            System.out.println("ID da tarefa inválido: " + idTarefa);
        }
    
        return resposta;
    }

    public void mostrarRotulosDeTarefa (int idTarefa) throws Exception {
        if (idTarefa < 0) {
            System.out.println("ID da tarefa inválido.");
            return;
        }
    
        System.out.println("Rótulos associados à tarefa ID: " + idTarefa);
    
        // Recupera os rótulos associados à tarefa
        ArrayList<String> rotulos = lerRotulosTarefa(idTarefa);
    
        if (rotulos.isEmpty()) {
            System.out.println("Nenhum rótulo associado à tarefa ID: " + idTarefa);
        } else {
            for (String nomeRotulo : rotulos) {
                System.out.println(" - " + nomeRotulo);
            }
        }
    }

    public void mostrarRotulos (int idTarefa) throws Exception {
        if (idTarefa < 0) {
            return;
        }
    
        // Recupera os rótulos associados à tarefa
        ArrayList<String> rotulos = lerRotulosTarefa(idTarefa);
    
        if ( !rotulos.isEmpty() ) {
            for (String nomeRotulo : rotulos) {
                System.out.print( nomeRotulo + " " );
            }
        }
    }

    public String StringRotulos (int idTarefa) {
        String resposta = new String();

        if (idTarefa < 0) {
            return null;
        }
    
        // Recupera os rótulos associados à tarefa
        ArrayList<String> rotulos = new ArrayList<String>();
        try{
            rotulos = lerRotulosTarefa(idTarefa);
        }catch ( Exception e ){
            return null;
        }

        if ( !rotulos.isEmpty() ) {
            String nomeRotulo = new String();
            for ( int i = 0; i < rotulos.size(); i++) {
                nomeRotulo = rotulos.get(i);
                resposta += nomeRotulo;
                if ( i < (rotulos.size() - 1) ) resposta += " | ";
            }
        }

        return resposta;
    }
    

    public void update() throws Exception {
        int idRotulo;
        Rotulo C;
        boolean sucesso;
        System.out.println("Você deseja alterar alguma rotulo? (S/N): ");
        char caracter = console.nextLine().charAt(0);
        if (caracter == 'S' || caracter == 's') {
            idRotulo = selecionaRotulos();
            C = super.read(idRotulo);

            if (C != null) {
                System.out.println("Rotulo atual: " + C.get());
                System.out.print("Digite o novo nome da rotulo: ");
                String novoNome = console.nextLine();
                String nome = C.get();

                // Atualiza o nome da rotulo
                C.set(novoNome);
                C.print();

                // Atualiza o registro no arquivo
                sucesso = super.update(C);
                arvoreBMais.delete(new ParNomeIdRotulo(nome, idRotulo));
                arvoreBMais.create(new ParNomeIdRotulo(novoNome, idRotulo));

                if (sucesso == true) {
                    System.out.println("atualizado com sucesso");
                } else {
                    System.out.println("Erro ao atualizar");
                }
            }
        }
    }

    public void delete() throws Exception {
        int idRotulo = 0;
        String nomeRotulo = "";
        
        System.out.println("Você realmente deseja apagar algum rotulo? (S/N) ");
        
        char caracter = console.nextLine().trim().toUpperCase().charAt(0);

        if (caracter == 'S') {
            idRotulo = selecionaRotulos();

            System.out.println(idRotulo);
            ArquivoTarefa arquivoTarefa = new ArquivoTarefa("Tarefas.db", 5);
            int quant = arquivoTarefa.qntTarefaRotulo(idRotulo);

            if (quant != 0) {
                System.out.println("Não é possivel apagar a rotulo, pois ela possui tarefas associadas");
            } else {
                boolean resp = super.delete(idRotulo);
                System.out.println("RESP = " + resp);
                if (resp == true) {
                    ArrayList<ParNomeIdRotulo> ict = arvoreBMais.read(null);
                    for (int i = 0; i < ict.size(); i++) {
                        if (ict.get(i).idRotulo == idRotulo) {
                            System.out.println("Rotulo: " + nomeRotulo);
                        }
                    }

                    resp = arvoreBMais.delete(new ParNomeIdRotulo(nomeRotulo, -1));
                    if (resp == true) {
                        System.out.println("Rotulo apagada com sucesso");
                    } else {
                        System.out.println("Erro ao apagar a rotulo");
                    }
                }
            }
        }

    }

}