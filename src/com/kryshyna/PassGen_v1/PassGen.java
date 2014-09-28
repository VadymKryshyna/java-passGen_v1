package com.kryshyna.PassGen_v1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * User: Vadym Kryshyna
 * Date: 27.09.14
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class PassGen extends JFrame {
    public static void main(String[] args) {


        JFrame frame = new PassGen();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public PassGen(){
        super("Password generator v.1");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setSize(300, 300);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
        JMenuItem menuItemAbout = new JMenuItem("About");
        menu.add(menuItemAbout);

        textPartPassword = new JTextField();
        textNewPassword = new JTextField();

        JLabel labelInfo = new JLabel("Please select parameter new password:");
        JLabel labelPartPassword = new JLabel("You can include some text to password:");
        JLabel labelLengthPassword = new JLabel("Select length your password:");

        buttonGeneratePassword = new JButton("Generate");

        checkNumber = new JCheckBox("include number");
        checkNumber.setSelected(true);
        checkLittle = new JCheckBox("include little letter");
        checkBig = new JCheckBox("include big letter");
        checkSpecial = new JCheckBox("include special chars");

        lengthPassword = new JComboBox<Integer>(array);


        GridLayout mainLayout = new GridLayout(11,1);

        this.setLayout(mainLayout);


        this.add(labelInfo);
        this.add(checkNumber);
        this.add(checkBig);
        this.add(checkLittle);
        this.add(checkSpecial);
        this.add(labelPartPassword);
        this.add(textPartPassword);
        this.add(labelLengthPassword);
        this.add(lengthPassword);
        this.add(buttonGeneratePassword);
        this.add(textNewPassword);


        menuItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, messageAbout);
            }
        });

        buttonGeneratePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((checkNumber.isSelected() | checkBig.isSelected() | checkLittle.isSelected() | checkSpecial.isSelected()) == true) {
                    char[][] tableChars = getTable(checkNumber.isSelected(), checkBig.isSelected(), checkLittle.isSelected(), checkSpecial.isSelected());
                    int length = array[lengthPassword.getSelectedIndex()];
                    String password = null;
                    if (textPartPassword.getText().length()==0){
                        password = generate(tableChars, length);
                    }else{
                        String partPassword = textPartPassword.getText();
                        if(partPassword.length()+1 > length){
                            JOptionPane.showMessageDialog(null, "Length input part password is >= length your password. Please check.");
                        }else if(checkPartPassword(partPassword)==false){
                            JOptionPane.showMessageDialog(null, "Input part password is incorrect. Please check.");
                        }else{
                            password = generate(tableChars, length, partPassword);
                        }
                    }
                    textNewPassword.setText(password);
                }else{
                    JOptionPane.showMessageDialog(null, "Please check same chars for your password.");
                }
            }
        });
    }

    //check input part future password
    private boolean checkPartPassword(String text) {
        char[] charArray = text.toCharArray();
        boolean correct = true;
        char [] correctChar = {'1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F','G','H','I','J','K','L',
                'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l',
                'm','n','o','p','q','r','s','t','u','v','w','x','y','z','!','@','#','$','%','^','&','*','(',')','-','_',
                '+','=','[',']','{','}','?','/','.',','};
        for(int i = 0; i <charArray.length && correct==true; i++){
            boolean next = false;
            for (int j = 0; j < correctChar.length && next==false; j++){
                if (charArray[i]==correctChar[j]){
                    next = true;
                }
            }
            if(next== false){
                correct= false;
            }
        }
        return correct;
    }

    // create table with selected chars
    private char[][] getTable(boolean num, boolean big, boolean little, boolean special){
        int size = 0;
        if (num == true){   size++; }
        if (big == true){   size++; }
        if (little == true){   size++; }
        if (special == true){   size++; }
        char [][] chars = new char[size][];
        if (num == true){
            size--;
            char []temp = {'1','2','3','4','5','6','7','8','9','0'};
            chars[size] = temp;
        }
        if (big == true){
            size--;
            char []temp = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
            chars[size] = temp;
        }
        if (little == true){
            size--;
            char []temp = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
            chars[size] = temp;
        }
        if (special == true){
            size--;
            char []temp = {'!','@','#','$','%','^','&','*','(',')','-','_','+','=','[',']','{','}','?','/','.',','};
            chars[size] = temp;
        }
        return chars;
    }

    //random select chars for password without part password
    private String generate(char chars[][], int length){
        char []password = new char[length];
        for (int i = 0; i < password.length; i++){
            Random rand = new Random();
            int j = rand.nextInt(chars.length);
            int k = rand.nextInt(chars[j].length);
            password[i] = chars[j][k];
        }

        return String.valueOf(password);
    }

    //random select chars for password with part password
    private String generate(char chars[][], int length, String partPassword){
        Random rand = new Random();
        char []password = new char[length];
        int partLength = partPassword.length();
        int position = rand.nextInt(length-partLength+1);

        for(int i=0;i<partPassword.length();i++){
            password[position+i] = partPassword.charAt(i);
        }

        for (int i = 0; i < password.length; i++){
            if(password[i]=='\u0000'){
                int j = rand.nextInt(chars.length);
                int k = rand.nextInt(chars[j].length);
                password[i] = chars[j][k];
            }
        }
        return String.valueOf(password);
    }


    private JTextField textPartPassword;
    private JTextField textNewPassword;
    private JCheckBox checkNumber;
    private JCheckBox checkLittle;
    private JCheckBox checkBig;
    private JCheckBox checkSpecial;
    private JComboBox<Integer> lengthPassword;
    private JButton buttonGeneratePassword;
    private Integer [] array = {3,4,5,6,7,8,9,10,11,12,13,14,15};
    private String messageAbout = "@Author: Vadym Kryshyna \n     2014";
}
