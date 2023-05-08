import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PageAceuill extends JFrame implements ActionListener {
    JLabel label = new JLabel("BIENVENU SUR TCH-QUIZZ");
    JButton btnStart = new JButton("Demmare le Quizz");
    JPanel pane = (JPanel) getContentPane();

    public PageAceuill() {
        super("TCH-QUIZZ");
        this.proprietePageAceuill();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(600, 700);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void proprietePageAceuill() {
        this.pane.setLayout(null);
        this.label.setBounds(180, 100, 300, 200);
        this.label.setFont(new Font("Arial", Font.BOLD, 20));
        this.btnStart.setBounds(220, 250, 200, 50);
        this.btnStart.addActionListener(this);
        this.pane.add(label);
        this.pane.add(btnStart);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            dispose();
            new PageJeu();
        }
    }

    public static void main(String[] args) {
        new PageAceuill();
    }

}
