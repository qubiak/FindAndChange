package findandchange;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FindAndChange extends JFrame {

    public FindAndChange() {

        initComponents();
    }

    public void initComponents() {

        this.setTitle("Przeszukaj i zamień");
        this.setBounds(500, 500, 500, 300);
        Image iconImage = Toolkit.getDefaultToolkit().getImage("images.png");
        this.setIconImage(iconImage);
        this.setJMenuBar(Menu);

        JMenu menuFile = Menu.add(new JMenu("Plik"));
        JMenu menuFile2 = Menu.add(new JMenu("Zapisz/Wczytaj"));
        JMenu menuFile3 = Menu.add(new JMenu("Wyjście"));

        Action actionFind = new Action("Znajdź", "Szukaj", "ctrl F", new ImageIcon("szukaj.jpg"));
        Action actionChange = new Action("Zamień", "Zamień", "ctrl C", new ImageIcon("zamien.png"));
        Action actionCount = new Action("Policz ile jest znaków i spacji", "Licz", "ctrl L", new ImageIcon("licz.jpg"));
        Action actionExit = new Action("Zakończ program", "Wyjście", "ctrl E", new ImageIcon("exit.png"));
        Action actionSave = new Action("Zapisz tekst do pliku", "Zapisz", "ctrl S", new ImageIcon("zapisz.jpg"));
        Action actionRead = new Action("Wczytaj plik", "Wczytaj", "ctrl R", new ImageIcon("wczytaj.png"));

        menuFile.add(new JMenuItem(actionFind));
        menuFile.add(new JMenuItem(actionChange));
        menuFile.add(new JMenuItem(actionCount));

        menuFile2.add(new JMenuItem(actionSave));
        menuFile2.add(new JMenuItem(actionRead));

        menuFile3.add(new JMenuItem(actionExit));

        find = new JButton(actionFind);
        change = new JButton(actionChange);
        count = new JButton(actionCount);

        save = new JButton(actionSave);
        read = new JButton(actionRead);

        exit = new JButton(actionExit);

        textArea.setBorder(BorderFactory.createEtchedBorder());
        findArea.setBorder(BorderFactory.createEtchedBorder());
        newText.setBorder(BorderFactory.createEtchedBorder());
        textArea.setLineWrap(true);

        textArea.setToolTipText("Tutaj wklej tekst który będziemy przszukiwać");
        findArea.setToolTipText("Wpisz tekst, którego będzimy szukać");
        newText.setToolTipText("Wpisz to na co będziemy zamieniać");

        GroupLayout layout = new GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup()
                                        .addComponent(textArea, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(
                                                layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup()
                                                                .addComponent(find, 150, 150, 150).addComponent(count, 150, 150, 150))
                                                        .addGroup(layout.createParallelGroup()
                                                                .addComponent(findArea, 50, 150, 200))
                                                        .addComponent(label1)
                                                        .addComponent(change, 150, 150, 150)
                                                        .addComponent(label2)
                                                        .addComponent(newText, 50, 150, 200)
                                                        .addGap(150, 150, 150)
                                        )));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(textArea, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(find, 30, 30, 30)
                                .addComponent(findArea, 10, 30, 30)
                                .addComponent(label1, 30, 30, 30)
                                .addComponent(change, 30, 30, 30)
                                .addComponent(label2, 30, 30, 30)
                                .addComponent(newText, 10, 30, 30))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(count, 30, 30, 30)
                        ));

        this.pack();
        this.setDefaultCloseOperation(3);
    }

    public static void main(String[] args) {

        new FindAndChange().setVisible(true);
    }

    private JTextArea textArea = new JTextArea("Wklej tutaj tekst który będziemy przeszukiwać.", 7, 10);
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private JButton find;
    private JLabel label1 = new JLabel("i");
    private JButton change;
    private JLabel label2 = new JLabel("na");
    private JTextField newText = new JTextField(6);
    private JTextField findArea = new JTextField(6);
    private JButton count;
    private JMenuBar Menu = new JMenuBar();
    private JButton exit;
    private JButton save;
    private JButton read;

    private class Action extends AbstractAction {

        public Action(String description, String name, String VK, Icon icon) {
            this.putValue(Action.SHORT_DESCRIPTION, description);
            this.putValue(Action.NAME, name);
            this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(VK));
            this.putValue(Action.SMALL_ICON, icon);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Szukaj")) {
                findWord();
            } else if (e.getActionCommand().equals("Zamień")) {
                changeWord();
            } else if (e.getActionCommand().equals("Licz")) {
                countCharacters();
            } else if (e.getActionCommand().equals("Wyjście")) {
                exit();
            } else if (e.getActionCommand().equals("Zapisz")) {
                saveText();
            } else if (e.getActionCommand().equals("Wczytaj")) {
                readFile();
            }

        }

        private void findWord() {
            startOfSearched = textArea.getText().toLowerCase().indexOf(findArea.getText().toLowerCase(), startOfSearched + findArea.getText().length());

            if (startOfSearched == -1) {
                startOfSearched = textArea.getText().indexOf(findArea.getText());
            }

            if (startOfSearched >= 0 && findArea.getText().length() > 0) {
                textArea.requestFocus();
                textArea.select(startOfSearched, startOfSearched + findArea.getText().length());

            }
        }

        private int startOfSearched = 0;

        private void changeWord() {
            if (textArea.getSelectionStart() != textArea.getSelectionEnd()) {
                changeTekst();

            } else {
                find.doClick();
                if (textArea.getSelectionStart() != textArea.getSelectionEnd()) ;
                changeTekst();
            }

        }

        private void changeTekst() {
            textArea.requestFocus();
            int opcja = JOptionPane.showConfirmDialog(rootPane, "Czy chcesz zamienić \'" + findArea.getText() + "\' na \'" + newText.getText() + "\'", "UWAGA", JOptionPane.YES_NO_OPTION);
            if (opcja == 0) {
                textArea.replaceRange(newText.getText(), textArea.getSelectionStart(), textArea.getSelectionEnd());

            }
        }

        private void countCharacters() {
            int countSpace = 0;
            for (int i = 0; i < textArea.getText().length(); i++) {
                if (textArea.getText().charAt(i) == ' ') {
                    countSpace++;
                }
            }

            JOptionPane.showMessageDialog(rootPane, "W tekście jest znajdują się komponenty w ilości: Znaki: " + (textArea.getText().length() - countSpace) + "; " + "Spacje: " + countSpace);
        }

        private void exit() {
            System.exit(3);
        }

        private void saveText() {

            JFileChooser saveWindow = new JFileChooser();
            saveWindow.setCurrentDirectory(new File(System.getProperty("user.dir")));
            saveWindow.setSelectedFile(new File(System.getProperty("user.dir") + File.separator + "mojanazwa.txt"));
            FileNameExtensionFilter saveFilter = new FileNameExtensionFilter("Pliki tekstowe *.txt", "txt");
            saveWindow.setFileFilter(saveFilter);
            int scoreSave = saveWindow.showSaveDialog(textArea);
            File saveFile = saveWindow.getSelectedFile();
            boolean lineWrap = textArea.getLineWrap();
            textArea.setLineWrap(false);
            String text = textArea.getText();

            String saveAdress = String.valueOf(saveFile).replace(".txt", "");

            if (scoreSave != 0) {
                JOptionPane.showMessageDialog(null, "Nic nie zapisano!!", "Export do 'Notatnik' format *.txt", 2);

            } else {
                int countLines = textArea.getLineCount();
                try {
                    BufferedWriter saveData = new BufferedWriter(new FileWriter(saveAdress + ".txt"));

                    for (int i = 0; i < countLines; i++) {
                        int dd2 = textArea.getLineStartOffset(i);
                        int dd3 = textArea.getLineEndOffset(i);
                        String readLine = text.substring(dd2, dd3);
                        saveData.write(readLine);
                        saveData.newLine();
                    }
                    saveData.flush();
                    saveData.close();

                } catch (Exception e) {
                    textArea.setLineWrap(lineWrap);
                    System.err.println("Unable to create output file.");
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(null, "Błąd utworzenia pliku *.odt !!!" + "\nBłąd: " + e.getMessage(), "UWAGA !!!", 2);
                }
                textArea.setLineWrap(lineWrap);
                JOptionPane.showMessageDialog(null, "Zapisano plik", "Export do 'Notatnik' format *.txt", 1);
            }
        }

        private void readFile() {

            JFileChooser readFileChoosr = new JFileChooser();
            readFileChoosr.setCurrentDirectory(new File(System.getProperty("user.dir")));
            readFileChoosr.showOpenDialog(null);

            File selectedFile = readFileChoosr.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.read(reader, "File");
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }

    }
}