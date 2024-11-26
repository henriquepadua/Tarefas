package Pares;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ParNomeIdTarefa implements aed3.RegistroArvoreBMais<ParNomeIdTarefa> {

  public String nomeTarefa;
  public int idTarefa;
  private short TAMANHO = 30;

  public ParNomeIdTarefa() throws Exception {
    this("", -1);
  }

  public ParNomeIdTarefa(String n) throws Exception {
    this(n, -1);
  }

  public ParNomeIdTarefa(String n, int i) throws Exception {
    if (n.getBytes().length > 26)
      throw new Exception("Nome extenso demais. Diminua o número de caracteres.");
    this.nomeTarefa = n; // ID do Usuário
    this.idTarefa = i; // ID da Pergunta
  }

  @Override
  public ParNomeIdTarefa clone() {
    try {
      return new ParNomeIdTarefa(this.nomeTarefa, this.idTarefa);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParNomeIdTarefa a) {
    return transforma(this.nomeTarefa).compareTo(transforma(a.nomeTarefa));
  }

  public static String transforma(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
  }

  public String toString() {
    return this.nomeTarefa + ";" + String.format("%-3d", this.idTarefa);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    byte[] vb = new byte[26];
    byte[] vbNome = this.nomeTarefa.getBytes();
    int i = 0;
    while (i < vbNome.length) {
      vb[i] = vbNome[i];
      i++;
    }
    while (i < 26) {
      vb[i] = ' ';
      i++;
    }
    dos.write(vb);
    dos.writeInt(this.idTarefa);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    byte[] vb = new byte[26];
    dis.read(vb);
    this.nomeTarefa = (new String(vb)).trim();
    this.idTarefa = dis.readInt();
  }
}
