import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aed3.Registro;

public class Rotulo implements Registro {

    /** ATRIBUTOS */
    private int id;         // id do Rótulo
    private String nome;  // Rótulo

    /** Construtor sem parâmetros */
    public Rotulo() {
        
    }

    public Rotulo( String nome ) {
        this.nome = nome;
    }

    public Rotulo( int id, String nome ) {
        this.id     = id;
        this.nome = nome;
    }

    /** Define um id 'i' pro nome */
    public void setId(int i) {
        id = i;
    }

    /** Retorna o id do nome */
    public int getId(){
        return id;
    }

    /** Define um id 'i' pro nome */
    public void set(String i) {
        nome = i;
    }

    /** Retorna o id do nome */
    public String get(){
        return nome;
    }

    /** Parser para String */
    public String toString() {
        return this.id + " " + this.nome;
    }

    /** @return Um array de bytes do objeto em questão */
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.id);
        dos.writeUTF(this.nome);
        return baos.toByteArray();
    }
    
    /** Preenche o objeto de acordo com um array de bytes lido
     * @param byteArray 'byte[ ]' Array de bytes
     */
    public void fromByteArray(byte[] byteArray) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        DataInputStream dis = new DataInputStream(bais);
        this.id = dis.readInt();
        this.nome = dis.readUTF();
    }

    public void print (  ) {
        System.out.println( id + " - " + nome );
    }

    public void printId (  ) {
        System.out.print( id );
    }

    public void printNome (  ) {
        System.out.print( nome );
    }
}
