package Relacoes;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RotuloTarefa implements aed3.RegistroArvoreBMais<RotuloTarefa>, Comparable<RotuloTarefa> {

  public int idRotulo;
  public int idTarefa;
  private static final short TAMANHO = 8;

  public RotuloTarefa() {
    this(-1, -1);
  }

  public RotuloTarefa(int n1) {
    this(n1, -1);
  }

  public RotuloTarefa(int n1, int n2) {
    this.idRotulo = n1;
    this.idTarefa = n2;
  }

  @Override
  public RotuloTarefa clone() {
    return new RotuloTarefa(this.idRotulo, this.idTarefa);
  }

  public short size() {
    return TAMANHO;
  }

  @Override
  public int compareTo(RotuloTarefa a) {
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
    dos.writeInt(this.idRotulo);
    dos.writeInt(this.idTarefa);
    return baos.toByteArray();
  }

  @Override
  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    this.idRotulo = dis.readInt();
    this.idTarefa = dis.readInt();
  }

}