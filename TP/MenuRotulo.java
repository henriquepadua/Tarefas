import java.util.Scanner;

public class MenuRotulo {

    ArquivoRotulo    arqRotulo;
    ArquivoTarefa    arqTarefa;
    ArquivoCategoria arqCategoria;
    private static Scanner console = new Scanner(System.in);

    public MenuRotulo (  ) throws Exception {
        arqRotulo    = new ArquivoRotulo("rotulos.db", 5);
        arqCategoria = new ArquivoCategoria("categorias.db", 5);
        arqTarefa    = new ArquivoTarefa("Tarefas.db", 5);
    }

    public void menu() throws Exception {

        int opcao;
        do {

            System.out.println("AEDsIII");
            System.out.println("-------");
            System.out.println("\n> Início > Rotulos");
            System.out.println("1 - Incluir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Mostrar");
            System.out.println("5 - Pesquisar Rotulos por Tarefa");
            System.out.println("0 - Voltar");

            System.out.print("Opção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    incluirRotulo();
                    break;
                case 2:
                    arqRotulo.update();
                    break;
                case 3:
                    arqRotulo.delete();
                    break;
                case 4:
                    arqRotulo.selecionaRotulos();
                    break;
                case 5:
                    System.out.println("Selecione a tarefa desejada: ");
                    int idTarefa = arqTarefa.selecionaTarefa();
                    arqRotulo.mostrarRotulosDeTarefa(idTarefa);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void incluirRotulo() {
        String nome;
        boolean dadosCompletos = false;

        System.out.println("\nInclusão de rotulo");
        do {
            System.out.print("\nNome da rotulo (min. de 5 letras): ");
            nome = console.nextLine();
            if (nome.length() >= 5 || nome.length() == 0)
                dadosCompletos = true;
            else
                System.err.println("O nome da rotulo deve ter no mínimo 5 caracteres.");
        } while (!dadosCompletos);

        if (nome.length() == 0)
            return;

        System.out.println("Confirma a inclusão da rotulo? (S/N) ");
        char resp = console.nextLine().charAt(0);
        if (resp == 'S' || resp == 's') {
            try {
                Rotulo r = new Rotulo(nome);

                arqRotulo.create(r);
                System.out.println("Rotulo criado com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível criar a rotulo!");
            }
        }
    }

}
