package com.simsns.ver03;

import javax.swing.*;
import java.awt.*;

public class AppMain extends JFrame {


    private JPanel panel1;
    private JButton systemOff;
    private JButton postList;
    private JButton createPost;
    private JButton modifyPost;
    private JButton profile;

    public static void main (String[] args) {
        EventQueue.invokeLater (new Runnable () {
            @Override
            public void run() {
                try {
                    AppMain window = new AppMain ();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AppMain () {
        initialize();
    }

    private void initialize () {
        setBounds(30, 30, 680, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setVisible(true);





    }
}
