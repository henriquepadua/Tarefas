import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import aed3.*;

public class ArquivoCategoria extends aed3.Arquivo<Categoria> {

    ArvoreBMais<ParNomeId> arvoreBMais;
    RandomAccessFile arquivo;
    Scanner console = new Scanner(System.in);
    // ArquivoTarefa arquivoTarefa;

    public ArquivoCategoria() throws Exception {
        super("categorias.db", Categoria.class.getConstructor());
        arquivo = new RandomAccessFile("dados/categorias.db", "rw");

    }

    public ArquivoCategoria(String nomeArquivo, int ordemArvore) throws Exception {
        super(nomeArquivo, Categoria.class.getConstructor());
        arquivo = new RandomAccessFile("dados/categorias.db", "rw");
        arvoreBMais = new ArvoreBMais<>(ParNomeId.class.getConstructor(), ordemArvore, "dados/ParNomeId.db");
        // arquivoTarefa = new ArquivoTarefa("tarefas.db", 5); // Inicializa
        // ArquivoTarefa
    }

    @Override
    public int create(Categoria categoria) throws Exception {
        int id = super.create(categoria); // Usar o método da classe pai
        boolean arvoreSucesso = arvoreBMais.create(new ParNomeId(categoria.nome, id));
        if (!arvoreSucesso) {
            // Tratamento se a inserção na árvore falhar
            System.err.println("Erro ao inserir na Árvore B+.");
        }
        return id;
    }

    public void mostraCategorais() throws Exception {
        short tam;
        byte[] b;
        byte lapide;
        arquivo.seek(4);

        while (arquivo.getFilePointer() < arquivo.length()) {
            lapide = arquivo.readByte(); // Lê a lápide (indicador de remoção)
            tam = arquivo.readShort(); // Lê o tamanho do registro

            if (lapide != 0) { // Verifica se o registro é válido (não removido)

                b = new byte[tam];
                arquivo.read(b);
                Categoria c = new Categoria();
                c.fromByteArray(b);
                System.out.println(c.id + " - " + c.nome);
            } else {
                // Caso o registro tenha sido removido, pula para o próximo
                arquivo.seek(arquivo.getFilePointer() + tam);
            }
        }
    }

    public int mostraCategorias2() throws Exception {
        int idCategoria = -1;
        String nomeCategoria;
        ArrayList<ParNomeId> list = arvoreBMais.read(null);

        for (ParNomeId item : list) {
            System.out.println(" - " + item.nomeCategoria);
        }
        System.out.print("-> ");

        nomeCategoria = console.nextLine();

        for (ParNomeId item : list) {
            if (nomeCategoria.equals(item.nomeCategoria)) {
                idCategoria = item.idCategoria;
                break; // Encontrou a categoria, sair do loop
            }
        }
        return idCategoria;
    }

    public void mostraCategorias3() throws Exception {
        ArrayList<ParNomeId> list = arvoreBMais.read(null);

        System.out.println("\n TODAS AS CATEGORIAS \n");
        for (ParNomeId item : list) {
            System.out.println(" -> " + item.nomeCategoria);
        }
        System.out.println();
    }

    public void update() throws Exception {
        int idCategoria;
        Categoria C;
        boolean sucesso;
        System.out.println("Você deseja alterar alguma categoria? (S/N): ");
        char caracter = console.nextLine().charAt(0);
        if (caracter == 'S' || caracter == 's') {
            idCategoria = mostraCategorias2();
            C = super.read(idCategoria);

            if (C != null) {
                System.out.println("Categoria atual: " + C.nome);
                System.out.print("Digite o novo nome da categoria: ");
                String novoNome = console.nextLine();
                String nome = C.nome;

                // Atualiza o nome da categoria
                C.nome = novoNome;
                System.out.println(C.toString());

                // Atualiza o registro no arquivo
                sucesso = super.update(C);
                arvoreBMais.delete(new ParNomeId(nome, idCategoria));
                arvoreBMais.create(new ParNomeId(novoNome, idCategoria));

                if (sucesso == true) {
                    System.out.println("atualizado com sucesso");
                } else {
                    System.out.println("Erro ao atualizar");
                }
            }
        }
    }

    public void delete() throws Exception {
        int idCategoria = 0;
        String nomeCategoria = "";
        
        char caracter = console.nextLine().trim().toUpperCase().charAt(0);System.out.println("Você realmente deseja apagar alguma categoria? (S/N) ");
        if (caracter == 'S') {
            idCategoria = mostraCategorias2();

            System.out.println(idCategoria);
            ArquivoTarefa arquivoTarefa = new ArquivoTarefa("Tarefas.db", 5);
            int quant = arquivoTarefa.qntTarefaCategoria(idCategoria);

            if (quant != 0) {
                System.out.println("Não é possivel apagar a categoria, pois ela possui tarefas associadas");
            } else {
                boolean resp = super.delete(idCategoria);
                System.out.println("RESP = " + resp);
                if (resp == true) {
                    ArrayList<ParNomeId> ict = arvoreBMais.read(null);
                    for (int i = 0; i < ict.size(); i++) {
                        if (ict.get(i).idCategoria == idCategoria) {
                            System.out.println("Categoria: " + nomeCategoria);
                        }
                    }

                    resp = arvoreBMais.delete(new ParNomeId(nomeCategoria, -1));
                    if (resp == true) {
                        System.out.println("Categoria apagada com sucesso");
                    } else {
                        System.out.println("Erro ao apagar a categoria");
                    }
                }
            }
        }

    }

}
