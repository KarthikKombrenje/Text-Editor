package com.company;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;


class TextEditor implements ActionListener {
    JFrame f;
    JMenuBar menuBar;
    JMenu file,
            edit,
            themes,
            help;
    JTextArea textArea;
    JScrollPane scroll;
    JMenuItem darkTheme,
            moonLightTheme,
            defaultTheme,
            save,
            open,
            close,
            cut,
            copy,
            paste,
            New,
            selectAll,bold,italic,
            fontSize;
    JPanel saveFileOptionWindow;
    JLabel fileLabel, dirLabel;
    JTextField fileName, dirName;

    TextEditor(){
        f = new JFrame("Untitled_Document-1"); //setting the frame
        menuBar = new JMenuBar();

        //menus
        file = new JMenu("File");
        edit = new JMenu("Edit");
        themes = new JMenu("Themes");
        //adding menus to menu bar
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(themes);
        f.setJMenuBar(menuBar);

        //adding submenus to file
        save = new JMenuItem("Save");
        open = new JMenuItem("Open");       //file menu
        New = new JMenuItem("New");
        close = new JMenuItem("Exit");
        file.add(open);
        file.add(New);
        file.add(save);
        file.add(close);

        cut = new JMenuItem("Cut");
        copy = new JMenuItem("Copy");        //Sub Menu for edit menu
        paste = new JMenuItem("Paste");
        selectAll = new JMenuItem("Select all");
        fontSize = new JMenuItem("Font size");
        bold = new JMenuItem("Bold");
        italic= new JMenuItem("Italic ");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(fontSize);
        darkTheme = new JMenuItem("Dark Theme"); //Sub Menu for themes
        moonLightTheme = new JMenuItem("Moonlight Theme");
        defaultTheme = new JMenuItem("Default Theme");
        themes.add(darkTheme);
        themes.add(moonLightTheme);
        themes.add(defaultTheme);

        //Text area
        textArea = new JTextArea(32,88);
        f.add(textArea);

        //scroll pane
        scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(scroll);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        fontSize.addActionListener(this); //change the font size
        open.addActionListener(this); //open the file
        save.addActionListener(this); //Save the file
        bold.addActionListener(this);//bold the text
        italic.addActionListener(this);//Italic the text
        New.addActionListener(this); //Create the new document
        darkTheme.addActionListener(this); //dark theme
        moonLightTheme.addActionListener(this); //moonlight theme
        defaultTheme.addActionListener(this); // default theme
        close.addActionListener(this); //close the window

        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {}

            @Override
            public void windowClosing(WindowEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(f,"Do you want to exit?","Are you sure?.",JOptionPane.YES_NO_OPTION);

                if (confirmExit == JOptionPane.YES_OPTION)
                    f.dispose();
                else if (confirmExit == JOptionPane.NO_OPTION)
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {}

            @Override
            public void windowIconified(WindowEvent windowEvent) {}

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {}

            @Override
            public void windowActivated(WindowEvent windowEvent) {}

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {}
        });

        //Keyboard Listeners
        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_S && e.isControlDown())//if we click on s it will save
                    saveTheFile(); //Saving the file
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        };
        textArea.addKeyListener(k);//whenever u type the below happens

        //Default Operations for frame
        f.setSize(1000,596);
        f.setResizable(true);
        f.setLocation(250,100);
        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Copy paste operations
        if (e.getSource()==cut)
            textArea.cut();
        if (e.getSource()==copy)
            textArea.copy();
        if (e.getSource()==paste)
            textArea.paste();
        if (e.getSource()==selectAll)
            textArea.selectAll();

        //change the font size by value
        if (e.getSource()==fontSize){

            String sizeOfFont = JOptionPane.showInputDialog(f,"Enter Font Size",JOptionPane.OK_CANCEL_OPTION);
                if (sizeOfFont != null){
                    int convertSizeOfFont = Integer.parseInt(sizeOfFont);
                    Font font = new Font(Font.SANS_SERIF,Font.PLAIN,convertSizeOfFont);
                    textArea.setFont(font);
                }
        }  
      //Open the file
        if (e.getSource()==open){
            JFileChooser chooseFile = new JFileChooser();
            int i = chooseFile.showOpenDialog(f);
            if (i == JFileChooser.APPROVE_OPTION){//yes or no to choose a file if yes then 
                File file = chooseFile.getSelectedFile(); //select the file
                String filePath = file.getPath(); //get the file path
                String fileNameToShow = file.getName(); //get the file name
                f.setTitle(fileNameToShow);

               try {
                   BufferedReader readFile = new BufferedReader(new FileReader(filePath));
                   String tempString1 = "";
                   String tempString2 = "";

                   while ((tempString1 = readFile.readLine()) != null)
                        tempString2 += tempString1 + "\n";

                   textArea.setText(tempString2);
                   readFile.close();
               }catch (Exception ae){
                   ae.printStackTrace();
               }
            }
        }

        //Save the file
        if (e.getSource()==save) saveTheFile();


        //New menu operations
        if (e.getSource()==New) textArea.setText("");


        //Exit from the window
        if (e.getSource()==close) System.exit(1);


        //themes area
        if (e.getSource()==darkTheme){
            textArea.setBackground(Color.DARK_GRAY);        //dark Theme
            textArea.setForeground(Color.WHITE);
        }

        if (e.getSource()==moonLightTheme){
            textArea.setBackground(new Color(107, 169, 255));
            textArea.setForeground(Color.black);
        }

        if (e.getSource() == defaultTheme){
            textArea.setBackground(new Color(255, 255, 255));
            textArea.setForeground(Color.black);
        }
    }

//Save the file
    public void saveTheFile(){
        saveFileOptionWindow = new JPanel(new GridLayout(2,1));
        fileLabel = new JLabel("Filename      :- ");
        dirLabel = new JLabel("Save File To :- ");
        fileName = new JTextField();
        dirName = new JTextField();
        saveFileOptionWindow.add(fileLabel);
        saveFileOptionWindow.add(fileName);
        saveFileOptionWindow.add(dirLabel);
        saveFileOptionWindow.add(dirName);

        JOptionPane.showMessageDialog(f,saveFileOptionWindow); //show the saving dialogue box
        String fileContent = textArea.getText();
        String file = fileName.getText();
        String filePath = dirName.getText()+"/"+file+".txt";

        try {
            BufferedWriter writeContent = new BufferedWriter(new FileWriter(filePath));
            writeContent.write(fileContent);
            writeContent.close();
            JOptionPane.showMessageDialog(f,"File Successfully saved!");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextEditor();
    }
}























