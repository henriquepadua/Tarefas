
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tarefas{
    private boolean lapide;
    private int id;
    private String Nome;
    private String Status;
    private String Prioridade;
    private String clubName;
    private Date DataCriacao;
    private Date DataConclusao;

    public Tarefas(){
        this.lapide = true;
        this.id = -1;
        this.Nome = "";
        this.Status = "";
        this.Prioridade = "";
    }

    public Tarefas(/*boolean lapide,*/ int id, String Nome, String fullName, byte overall, double value, String Status, String Prioridade, byte age, String clubName, String DataCriacao,String DataConclusao) throws Exception{
        //this.lapide = lapide;
        this.id = id;
        this.Nome = Nome;
        this.Status = Status;
        this.Prioridade = Prioridade;
        this.clubName = clubName;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.Nome;
    }

    public void setNome(String Nome){
        this.Nome = Nome;
    }

    public String getStatus(){
        return this.Status;
    }

    public void setStatus(String Status){
        this.Status = Status;
    }

    public String getPrioridade(){
        return this.Prioridade;
    }

    public void setPrioridade(String Prioridade){
        this.Prioridade = Prioridade;
    }

    public String getClubName(){
        return this.clubName;
    }

    public void setClubName(String clubName){
        this.clubName = clubName;
    }

    public boolean getLapide(){
        return this.lapide;
    }

    public void setLapide(boolean lapide){
        this.lapide = lapide;
    }

    public Date getDataCriacao(){
        return DataCriacao;
    }

    public void setDataCriacao(String DataCriacao) throws Exception{
        Date date = stringToDate(DataCriacao);
        this.DataCriacao = date;
    }

    public Date getDataConclusao(){
        return DataCriacao;
    }

    public void setDataConclusao(String DataConclusao) throws Exception{
        Date date = stringToDate(DataConclusao);
        this.DataConclusao = date;
    }

    public Date stringToDate(String data) throws Exception{
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        date = sdf.parse(data);
        return date;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        String string = sdf.format(date);
        return string;
    }

    public byte[] toByteArray() throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        dos.writeBoolean(lapide);
        dos.writeInt(id);
        dos.writeInt(Nome.length());
        dos.writeUTF(Nome);
        dos.writeInt(dateToString(DataCriacao).length());
        dos.writeUTF(dateToString(DataCriacao));
        dos.writeInt(dateToString(DataConclusao).length());
        dos.writeUTF(dateToString(DataConclusao));
        dos.writeInt(Status.length());
        dos.writeUTF(Status);
        dos.writeInt(Prioridade.length());
        dos.writeUTF(Prioridade);

        return out.toByteArray();
    }
}