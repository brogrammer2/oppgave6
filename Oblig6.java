import krypto.*;

class Oblig6 {
  private static final int ANTALL_TELEGRAFISTER = 3;
  private static final int ANTALL_KRYPTOGRAFER = 5;

  public static void main(String[] args) {
    Operasjonssentral ops = new Operasjonssentral(ANTALL_TELEGRAFISTER); //oppretter en operasjonssentral med like mange kanaler som AT
    Kanal[] kanaler = ops.hentKanalArray(); //henter kanalene
    KryptertMonitor kryptMonitor = new KryptertMonitor(ANTALL_TELEGRAFISTER); //sender med antall telegrafister
    DekryptertMonitor dekryptMonitor = new DekryptertMonitor(ANTALL_KRYPTOGRAFER); //sender med antall kryptografer

    //starter opp telegrafistene
    int i = 0;
    while (i < ANTALL_TELEGRAFISTER) {
      Runnable telegrafist = new Telegrafist(kanaler[i], kryptMonitor);
      new Thread(telegrafist).start();
      i++;
    }

    //starter opp kryptografene
    int j = 0;
    while (j < ANTALL_KRYPTOGRAFER) {
      Runnable kryptograf = new Kryptograf(kryptMonitor, dekryptMonitor);
      new Thread(kryptograf).start();
      j++;
    }

    //starter opp operasjonslederen
    Runnable operasjonsleder = new Operasjonsleder(dekryptMonitor, ANTALL_TELEGRAFISTER);
    new Thread(operasjonsleder).start();

  }
}
