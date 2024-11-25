import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuPesquisaTarefas {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Scanner console = new Scanner(System.in);

    public MenuPesquisaTarefas() throws Exception {

    }

    public void menu(ArquivoTarefa arqTarefa, ArquivoRotulo arquivoRotulo, ArquivoCategoria arquivoCategoria) throws Exception {

        int opcao;
        do {

            System.out.println("AEDsIII");
            System.out.println("-------");
            System.out.println("\n> Início > Tarefas > Mostrar/Pesquisar");
            System.out.println("1 - Mostrar tudo          \n");
            System.out.println("2 - Pesquisa por termo    \n");
            System.out.println("3 - Pesquisa por Categoria\n");
            System.out.println("4 - Pesquisa por Rotulo   \n");
            System.out.println("0 - Voltar                \n");
            System.out.print("Opção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    arqTarefa.mostrarTarefas();
                break;
                case 2:
                    pesquisaPorTermo(arqTarefa);
                    break;
                case 3:
                    System.out.println("Selecione a categoria desejada: ");
                    int idCategoria = arquivoCategoria.mostraCategorias2();
                    arqTarefa.mostrarTarefasCategoria(idCategoria);
                    break;
                case 4:
                    System.out.println("Selecione o rotulo desejado: ");
                    int idRotulo = arquivoRotulo.selecionaRotulos();
                    arqTarefa.mostrarTarefasRotulos(idRotulo);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    public void pesquisaPorTermo(ArquivoTarefa arqTarefa) throws Exception {

        String termo;
        System.out.println("Digite o termo a ser pesquisado: ");
        termo = console.nextLine();
        arqTarefa.pesquisaPorTermo(termo);

    }

}
