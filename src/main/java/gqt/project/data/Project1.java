package gqt.project.data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedHashMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Project1 {
    public static void main(String[] args) throws Exception {
        String path = "D:\\IOprograms\\Resumeupdated.txt";
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        LinkedHashMap<String, StringBuilder> map = new LinkedHashMap<>();
        String current_Key = "GENERAL";
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("http")) {
                map.putIfAbsent(current_Key, new StringBuilder());
                map.get(current_Key).append(line).append("\n");
                continue;
            }

            if (line.endsWith(":")) {
                current_Key = line.substring(0, line.length() - 1);
                map.putIfAbsent(current_Key, new StringBuilder());
                continue;
            }

            if (current_Key.equals("INTERNSHIP EXPERIENCE") || current_Key.equals("PROJECTS") || current_Key.equals("EDUCATION")) {
                map.putIfAbsent(current_Key, new StringBuilder());
                map.get(current_Key).append(line).append("\n");
                continue;
            }

            if (line.contains(":")) {
                String[] part = line.split(":", 2);
                current_Key = part[0].trim();
                String value = part[1].trim();
                map.putIfAbsent(current_Key, new StringBuilder());
                if (!value.isEmpty())
                    map.get(current_Key).append(value).append("\n");
            } else {
                map.putIfAbsent(current_Key, new StringBuilder());
                map.get(current_Key).append(line).append("\n");
            }
        }
        br.close();
        fr.close();

        StringBuilder display = new StringBuilder();
        display.append("ABHISHEK D N\n");
        display.append("SOFTWARE DEVELOPER | JAVA FULL STACK\n");
        display.append("===================================================\n\n");

        for (String key : map.keySet()) {
            String value = map.get(key).toString().trim();
            if (value.isEmpty()) continue;
            if (key.equals("Name") || key.equals("Role") || key.equals("Company") || key.equals("Duration")) continue;

            display.append(key.toUpperCase()).append("\n");

            if (key.equals("INTERNSHIP EXPERIENCE")) {
                String[] lines = value.split("\n");
                for (String s : lines) {
                    if (s.startsWith("-"))
                        display.append("   • ").append(s.substring(1).trim()).append("\n");
                    else
                        display.append(s).append("\n");
                }
                display.append("\n");
            } else if (key.equals("PROJECTS")) {
                String[] lines = value.split("\n");
                for (String s : lines) {
                    if (s.matches("\\d+\\..*")) {
                        display.append("\n");
                        display.append(s);
                        display.append("\n");
                    } else if (s.startsWith("-")) {
                        display.append("   • ").append(s.substring(1).trim()).append("\n");
                    } else {
                        if (!s.isBlank())
                            display.append("   ").append(s).append("\n");
                    }
                }
                display.append("\n");
            } else if (key.equals("EDUCATION")) {
                String[] lines = value.split("\n");
                for (String s : lines) {
                    if (s.matches("\\d+\\..*") || s.contains("College") || s.contains("School")) {
                        display.append("\n");
                        display.append("• ").append(s);
                        display.append("\n");
                    } else {
                        display.append("   ");
                        display.append(s);
                        display.append("\n");
                    }
                }
                display.append("\n");
            } else {
                display.append(value);
                display.append("\n\n");
            }
            display.append("---------------------------------------------------\n\n");
        }

        JFrame frame = new JFrame("Resume Viewer");
        JTextArea area = new JTextArea();
        area.setText(display.toString());
        area.setEditable(false);
        area.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        area.setForeground(new Color(35, 35, 35));
        area.setBackground(new Color(245, 245, 245));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(40, 60, 40, 60));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.setContentPane(scroll);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}