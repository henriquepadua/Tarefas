
import java.io.*;
import java.util.Date;
import java.util.Scanner;


public class Arquivo{
    private File arquivo;
    public static RandomAccessFile fileReader;
    private static long posicao;
    final int cabecalho = 4;
    
    public Arquivo(String arquivo)throws IOException{
        this.arquivo = new File(arquivo);
        fileReader = new RandomAccessFile(arquivo, "rw");
        if(fileReader.length() == 0) fileReader.writeInt(0);
    }

    public Arquivo(){}

    public static Tarefas leTarefas(int tamanhoArquivo, int id, boolean lapide) throws Exception{
        Tarefas tarefas = new Tarefas();
        String s = "";
        Date date = tarefas.getDataCriacao();
        int tamanhoString = 0;//double value = 0;byte overall = 0;

        System.out.print("Tarefa#" + id + "=");
        tarefas.setLapide(lapide);
        tarefas.setId(id);
        tamanhoString = fileReader.readInt();
        tarefas.setNome(s = fileReader.readUTF());
        System.out.print(","+s);
        tamanhoString = fileReader.readInt();
        tarefas.setDataCriacao(s = fileReader.readUTF());
        System.out.println(","+s);
        tamanhoString = fileReader.readInt();
        tarefas.setDataConclusao(s = fileReader.readUTF());
        System.out.println(","+s);
        tamanhoString = fileReader.readInt();
        tarefas.setStatus(s = fileReader.readUTF());
        tamanhoString = fileReader.readInt();
        System.out.print(s);
        tarefas.setPrioridade(s = fileReader.readUTF());
        System.out.print(","+s);
        
        return tarefas;
    }

    public static int create(Tarefas tarefas) throws IOException{ 

        //fileReader = new RandomAccessFile("Tarefas.db", "rw");
        fileReader.seek(0);
        
        if(fileReader.length() == 0) fileReader.writeInt(0);
        
        int ultimoId = fileReader.readInt();
        int proximoId = ultimoId + 1;
        
        fileReader.seek(0);
        fileReader.writeInt(proximoId);

        fileReader.seek(fileReader.length());

        tarefas.setLapide(true);
        tarefas.setId(proximoId);

        byte[] ba = tarefas.toByteArray();
               
        fileReader.writeInt(ba.length);
        fileReader.write(ba); 
        return proximoId;    
    }

    public static int criarTarefas(Tarefas tarefas) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome: ");
        tarefas.setNome(sc.nextLine());
                    
        System.out.println("Digite a Data de Criação: ");
        tarefas.setDataCriacao(sc.next());
        
        System.out.println("Digite a Data de Conclusão: ");
        tarefas.setDataConclusao(sc.next());

        System.out.println("Digite o Status: ");
        tarefas.setStatus(sc.next());

        System.out.println("Digite a Prioridade: ");
        tarefas.setPrioridade(sc.next());

        return create(tarefas);
    }

    public static Tarefas pesquisa(int id, Tarefas jogador) throws IOException{
        fileReader.seek(0);
        fileReader.readInt();
        int tamanhoTarefa;
        boolean lapide;
        int idJogador;

        try{
            while(fileReader.getFilePointer() < fileReader.length()){
                posicao = fileReader.getFilePointer();
                tamanhoTarefa = fileReader.readInt();
                lapide = fileReader.readBoolean();
                
                if(lapide){
                   // fileReader.readInt();
                    idJogador = fileReader.readInt();
                    if(idJogador == id){
                       jogador = leTarefas(tamanhoTarefa, id, lapide);
                        break;
                    }else {
                        fileReader.skipBytes(tamanhoTarefa - 5);
                    }
                } else{
                    fileReader.skipBytes(tamanhoTarefa - 1);
                }
            }

            if(jogador.getNome() == ""){ System.out.println("Id deletado");
                return null;
            }
            

        } catch(Exception e){
            System.err.println("Id nao encontrado");
        }

        return jogador;
    }

    public static void imprimeArquivo (long comeco) { // imprime as ids de um vo
		int ultimaId;
		int tamRegAtual;
		long pos0;
		int idAtual;
		
		try {
			fileReader.seek(comeco);
			ultimaId = fileReader.readInt();
			idAtual = -1;
			System.out.print("| ");
			while(idAtual != ultimaId) { // varre o fileReaderuivo e imprime as ids
				tamRegAtual = fileReader.readInt();
				pos0 = fileReader.getFilePointer();
				if(fileReader.readBoolean() != false) {
					idAtual = fileReader.readInt();
					System.out.print(idAtual + ", ");
				} else {
					System.out.print("*, ");
				}
				fileReader.seek(pos0);
				fileReader.skipBytes(tamRegAtual);
				System.out.print(tamRegAtual + "B | "); // teste
			}
			System.out.println("");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

    public void update(int id, byte opcao) throws Exception{ //metodo para atualizar jogador
        Tarefas tarefas = new Tarefas();
        Scanner sc = new Scanner(System.in);

        tarefas = pesquisa(id,tarefas);

        if(tarefas != null && tarefas.getLapide() == true){
            System.out.println("Tarefas selecionada:");
            //System.out.println(tarefas);        


            switch(opcao){
                case 1:
                    System.out.println("Digite o novo nome: ");
                    tarefas.setNome(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Digite a nova Data de Criação: ");
                    tarefas.setDataCriacao(sc.nextLine());
                    break;
                case 3:
                    System.out.println("Digite a nova Data de Conclusão: ");
                    tarefas.setDataConclusao(sc.nextLine());
                    break;
                case 4:
                    System.out.println("Digite o novo Status: ");
                    tarefas.setStatus(sc.nextLine());
                    break;
                case 5:
                    System.out.println("Digite a nova Prioridade: ");
                    tarefas.setPrioridade(sc.nextLine());
                    break;
                default:
                    System.out.println("A opção escolhida não é válida");
            }
            
            //sc.close();

            tarefas.setId(id);
            tarefas.setLapide(true);
            byte[] ba = tarefas.toByteArray();
            fileReader.seek(posicao);

            int tamanhoTarefa = fileReader.readInt();
            if(ba.length <= tamanhoTarefa){
                fileReader.write(ba);
            }else{
                byte[] tarefaba = tarefas.toByteArray();              
                fileReader.writeBoolean(false);
                fileReader.seek(fileReader.length());
                fileReader.writeInt(tarefaba.length);
                fileReader.write(tarefaba);
            }
        }
        //System.out.println("Não foi possível encontrar Jogador, seu Jogador foi deletado ou não existe!! Favor verificar seus dados");
    }

    public void delete(int id) throws IOException{ //metodo para deletar conta
        fileReader.seek(0);
        fileReader.readInt();
        int tamanhoTarefa;
        boolean lapide;
        int idJogador;
        long posicaoLapide;

        try{
            while(fileReader.getFilePointer() < fileReader.length()){
                tamanhoTarefa = fileReader.readInt();
                posicaoLapide = fileReader.getFilePointer();
                lapide = fileReader.readBoolean();

                if(lapide){
                    idJogador = fileReader.readInt();
                    if(idJogador == id){
                        fileReader.seek(posicaoLapide);
                        fileReader.writeBoolean(false);
                        fileReader.skipBytes(4);
                        System.out.println("Jogador deletado: \n" + leTarefas(tamanhoTarefa,id,false));
                        break;
                    }else{
                        fileReader.skipBytes(tamanhoTarefa - 5);
                    }
                } else{
                    fileReader.skipBytes(tamanhoTarefa - 1);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }   
    }
}
