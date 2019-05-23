/* Riff Ahmad Bazlee
 * P465225
 * 22.05.2019
 * Software Deployment Project
 */
package dvdsearchapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DVDSearchApp extends JFrame {

    //GUI variables
    JTextField txtTitle;
    JButton butSearch;
    JList list;
    JScrollPane scrollResults;
    ArrayList<String> dvdList;//store list

    final String mainClass = DVDSearchApp.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//    final String mainDir = mainClass.substring(0, mainClass.indexOf("build"));

    //constructor
    public DVDSearchApp() throws IOException {

        //set gui title and layout
        setTitle("DVD Search App");
        setLayout(null);

        //set vatiables
        DefaultListModel dlm = new DefaultListModel();
        list = new JList(dlm);
        dvdList = new ArrayList();
        txtTitle = new JTextField();
        txtTitle.setBounds(30, 30, 150, 25);
        butSearch = new JButton("Title Search");
        butSearch.setBounds(200, 30, 120, 25);
        scrollResults = new JScrollPane(list);
        scrollResults.setBounds(30, 85, 290, 150);

        //new config reader
        ConfigReader properties = new ConfigReader();

        //Connection strings
        String url = properties.getURL();
        String user = properties.getUser();
        String password = properties.getPass();

        //query
        String query = "SELECT title FROM `dvd_table`;";

        //try filling array with database
        try ( Connection con = DriverManager.getConnection(url, user, password); //connection creation (autoclosed)
                  Statement stmt = con.createStatement();  ResultSet result = stmt.executeQuery(query);) {

            while (result.next()) {
                dvdList.add(result.getString("title"));
            }
        } catch (Exception e) {
            txtTitle.setText("Failed to Build DVD List");
            System.out.println(e);
        }
        
        if(url==null){
            txtTitle.setText("Not connected");
        }

        //loop that array to fill dlm
        for (String curVal : dvdList) {
            dlm.addElement(curVal);
        }

        butSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                //search string
                String search = txtTitle.getText().toLowerCase();
                //query
                String query = "SELECT * FROM `dvd_table` WHERE LOWER(title) LIKE ?;";
 
                try ( Connection con = DriverManager.getConnection(url, user, password); //connection creation (autoclosed)
                         PreparedStatement prepstmt = con.prepareStatement(query); ) {
                    prepstmt.setString(1, "%" + search + "%");
                    ResultSet result = prepstmt.executeQuery();

                    dlm.removeAllElements();//clear dlm first
                    dvdList.clear(); // clear arraylist

                    while (result.next()) {
                        dvdList.add(result.getString("title"));
                    }
                
                    prepstmt.close();
                    result.close();
                } catch (Exception e) {
                    txtTitle.setText("Something Went Wrong!");
                    System.out.println(e);
                }

                //loop that array to fill dlm
                for (String curVal : dvdList) {
                    dlm.addElement(curVal);
                }
            }
        });

        add(txtTitle);
        add(butSearch);
        add(scrollResults);

        setSize(360, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new DVDSearchApp();
        } catch (IOException e) {

        }
    }
}
