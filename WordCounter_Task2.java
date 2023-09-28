import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordCounter_Task2 extends JFrame {
    private JTextArea textArea;
    private JLabel labelCount,labelCounts;

    public WordCounter_Task2() {
        setTitle("Word Counter");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                int wordCount = countWords(text);
                labelCount.setText("Word Count: " + wordCount);
                int uniq = UniqueWords(text);
                labelCounts.setText("Total Unique Words: " + uniq);

            }
        });

        JButton fileButton = new JButton("Open .txt File");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(WordCounter_Task2.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                        String fileContent = readFile(filePath);
                        textArea.setText(fileContent);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(WordCounter_Task2.this,
                                "Error reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        labelCount = new JLabel("Word Count: 0");
        labelCount.setHorizontalAlignment(SwingConstants.CENTER);
        labelCounts = new JLabel("Total Unique Words: 0");
        labelCounts.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(fileButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(labelCount, BorderLayout.NORTH);
        panel.add(labelCounts, BorderLayout.WEST);
        

        add(panel);
        setVisible(true);
    }

    private int countWords(String text) {
        if (text.isEmpty())
            return 0;
        String[] words = text.split("\\W+");
        return words.length;
    }

    private int UniqueWords(String text)
    {
        int count,c=0;
        String[] words = text.split("\\W");
        for (int i = 0; i < words.length; i++) {
            count = 1;
            for (int j = i + 1; j < words.length; j++) {
                if (words[i].equalsIgnoreCase(words[j])) {
                    count++;
                    words[j] = "";
                }
            }
            if (count == 1 && words[i] != "")
                c++;
        }
        return c;
    }



    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordCounter_Task2();
            }
        });
    }
}