import krypto.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class KryptertMonitor {
  private final Lock laas = new ReentrantLock();
  private final Condition aapneKanaler = laas.newCondition();
  private Koe<Melding> meldingsKoe = new Koe<Melding>();
  private int stengteKanaler = 0;
  private int antallTelegrafister;

  public KryptertMonitor(int antallTelegrafister) {
    this.antallTelegrafister = antallTelegrafister;
  }

  public void laasMonitor() {
    laas.lock();
  }

  public void aapneMonitor() {
    laas.unlock();
  }

  /*Metode for aa sette inn meldinger i monitoren. Hvis meldingens tekst er null,
  saa oek antall stengte kanaler ettersom at det betyr at en kanal ikke vil sende flere meldinger.
  Ellers saa er det en ekte melding og da kan man sette den inn i meldingskoen.
  Til slutt signaliserer den om at kanalen fremdeles er aapen.*/
  public void sendMelding(Melding melding) {
    if (melding.hentTekst() == null) {
      stengteKanaler++;
    } else {
      meldingsKoe.settInn(melding);
      aapneKanaler.signalAll();
    }
  }

  /*Metode for aa hente meldinger fra monitoren. Hvis listen er tom og stengte kanaler er mindre enn antall telegrafister,
  saa betyr det at det fremdeles er flere meldinger som kommer. Da maa man vente paa at nye meldinger skal komme inn.
   Hvis ikke stengteKanaler er mindre enn antallTelegrafister saa betyr det at alle kanalene er stengt og null returneres.
   Hvis den foerste if-sjekken ikke slaar til saa er det fremdeles flere meldinger aa hente, og da bare henter man en melding.*/
  public Melding hentMelding() throws InterruptedException {
    if (meldingsKoe.erTom()) {
      if (stengteKanaler < antallTelegrafister) {
        aapneKanaler.await();
        return meldingsKoe.fjern();
      } else {
        return null;
      }
    } else {
      return meldingsKoe.fjern();
    }
  }


}
