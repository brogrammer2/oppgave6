import krypto.*;

public class Telegrafist implements Runnable {
  private Kanal kanal;
  private KryptertMonitor kryptMonitor;

  public Telegrafist(Kanal kanal, KryptertMonitor kryptMonitor){
    this.kanal = kanal;
    this.kryptMonitor = kryptMonitor;
  }

  /*mens tekst ikke er null saa skal vi lage meldinger
  og sette dem inn i monitoren. Vi bruker en laas i monitoren
  for at en og en traad skal faa gjort sin arbeidsoppgave i monitoren.
  Naar en traad har gjort sin arbeidsoppgave saa laaser den opp laasen igjen
  for de andre traadene.*/
  public void run() {
    String tekst = " ";
    while (tekst != null) {
      kryptMonitor.laasMonitor();
      try {
        tekst = kanal.lytt();
        kryptMonitor.sendMelding(new Melding(kanal.hentId(), tekst));
      } finally {
        kryptMonitor.aapneMonitor();
      }
    }
  }
}
