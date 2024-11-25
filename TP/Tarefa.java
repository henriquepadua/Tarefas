import java.time.LocalDate;
import java.util.ArrayList;

import aed3.Registro;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class Tarefa implements Registro {

    public int id;
    public String nome;
    public LocalDate dataCriacao;
    public LocalDate dataConclusao;
    public String status;
    public String prioridade;
    public int idCategoria;
    public ArrayList<Integer> idRotulo;

    public Tarefa() {
        this(-1, "", LocalDate.now(), LocalDate.now(), "", "", -1, new ArrayList<Integer>(0));
    }

    public Tarefa(String nome, LocalDate dataCriacao, LocalDate dataConclusao, String status, String prioridade,
            int idCategoria, ArrayList<Integer> idRotulo) {
        this(-1, nome, dataCriacao, dataConclusao, status, prioridade, idCategoria, idRotulo);
    }

    public Tarefa(int id, String nome, LocalDate dataCriacao, LocalDate dataConclusao, String status, String prioridade,
            int idCategoria, ArrayList<Integer> idRotulo) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.prioridade = prioridade;
        this.idCategoria = idCategoria;
        this.idRotulo = idRotulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String resposta = "Tarefa :\n" +
                "id             = " + id + "\n" +
                "nome           = " + nome + "\n" +
                "dataCriacao    = " + dataCriacao + "\n" +
                "dataConclusao  = " + dataConclusao + "\n" +
                "status         = " + status + "\n" +
                "prioridade     = " + prioridade + "\n" +
                "idCategoria    = " + idCategoria + "\n" +
                "idRotulos      = ";
        for (int i = 0; i < idRotulo.size(); i++) {
            resposta += (idRotulo.get(i) + " ");
        }
        resposta += "\n";
        return resposta;
    }

    public String toString( ArquivoRotulo AR, ArquivoCategoria AC ) {
        String resposta = "Tarefa :\n" +
                "id             = " + id + "\n" +
                "nome           = " + nome + "\n" +
                "dataCriacao    = " + dataCriacao + "\n" +
                "dataConclusao  = " + dataConclusao + "\n" +
                "status         = " + status + "\n" +
                "prioridade     = " + prioridade + "\n" +
                "idCategoria    = " + idCategoria + "\n" +
                "idRotulos      = " + AR.StringRotulos(id) + "\n";
        return resposta;
    }

    /*
     * public String toString( ArvoreBMais<ParNomeId> arvoreCT) {
     * return "Tarefa :\n" +
     * "id             = " + id + "\n" +
     * "nome           = " + nome + "\n" +
     * "dataCriacao    = " + dataCriacao + "\n" +
     * "dataConclusao  = " + dataConclusao + "\n" +
     * "status         = " + status + "\n" +
     * "prioridade     = " + prioridade + "\n" +
     * "idCategoria    = " + arvoreCT.read(new ParNomeId(idCategoria)) + "\n";
     * }
     */

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        dos.writeLong(this.dataCriacao.toEpochDay());
        dos.writeLong(this.dataConclusao.toEpochDay());
        dos.writeUTF(this.status);
        dos.writeUTF(this.prioridade);
        dos.writeInt(this.idCategoria);
        dos.writeInt(this.idRotulo.size());
        for (int i = 0; i < idRotulo.size(); i++) {
            dos.writeInt(this.idRotulo.get(i));
        }
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);
        int qttRotulos = 0;

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.dataCriacao = LocalDate.ofEpochDay(dis.readLong());
        this.dataConclusao = LocalDate.ofEpochDay(dis.readLong());
        this.status = dis.readUTF();
        this.prioridade = dis.readUTF();
        this.idCategoria = dis.readInt();
        qttRotulos = dis.readInt();
        for (int i = 0; i < qttRotulos; i++) {
            this.idRotulo.add(dis.readInt());
        }
    }

    public boolean existIdRotulo(int idRotulo) {
        boolean resposta = false;

        for (int i = 0; i < this.idRotulo.size(); i++) {
            if (this.idRotulo.get(i) == idRotulo)
                resposta = true;
        }

        return resposta;
    }
}