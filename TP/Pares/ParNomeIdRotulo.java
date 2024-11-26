package Pares;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ParNomeIdRotulo implements aed3.RegistroArvoreBMais<ParNomeIdRotulo> {

  public String nomeRotulo;
  public int idRotulo;
  private short TAMANHO = 30;

  public ParNomeIdRotulo() throws Exception {
    this("", -1);
  }

  public ParNomeIdRotulo(String n) throws Exception {
    this(n, -1);
  }

  public ParNomeIdRotulo(String n, int i) throws Exception {
    if (n.getBytes().length > 26)
      throw new Exception("Nome extenso demais. Diminua o número de caracteres.");
    this.nomeRotulo = n; // ID do Usuário
    this.idRotulo = i; // ID da Pergunta
  }

  @Override
  public ParNomeIdRotulo clone() {
    try {
      return new ParNomeIdRotulo(this.nomeRotulo, this.idRotulo);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public short size() {
    return this.TAMANHO;
  }

  public int compareTo(ParNomeIdRotulo a) {
    return transforma(this.nomeRotulo).compareTo(transforma(a.nomeRotulo));
  }

  public static String transforma(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
  }

  public String toString() {
    return this.nomeRotulo + ";" + String.format("%-3d", this.idRotulo);
  }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    byte[] vb = new byte[26];
    byte[] vbNome = this.nomeRotulo.getBytes();
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
    dos.writeInt(this.idRotulo);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    byte[] vb = new byte[26];
    dis.read(vb);
    this.nomeRotulo = (new String(vb)).trim();
    this.idRotulo = dis.readInt();
  }
}
