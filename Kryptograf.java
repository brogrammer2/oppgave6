import krypto.*;

public class Kryptograf implements Runnable {
  private KryptertMonitor kryptMonitor;
  private DekryptertMonitor dekryptMonitor;
  private Melding melding;

  public Kryptograf(KryptertMonitor kryptMonitor, DekryptertMonitor dekryptMonitor) {
    this.kryptMonitor = kryptMonitor;
    this.dekryptMonitor = dekryptMonitor;
  }

  /*Mens meldingsobjektet ikke er null saa hent meldingen fra den krypterte monitoren.
  Hvis meldingen ikke er null saa dekrypter den meldingen med dekrypteringsmetoden i class Melding.
  Deretter saa skal den dekrypterte meldingen puttes inn i den dekrypterte monitoren.*/
  public void run() {
    melding = new Melding(0, "test");
    while (melding != null) {
      kryptMonitor.laasMonitor();
      try {
        melding = kryptMonitor.hentMelding();
      } catch (InterruptedException e) {}
      finally {
        kryptMonitor.aapneMonitor();
      }
      if (melding != null) {
        melding.dekryptering();
      }
      dekryptMonitor.laasMonitor();
      try {
        dekryptMonitor.sendMelding(melding);
      }
      finally {
        dekryptMonitor.aapneMonitor();
      }
    }
  }
}
