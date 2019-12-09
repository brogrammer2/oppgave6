import krypto.*;
import java.util.*;
import java.io.*;

public class Operasjonsleder implements Runnable {
  private DekryptertMonitor dekryptMonitor;
  private int antallTelegrafister;
  private ArrayList<ArrayList<Melding>> kanaler = new ArrayList<ArrayList<Melding>>();

  public Operasjonsleder(DekryptertMonitor dekryptMonitor, int antallTelegrafister) {
    this.dekryptMonitor = dekryptMonitor;
    this.antallTelegrafister = antallTelegrafister;
  }

  /*For alle de tre kanalene saa legger vi til de tilsvarende meldingene.
  Siden den ordnede lenkelisten har sortert meldingene etter sekvensnummer
  automatisk saa er det bare aa legge de til i arraylist kanaler. Deretter
  saa printer operasjonslederen til fil gjennom printTilFil-metoden.*/
  public void run() {
    int i = 1;
    while (kanaler.size() < antallTelegrafister) {
      dekryptMonitor.laasMonitor();
      try {
        kanaler.add(dekryptMonitor.hentMeldinger(i));
        i++;
      }
      finally {
        dekryptMonitor.aapneMonitor();
      }
      try {
        this.printTilFil();
      }
      catch (Exception e) {}
    }
  }

  /*metode som printer meldingene til fil. For hver kanal lages
  det en printwriter som deretter printer meldingene til fil.*/
  private void printTilFil() throws Exception {
    PrintWriter pw;
    for (ArrayList<Melding> al : kanaler){
      pw = new PrintWriter("fil"+Integer.toString(kanaler.indexOf(al)), "utf-8");
      for (Melding m : al){
        pw.println(m.hentTekst() + "\n");
      }
      pw.close();
    }
  }
}
