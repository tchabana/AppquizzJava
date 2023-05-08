import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Collections;

public class PageJeu extends JFrame implements ActionListener {
    public static String pays;
    static int SCORE, DEFETE = 0;
    public static HashMap<String, String> paysCapital = new HashMap<String, String>();
    //
    JPanel pane = (JPanel) getContentPane();
    ButtonGroup group = new ButtonGroup();
    //
    JPanel paneN = new JPanel();
    JLabel labelScore = new JLabel("SCORE:");
    JLabel score = new JLabel("0");
    JLabel labelDefaite = new JLabel("DEFAITE:");
    JLabel defete = new JLabel("0");

    //
    JLabel question = new JLabel();
    JPanel paneC = new JPanel();
    JRadioButton p1 = new JRadioButton();
    JRadioButton p2 = new JRadioButton();
    JRadioButton p3 = new JRadioButton();
    JRadioButton p4 = new JRadioButton();
    //
    JPanel paneS = new JPanel();
    JButton btnretry = new JButton("recomencer");
    JButton valider = new JButton("Valider");

    public PageJeu() {
        super("TCH-QUIZZ");
        this.initialisation();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.proprietePageJeu();
        this.setSize(600, 700);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public static HashMap<String, String> hashDePaysCapitale() {
        Path source = Paths.get("Liste-des-pays.csv");
        try {
            List<String> listeLines = Files.readAllLines(source);
            for (String elt : listeLines) {
                String[] eltTableau = elt.split(",");
                paysCapital.put(eltTableau[0], eltTableau[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paysCapital;
    }

    public ArrayList<String> capitaleParasite(HashMap<String, String> pays) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int i_hazard = (int) (pays.size() * Math.random());
            Object[] contrys = pays.values().toArray();
            result.add((String) contrys[i_hazard]);
        }
        return result;
    }

    public static String genareteCountry(HashMap<String, String> pays) {
        int i_hazard = (int) (pays.size() * Math.random());
        Object[] contrys = pays.keySet().toArray();
        Object contry = contrys[i_hazard];
        return (String) contry;
    }

    public void initialisation() {
        paysCapital = hashDePaysCapitale();
        pays = genareteCountry(paysCapital);
        String qst = "Quelle est la capitale de ce Pays: " + pays;
        System.out.println(paysCapital.get(pays));
        this.question.setText(qst);
        ArrayList<String> fauxCapitale = this.capitaleParasite(paysCapital);
        fauxCapitale.add(paysCapital.get(pays));
        Collections.shuffle(fauxCapitale);
        this.p1.setText(fauxCapitale.get(0));
        this.p2.setText(fauxCapitale.get(1));
        this.p3.setText(fauxCapitale.get(2));
        this.p4.setText(fauxCapitale.get(3));
        this.p1.setActionCommand(fauxCapitale.get(0));
        this.p2.setActionCommand(fauxCapitale.get(1));
        this.p3.setActionCommand(fauxCapitale.get(2));
        this.p4.setActionCommand(fauxCapitale.get(3));
    }

    public void proprietePageJeu() {
        this.labelScore.setFont(new Font("Arial", Font.BOLD, 20));
        this.score.setFont(new Font("Arial", Font.BOLD, 20));
        this.labelDefaite.setFont(new Font("Arial", Font.BOLD, 20));
        this.defete.setFont(new Font("Arial", Font.BOLD, 20));
        this.question.setFont(new Font("Arial", Font.BOLD, 16));
        //
        paneN.setLayout(new GridLayout(2, 2));
        paneN.add(labelScore);
        paneN.add(score);
        paneN.add(labelDefaite);
        paneN.add(defete);
        //
        paneC.setLayout(new GridLayout(6, 1));
        paneC.add(question);
        //
        paneC.add(p1);
        p1.addActionListener(this);
        paneC.add(p2);
        p2.addActionListener(this);
        paneC.add(p3);
        p3.addActionListener(this);
        paneC.add(p4);
        p4.addActionListener(this);
        group.add(p1);
        group.add(p2);
        group.add(p3);
        group.add(p4);
        //
        valider.addActionListener(this);
        //
        btnretry.addActionListener(this);
        //
        paneS.add(valider);
        paneS.add(btnretry);
        //
        pane.setLayout(new BorderLayout());
        pane.add(paneN, BorderLayout.NORTH);
        pane.add(paneC, BorderLayout.CENTER);
        pane.add(paneS, BorderLayout.SOUTH);
    }

    public static Boolean verfyAnswer(String reponse, String solution) {
        reponse = String.format("%s", reponse);
        Boolean r = reponse.equals(solution) || solution.equals(reponse);
        System.out.println(reponse + "  " + solution + " " + r);
        return r;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == valider) {
            if (p1.isSelected() == false && p2.isSelected() == false && p3.isSelected() == false
                    && p4.isSelected() == false) {
                JOptionPane.showMessageDialog(null, "Vous devez choisire une preposition");
            } else {
                ButtonModel b = group.getSelection();
                if (verfyAnswer(b.getActionCommand(), paysCapital.get(pays))) {
                    SCORE++;
                } else {
                    DEFETE++;
                }
            }
            this.score.setText(String.valueOf(SCORE));
            this.defete.setText(String.valueOf(DEFETE));
            if (DEFETE == 5) {
                JOptionPane.showMessageDialog(null, "Vous avez predu (5 mavaise reponses)");
                new PageJeu();
            }
            initialisation();
            group.clearSelection();
        } else if (arg0.getSource() == btnretry) {
            dispose();
            new PageJeu();
            SCORE = 0;
            DEFETE = 0;
        }
    }
}
