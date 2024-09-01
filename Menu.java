import java.util.Scanner;

public class Menu {
    public void exibeMenu() throws Exception{
        Scanner sc = new Scanner(System.in);
        Arquivo arquivo = new Arquivo();
        int opcao, id = 0;;
        boolean sair = false;

        while(!sair){
            System.out.println("******** Menu Tarefas ********\n");
            System.out.println("Escolha uma opção:");
            System.out.println("1) Criar Tarefas");
            System.out.println("2) Pesquisar Tarefas");
            System.out.println("3) Alterar Tarefas");
            System.out.println("4) Deletar Tarefas");
            System.out.println("5) Sair");
            Tarefas tarefas = new Tarefas();
            opcao = sc.nextInt();

            switch(opcao) { // trata as opcoes
                case 1:
                    Arquivo.criarTarefas(tarefas);
                    break;
                case 2:
                    System.out.println("Digite o id que deseja pesquisar: ");
                    id = sc.nextInt();
                    Arquivo.pesquisa(id,tarefas);
                    break;
                case 3:
                    System.out.println("Digite o id que deseja alterar: ");
                    id = sc.nextInt();
                    System.out.println("Selecione a opcao que deseja alterar: ");
                    System.out.println("1) Nome \n 2) Data de Criação \n 3) Data de Criação \n 4) Status \n 5) Prioridade \n");
                    byte escolha = sc.nextByte();
                    arquivo.update(id, escolha);
                    break;
                case 4:
                    System.out.println("Digite o id que deseja deletar: ");
                    id = sc.nextInt();
                    arquivo.delete(id);
                    break;
                case 5:
                    sair = true;
                    System.out.println("Saindo...");
                    sc.close();
                    break;
                default:
                    System.out.println("Digite uma opção válida!!");
            }
        }
    }
}