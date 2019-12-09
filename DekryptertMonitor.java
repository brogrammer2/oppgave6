import krypto.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class DekryptertMonitor {
  private final Lock laas = new ReentrantLock();
  private final Condition kryptografLaas = laas.newCondition();
  private OrdnetLenkeliste<Melding> meldinger = new OrdnetLenkeliste<Melding>(); //den ordnede lenkelisten soerger for at meldingene blir sortert etter seknr
  private int antallKryptografer;
  private int ferdigeKryptografer;

  public DekryptertMonitor(int antallKryptografer) {
    this.antallKryptografer = antallKryptografer;
  }

  public void laasMonitor() {
    laas.lock();
  }

  public void aapneMonitor() {
    laas.unlock();
  }

  /*Hvis meldingen ikke er det samme som null, saa sett inn meldingen i listen.
  Ellers, hvis melding er null, saa oek antallet ferdige kryptografer. Hvis alle
  kryptografene er ferdige, saa skal det signaliseres om at alle kryptografene er
  ferdige og at det ikke er mer aa vente paa.*/
  public void sendMelding(Melding melding) {
  if (melding != null) {
    meldinger.settInn(melding);
    } else {
    ferdigeKryptografer++;
    if (ferdigeKryptografer == antallKryptografer) {
      kryptografLaas.signalAll();
      }
    }
  }

  /*Hvis det er mindre ferdige kryptografer enn antall kryptografer, saa betyr
  det at det fremdeles er mer arbeid som maa gjoeres. Da maa det ventes.
  Videre i metoden saa loeper vi gjennom den ordnede lenkelisten med meldinger og hvis meldingen
  vi har har samme kanalID som den kanalID'en operasjonslederen sendte med, saa skal den meldingen
  legges til i den tilsvarende kanalen.*/
  public ArrayList<Melding> hentMeldinger(int kanalID) {
    ArrayList<Melding> sorterteMeldinger = new ArrayList<Melding>();
    if (ferdigeKryptografer < antallKryptografer) {
      try {
        kryptografLaas.await();
      }
      catch(InterruptedException e) {}
    }
    for (Melding m : meldinger) {
      if (m.hentKanalID() == kanalID){
        sorterteMeldinger.add(m);
        kryptografLaas.signalAll();
      }
    }
    return sorterteMeldinger;
  }


}
