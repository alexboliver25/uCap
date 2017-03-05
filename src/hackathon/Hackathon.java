/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackathon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ddrms
 */
public class Hackathon extends JFrame implements ActionListener
{        
    
    private JComboBox floorcombobox, buildingcombobox;
    private JPanel capturePanel;
    private JLabel floorLabel, buildingLabel;
    private JButton submitButton, browseButton, displayButton;
    private JTextField descriptionfield;
    String s;
    
    
    
    // instance variables used to manipulate database
    private Connection myConnection;
    private Statement myStatement;
    private ResultSet myResultSet;
   
    static int ID;
    int floor;
    int building;
    boolean flag; 
    int weight = 0;   
    String description;    
    

    
    public static void main(String[] args) 
    {
        String databaseDriver = "org.apache.derby.jdbc.ClientDriver";
        String databaseURL = "jdbc:derby://localhost:1527/captureproblem";
        
        Hackathon hack = new Hackathon(databaseDriver, databaseURL);
        hack.interfaceBuild();
        hack.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );  
    }
    
    public Hackathon(String databaseDriver, String databaseURL)
    {
        {
        // establish connection to database
      try
      {
         // load Sun driver
         //Class.forName( databaseDriver );
         DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
         // connect to database
         myConnection = DriverManager.getConnection( databaseURL );

         // create Statement for executing SQL
         myStatement = myConnection.createStatement();
      }
      catch ( SQLException exception )
      {
         exception.printStackTrace();
      }
 
         }
    }
    
    public void interfaceBuild()    
    {
        Container contentPane = getContentPane();
        
        String[] floorStrings = { " " , "Floor 1", "Floor 2", "Floor 3", "Floor 4", "Floor 5" };
        String[] buildingStrings={"", "Building 1", "Building 2", "Building 3"};
        
        
        capturePanel =  new JPanel();
        capturePanel.setBounds(60, 20, 276, 48 );
        capturePanel.setBorder( BorderFactory.createEtchedBorder() );
        capturePanel.setLayout(null);
        contentPane.add(capturePanel);
        
        buildingLabel = new JLabel();
        buildingLabel.setBounds(25, 15, 100, 20);
        buildingLabel.setText( "Building:" );
        capturePanel.add( buildingLabel );
        
        buildingcombobox = new JComboBox(buildingStrings);
        buildingcombobox.setBounds(150, 15, 96, 25);
        //floorcombobox.addItem( "" );
        buildingcombobox.setSelectedIndex( 0 );
        capturePanel.add( buildingcombobox ); 
        
        floorLabel = new JLabel();
        floorLabel.setBounds( 25, 55, 100, 20 );
        floorLabel.setText( "Floor:" );
        capturePanel.add( floorLabel );
  
        floorcombobox = new JComboBox(floorStrings);
        floorcombobox.setBounds( 150, 55, 96, 25 );
        //floorcombobox.addItem( "" );
        floorcombobox.setSelectedIndex( 0 );
        capturePanel.add( floorcombobox ); 
        
        browseButton = new JButton("Browse");
        browseButton.setBounds(175, 200, 78, 40);
        capturePanel.add(browseButton);
        
        descriptionfield = new JTextField("Please enter a description here", 300);
        descriptionfield.setFont( new Font( "Serif", Font.PLAIN, 14 ) );
        descriptionfield.setBounds(25, 95, 300, 20 );
        capturePanel.add(descriptionfield);
        
        submitButton = new JButton("Submit");
        submitButton.setBounds(75, 200, 78, 40);
        capturePanel.add(submitButton);
        
        displayButton = new JButton("Display");
        displayButton.setBounds(125, 250, 78, 40 );
        capturePanel.add(displayButton);
        
        browseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION)
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    //label.setIcon(ResizeImage(path));
                    s = path;
                }
                else if(result == JFileChooser.CANCEL_OPTION)
                {
                    System.out.println("No Data");
                }
            }
        });

        
        submitButton.addActionListener(this);
        displayButton.addActionListener(this);
      
        
        setTitle( "UMSLCapture" );   // set title bar string
        setSize( 375, 410 ); // set window size
        setVisible( true );  // display window
    }
    
