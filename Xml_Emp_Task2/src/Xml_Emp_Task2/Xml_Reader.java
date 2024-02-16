package Xml_Emp_Task2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Xml_Reader {

    public static void main(String[] args) {
        // Establish database connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees", "root", "arun23"))
        		{
            //Db_Connect d1 = new Db_Connect();

            // XML parsing
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("Exployee.xml");
            NodeList nodeList = doc.getElementsByTagName("employee");

            // Iterate through XML nodes
            for (int x = 0; x < nodeList.getLength(); x++) {
                Node node = nodeList.item(x);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    //String id = element.getAttribute("id");
                    String id = element.getElementsByTagName("id").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String post = element.getElementsByTagName("position").item(0).getTextContent();
                    String dept = element.getElementsByTagName("department").item(0).getTextContent();
                    

                    if (post.equals("Quality Engineer")) {
                        try {
                        	System.out.println("Employee ID: " + id);
                            System.out.println("Name: " + name);
                            System.out.println("Position: " + post);
                            System.out.println("Department: " + dept);
                            
                            // Insert data into the database
                            PreparedStatement ps = con.prepareStatement("insert into employee(ID, Name, Position, Department) values (?, ?, ?, ?)");
                            ps.setString(1, String.valueOf(id));
                            ps.setString(2, name);
                            ps.setString(3, post);
                            ps.setString(4, dept);
                            ps.executeUpdate();
                            //ps.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (SQLException | ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
