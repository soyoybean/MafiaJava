import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MafiaGUI extends JFrame {
    private DefaultListModel model;
    private JList list;
    private JTextField inputText;

    public MafiaGUI() {
        setTitle("Mafia");
        model = new DefaultListModel();
        inputText = new JTextField("Type something and Hit Enter");
        inputText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                inputText.setText("");
            }
        });
        list = new JList(model);
        list.setBackground(Color.white);
        inputText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                model.addElement(inputText.getText());
                JOptionPane.showMessageDialog(null, inputText.getText());
                inputText.setText("Type something and Hit Enter");
            }
        });
        add(inputText,BorderLayout.NORTH);
        add(new JScrollPane(list),BorderLayout.CENTER);
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public static void main(String[] args) {
        new MafiaGUI();
    }
}