//    public void secondInterface()
//    {
//        
//    }
//    
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException 
    {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
         Vector<String> columnNames = new Vector<String>();
         int columnCount = metaData.getColumnCount();
         
         for (int column = 1; column <= columnCount; column++) 
         {
         columnNames.add(metaData.getColumnName(column));
         }

        // data of the table
         Vector<Vector<Object>> data = new Vector<Vector<Object>>();
         while (rs.next()) 
         {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
            {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
         }

    return new DefaultTableModel(data, columnNames);

}
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
        if(e.getSource().equals(submitButton))
        {
            if(floorcombobox.getSelectedItem().toString() == "Floor 1")
            {
                floor = 1;
            }
        
            else if(floorcombobox.getSelectedItem().toString() == "Floor 2")
            {
                floor = 2;
            }
        
            else if(floorcombobox.getSelectedItem().toString() == "Floor 3")
            {
                floor = 3;
            }
        
            else if(floorcombobox.getSelectedItem().toString() == "Floor 4")
            {
                floor = 4;
            }
        
            else if(floorcombobox.getSelectedItem().toString() == "Floor 5")
            {
                floor = 5;
            }
        
            if(buildingcombobox.getSelectedItem().toString() == "Building 1")
            {
                building = 1;
            }
        
            else if(buildingcombobox.getSelectedItem().toString() == "Building 2")
            {
                building = 2;
            }
        
            else if(buildingcombobox.getSelectedItem().toString() == "Building 3")
            {
                building = 3;
            }
            
            description = descriptionfield.getText();   
        
        //JOptionPane.showMessageDialog( null, "Floor: " + floor + "\n" + "Building: " + building);
        
           try
           {
               //Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/captureproblem");
               //String testStatement = "INSERT INTO APP.PROBLEMS(ID, FLOOR, WEIGHT, IMAGE) VALUES (" + ID + "," + floor+ "," + weight+ "," + s +")";
                
               
               
//               myResultSet=myStatement.executeQuery("SELECT MAX(ID) FROM APP.PROBLEMS");
//               
//               int test = myResultSet.getInt(1);
//               
//               test ++;
               ID = 5;
               //System.out.println(ID);
               
               
               PreparedStatement ps = myConnection.prepareStatement("INSERT INTO APP.PROBLEMS (ID, FLOOR, WEIGHT, IMAGE, FLAG, BUILDING, DESCRIPTION) VALUES(?,?,?,?,?,?,?)");
               InputStream is = new FileInputStream(new File(s));
               
               ps.setInt(1, ID);
               ps.setInt(2, floor);
               ps.setInt(3, weight);
               ps.setBlob(4, is);
               ps.setBoolean(5, flag);
               ps.setInt(6,building);
               ps.setString(7,description);
               ps.executeUpdate();
               
               //myStatement.executeUpdate(testStatement);
               
               JOptionPane.showMessageDialog(null, "Data Inserted");
           }
           catch(Exception ex)
           {
               ex.printStackTrace();
           }
           
           
           
           
       }
       if (e.getSource() == displayButton)
           {
            try
            {
                myResultSet = myStatement.executeQuery("SELECT ID, FLOOR, WEIGHT, IMAGE, FLAG, BUILDING, DESCRIPTION FROM APP.PROBLEMS");
                //WHERE BUILDING = " + building + "AND FLOOR = " + floor
                JTable table = new JTable(buildTableModel(myResultSet));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
            catch( SQLException exception )
            {
                 exception.printStackTrace();
            }
           }
    }
}
    
            
    
    
//    public void inputPicture(Connection conn,String img)
//    {
//        {
//                int len;
//                String query;
//                PreparedStatement pstmt;
//                 
//                try
//                {
//                        File file = new File(img);
//                        FileInputStream fis = new FileInputStream(file);
//                        len = (int)file.length();
// 
//                        query = ("insert into TableImage VALUES(?,?,?)");
//                        pstmt = myConnection.prepareStatement(query);
//                        pstmt.setString(1,file.getName());
//                        pstmt.setInt(2, len);
//   
//                        // Method used to insert a stream of bytes
//                        pstmt.setBinaryStream(3, fis, len); 
//                        pstmt.executeUpdate();
// 
//                }
//                catch (Exception e)
//                {
//                        e.printStackTrace();
//                }
//        }
//    }
//}