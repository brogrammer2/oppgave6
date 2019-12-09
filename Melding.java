import krypto.*;

public class Melding implements Comparable<Melding> {
  private int kanalID;
  private String tekst;
  private static int teller = 0;
  private int sekvensnummer;

  public Melding(int kanalID, String tekst) {
    teller++;
    this.tekst = tekst;
    this.kanalID = kanalID;
    this.sekvensnummer = teller;
  }

  //metode for aa dekryptere meldinger.
  public void dekryptering() {
    this.tekst = Kryptografi.dekrypter(this.tekst);
  }

  public String hentTekst() {
    return tekst;
  }

  public int hentKanalID() {
    return kanalID;
  }

  public int hentSekvensnummer() {
    return sekvensnummer;
  }

  @Override
  public int compareTo(Melding annenMelding) {
    return this.sekvensnummer-annenMelding.sekvensnummer;
  }
}
