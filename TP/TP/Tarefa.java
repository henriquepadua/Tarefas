import java.time.LocalDate;
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

    public Tarefa() {
        this(-1, "", LocalDate.now(), LocalDate.now(), "", "", -1);
    }

    public Tarefa(String nome, LocalDate dataCriacao, LocalDate dataConclusao, String status, String prioridade,
            int idCategoria) {
        this(-1, nome, dataCriacao, dataConclusao, status, prioridade, idCategoria);
    }

    public Tarefa(int id, String nome, LocalDate dataCriacao, LocalDate dataConclusao, String status, String prioridade,
            int idCategoria) {
        this.id = id;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.status = status;
        this.prioridade = prioridade;
        this.idCategoria = idCategoria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Tarefa :\n" +
                "id             = " + id + "\n"+
                "nome           = " + nome + "\n"+
                "dataCriacao    = " + dataCriacao + "\n"+
                "dataConclusao  = " + dataConclusao + "\n"+
                "status         = " + status + "\n"+
                "prioridade     = " + prioridade +  "\n"+
                "idCategoria    = " + idCategoria + "\n";
    }

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
        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.dataCriacao = LocalDate.ofEpochDay(dis.readLong());
        this.dataConclusao = LocalDate.ofEpochDay(dis.readLong());
        this.status = dis.readUTF();
        this.prioridade = dis.readUTF();
        this.idCategoria = dis.readInt();
    }
}