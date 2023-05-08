import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class App {
    public static HashMap<String, String> pays = new HashMap<String, String>();
    static Scanner sc = new Scanner(System.in);
    static int score, defete;

    public static HashMap<String, String> hashDePaysCapitale() {
        Path source = Paths.get("Liste-des-pays.csv");
        try {
            List<String> listeLines = Files.readAllLines(source);
            for (String elt : listeLines) {
                String[] eltTableau = elt.split(",");
                App.pays.put(eltTableau[0], eltTableau[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return App.pays;
    }

    public static String genareteCountry(HashMap<String, String> pays) {
        int i_hazard = (int) (pays.size() * Math.random());
        Object[] contrys = pays.keySet().toArray();
        Object contry = contrys[i_hazard];
        return (String) contry;
    }

    public static String askQuestion(String pays) {
        System.out.print("Donner la capitale de " + pays + ": ");
        return sc.nextLine();
    }

    public static Boolean verfyAnswer(String reponse, String solution) {
        reponse = String.format("\"%s\"", reponse);
        return reponse.equalsIgnoreCase(solution);
    }

    public static void main(String[] args) {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│🧠️ BIENVENUE DANS QUESTION POUR CHAMPION    🧠️  │");
        System.out.println("└────────────────────────────────────────────────┘");
        HashMap<String, String> paysCapital = App.hashDePaysCapitale();
        while (true) {
            System.out.println("┌───SCORES────┐   ┌───RATTER────┐");
            System.out.printf("|      %d      |   |      %d      |\n", App.score, App.defete);
            System.out.println("└─────────────┘   └─────────────┘");
            String paysGenerer = App.genareteCountry(paysCapital);
            String reponse = App.askQuestion(paysGenerer);
            Boolean continuation = App.verfyAnswer(reponse, (String) App.pays.get(paysGenerer));
            if (App.defete == 3) {
                System.out.println("💥️💥️💥️VOUS AVEZ PERDU  (3 Mauvaise reponse)💥️💥️💥️");
                break;
            } else if (continuation.equals(true)) {
                App.score++;
                System.out.println("💵️💵️REPONSE CORRECTE💵️💵️");
                continue;
            } else if (continuation.equals(false)) {
                System.out.println("🚫️🚫️REPONSE INCORRECTE🚫️🚫️");
                System.out.println("🤖️LA BONNE REPONSE EST:" + App.pays.get(paysGenerer));
                App.defete++;
                continue;
            }
        }

    }
}
