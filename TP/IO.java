import java.util.Scanner;

public class IO {

    public static void main(String[] args) {

        Scanner console;

        try {
            console = new Scanner(System.in);
            int opcao;
            do {
                System.out.println("AEDsIII");
                System.out.println("-------");
                System.out.println("\n> Início");
                System.out.println("1 - Tarefas");
                System.out.println("2 - Categorias");
                System.out.println("3 - Rotulos");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");
                try {
                    opcao = Integer.valueOf(console.nextLine());
                } catch (NumberFormatException e) {
                    opcao = -1;
                }
                switch (opcao) {
                    case 1:
                        (new MenuTarefas()).menu();
                    break;
                    case 2:
                        (new MenuCategorias()).menu();
                    break;
                    case 3:
                        (new MenuRotulo()).menu();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida!");

                        break;
                }
            } while (opcao != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}