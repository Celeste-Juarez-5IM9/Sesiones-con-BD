/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SesionesServletVALIDACION extends HttpServlet {
   
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      HttpSession sesion = request.getSession();
      String titulo = null;

      //Pedimos el atributo, y verificamos si existe
      String claveSesion = (String) sesion.getAttribute("claveSesion");


            Connection c=null; 
            Statement s=null;
            ResultSet r = null;
            try{
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                c = DriverManager.getConnection("jdbc:mysql://localhost/sesion","root","flordemaria117");
                s= c.createStatement();
            }
            catch( SQLException error) {
                out.print(error.toString() );
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SesionesServletVALIDACION.class.getName()).log(Level.SEVERE, null, ex);
        }
            String usua=null, cont=null;
            try{
                r = s.executeQuery("select * from login");
                if(r.next()){
                    usua = r.getString("usuario");
                    cont = r.getString("contra");
                }
                else
                    out.println("<script>alert('No existe.')</script>");

        
            }
            catch( SQLException error) {
                out.print(error.toString() );
            }
      if(claveSesion.equals(usua+cont)){
        titulo = "Clave correcta.";
      }
      else
      {
        titulo = "Clave incorrecta";
      }   


      //Mostramos los  valores en el cliente
      out.println("¿Continua la Sesion y es valida?: " + titulo);
      out.println("<br>");
      out.println("ID de la sesi&oacute;n JSESSIONID: " + sesion.getId());
  
    }

}
