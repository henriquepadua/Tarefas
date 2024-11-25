import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TarefaRotulo implements aed3.RegistroArvoreBMais<TarefaRotulo>, Comparable<TarefaRotulo> {

    public int idTarefa;
    public int idRotulo;
    private static final short TAMANHO = 8;

    public TarefaRotulo() {
        this(-1, -1);
    }

    public TarefaRotulo(int n1) {
        this(n1, -1);
    }

    public TarefaRotulo(int n1, int n2) {
        this.idTarefa = n1;
        this.idRotulo = n2;
    }

    @Override
    public TarefaRotulo clone() {
        return new TarefaRotulo( this.idTarefa, this.idRotulo );
    }

    public short size() {
        return TAMANHO;
    }

    @Override
    public int compareTo(TarefaRotulo a) {
        if (this.idRotulo != a.idRotulo)
            return Integer.compare(this.idRotulo, a.idRotulo);
        else
            return this.idTarefa == -1 ? 0 : Integer.compare(this.idTarefa, a.idTarefa);
    }

    @Override
    public String toString() {
        return String.format("%3d", this.idRotulo) + ";" + String.format("%-3d", this.idTarefa);
    }

    @Override
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(this.idTarefa);
        dos.writeInt(this.idRotulo);
        return baos.toByteArray();
    }

    @Override
    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        this.idTarefa = dis.readInt();
        this.idRotulo = dis.readInt();
    }

}