import Classes.lzw;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MenuBackup {

    private static Scanner console = new Scanner(System.in);
    private static String PastaDados = "dados";
    private static String PastaBackups = "backups";

    public void menu() throws Exception {
        int opcao;
        do {
            System.out.println("AEDsIII");
            System.out.println("-------");
            System.out.println("\n> Início > Backups");
            System.out.println("1 - Criar Backup");
            System.out.println("2 - Carregar Backup");
            System.out.println("3 - Comparar Tamanhos");
            System.out.println("0 - Voltar");

            System.out.print("Opção: ");
            try {
                opcao = Integer.valueOf(console.nextLine());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    comprimirDados();
                    break;
                case 2:
                    recuperarDados("backups\\teste");
                    break;
                case 3:
                    compararTamanhos();
                    break;
                case 4:
                        String[] nomes = getFileNames("backups");
                        System.out.println(nomes.length);
                        for(int i = 0; i < nomes.length; i++){
                            System.out.println(nomes[i]);
                        }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);
    }

    public void comprimirDados() {
        try {
            System.out.println("Digite o nome do backup a ser salvo: ");
            String nomeBackup = console.nextLine();

            String[] result = getFileNames(PastaDados);
            List<String> arquivos = List.of(result);
            for (String arquivoNome : arquivos) {
                System.out.println(arquivoNome);
            }

            compactarArquivos(arquivos, nomeBackup);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String[] getFileNames(String folderPath) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("O caminho especificado não é uma pasta válida.");
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(folderPath + File.separator + file.getName());
                }
            }
        }

        return fileNames.toArray(new String[0]);
    }

    public static byte[] fileToByteArray(String filePath) {
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void compactarArquivos(List<String> arquivos, String nomeBackup) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(
                new FileOutputStream(PastaBackups + "\\" + nomeBackup))) {
            dataOutputStream.writeInt(arquivos.size());

            for (String diretorioArquivo : arquivos) {
                dataOutputStream.writeInt(diretorioArquivo.length());
                dataOutputStream.write(diretorioArquivo.getBytes());

                byte[] vetorBytes = fileToByteArray(diretorioArquivo);
                String textoOriginal = new String(vetorBytes); // Converter bytes para String
                String textoCompactado = (new lzw()).lzw_compress(textoOriginal); // Compactação
                byte[] bytesCompactados = textoCompactado.getBytes(); // String compactada para byte[]

                dataOutputStream.writeInt(bytesCompactados.length);
                dataOutputStream.write(bytesCompactados);
            }

            System.out.println("Dados gravados com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recuperarDados(String caminhoBackup) {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(caminhoBackup))) {
            // Exclui todos os arquivos existentes na pasta "dados"
            File pastaDados = new File(PastaDados);
            if (pastaDados.exists() && pastaDados.isDirectory()) {
                for (File file : Objects.requireNonNull(pastaDados.listFiles())) {
                    if (!file.delete()) {
                        System.out.println("Falha ao excluir o arquivo: " + file.getName());
                    }
                }
            }

            int numeroArquivos = dataInputStream.readInt();
            System.out.println("Número de arquivos armazenados: " + numeroArquivos);

            for (int i = 0; i < numeroArquivos; i++) {
                int tamanhoNomeArquivo = dataInputStream.readInt();
                byte[] nomeBytes = new byte[tamanhoNomeArquivo];
                dataInputStream.readFully(nomeBytes);
                String nomeArquivo = new String(nomeBytes);

                // Garante que o nome do arquivo não contenha caminhos duplicados
                nomeArquivo = new File(nomeArquivo).getName();

                System.out.println("Nome do arquivo: " + nomeArquivo);

                int tamanhoBytes = dataInputStream.readInt();
                byte[] arquivoBytes = new byte[tamanhoBytes];
                dataInputStream.readFully(arquivoBytes);

                String textoCompactado = new String(arquivoBytes); // Bytes para String compactada
                String textoDescompactado = (new lzw()).lzw_extract(textoCompactado); // Descompactação
                byte[] bytesDescompactados = textoDescompactado.getBytes(); // String descompactada para byte[]

                System.out.println("Tamanho dos dados: " + bytesDescompactados.length);
                System.out.println("Conteúdo dos bytes: " + new String(bytesDescompactados));

                salvarArquivo(PastaDados + File.separator + nomeArquivo, bytesDescompactados);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salvarArquivo(String nomeArquivo, byte[] dados) {
        try (FileOutputStream fos = new FileOutputStream(nomeArquivo)) {
            fos.write(dados);
            System.out.println("Arquivo " + nomeArquivo + " recuperado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compararTamanhos() {
        try {
            String[] arquivosOriginais = getFileNames(PastaDados);

            // Soma dos tamanhos dos arquivos originais
            long tamanhoOriginal = 0;
            for (String arquivo : arquivosOriginais) {
                File file = new File(arquivo);
                tamanhoOriginal += file.length();
            }

            System.out.println("Digite o nome do backup para comparação: ");
            String nomeBackup = console.nextLine();

            // Tamanho do arquivo compactado
            File backup = new File(PastaBackups + "\\" + nomeBackup);
            long tamanhoCompactado = backup.length();

            // Exibe os resultados
            System.out.println("Tamanho total dos arquivos originais: " + tamanhoOriginal + " bytes");
            System.out.println("Tamanho do arquivo compactado: " + tamanhoCompactado + " bytes");

            // Calcula a redução percentual
            if (tamanhoOriginal > 0) {
                double reducao = 100.0 * (1 - ((double) tamanhoCompactado / tamanhoOriginal));
                System.out.printf("Redução de tamanho: %.2f%%\n", reducao);
            } else {
                System.out.println("Não foi possível calcular a redução devido ao tamanho original ser zero.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao comparar tamanhos: " + e.getMessage());
            e.printStackTrace();
        }
    }

}