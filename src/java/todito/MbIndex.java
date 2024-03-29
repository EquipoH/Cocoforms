
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todito;

import entidades.Encuesta;
import entidades.Pregunta;
import entidades.Preguntaabcd;
import entidades.Respuestaabcd;
import entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Lenovo
 */
@Named(value = "mbIndex")
@SessionScoped
public class MbIndex implements Serializable {

    /**
     * Creates a new instance of MbIndex
     */
    private ArrayList<Encuesta> cmb1 = null;
    private Encuesta formSelected;
    private ArrayList<Pregunta> cmb2 = null;
    private ArrayList<Preguntaabcd> cmb3 = null;
    private ArrayList<Pregunta> cmb4 = null;
    private ArrayList<Respuestaabcd> cmb5 = null;
    private String user="";
    private String password="";

    public ArrayList<Respuestaabcd> getCmb5() {
        return cmb5;
    }

    public void setCmb5(ArrayList<Respuestaabcd> cmb5) {
        this.cmb5 = cmb5;
    }

    public Encuesta getFormSelected() {
        return formSelected;
    }

    public void setFormSelected(Encuesta formSelected) {
        this.formSelected = formSelected;
    }

    public ArrayList<Encuesta> getCmb1() {

        cmb1 = new ArrayList<Encuesta>();
        EntityManagerFactory emf;
        EntityManager em;
        emf = Persistence.createEntityManagerFactory("CocoFormsPU");
        em = emf.createEntityManager();
        Query consulta = em.createNamedQuery("FindtoAllEncuestas");
        List<Encuesta> tipo = consulta.getResultList();
        System.out.println("holaaaa");
        for (Encuesta encuesta : tipo) {
            cmb1.add(encuesta);
        }

        return cmb1;
    }

    public ArrayList<Pregunta> getCmb2() {
        return cmb2;
    }

    public void setCmb2(ArrayList<Pregunta> cmb2) {
        this.cmb2 = cmb2;
    }

    public void setCmb1(ArrayList<Encuesta> cmb1) {
        this.cmb1 = cmb1;
    }

    public ArrayList<Preguntaabcd> getCmb3() {
        return cmb3;
    }

    public void setCmb3(ArrayList<Preguntaabcd> cmb3) {
        this.cmb3 = cmb3;
    }

    public ArrayList<Pregunta> getCmb4() {
        return cmb4;
    }

    public void setCmb4(ArrayList<Pregunta> cmb4) {
        this.cmb4 = cmb4;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public MbIndex() {

    }

    public String toForm(Encuesta encuesta) {
        formSelected = encuesta;
        cmb2 = new ArrayList<Pregunta>();
        cmb3 = new ArrayList<Preguntaabcd>();
        EntityManagerFactory emf;
        EntityManager em;
        emf = Persistence.createEntityManagerFactory("CocoFormsPU");
        em = emf.createEntityManager();

        Query consulta = em.createNamedQuery("Preguntas");
        consulta.setParameter("encuesta", formSelected.getId());
        List<Pregunta> tipo = consulta.getResultList();

        System.out.println("se va hacer el primer for");
        for (Pregunta pregunta : tipo) {

            System.out.println(pregunta.getTexto());

            cmb2.add(pregunta);

        }
        System.out.println("se termino el primer for");

        Query consulta2 = em.createNamedQuery("Preguntasabcd");
        consulta2.setParameter("encuesta", formSelected.getId());
        List<Preguntaabcd> tipo2 = consulta2.getResultList();


        System.out.println("se va hacer el segundo for");
        for (Preguntaabcd preguntaabcd : tipo2) {
            
            System.out.println(preguntaabcd.getText());

            cmb3.add(preguntaabcd);

        }

        System.out.println("se va terminar el segundo for");

        return "form";

    }
    
     public String ingresar() {
        String nombre=this.user;
        String contrasena = this.password;
        EntityManagerFactory emf;
        EntityManager em;
        emf = Persistence.createEntityManagerFactory("CocoFormsPU");
        em = emf.createEntityManager();

        Query consulta = em.createNamedQuery("Usuario");
    
        //consulta.setParameter("inicio",nombre);
        //consulta.setParameter("inicio", contrasena);
        List<Usuario> tipo = consulta.getResultList();
        System.out.println("Usuario de login:"+nombre);
        System.out.println("Contraseņa de login:"+contrasena);
        
        
        for (Usuario usuario : tipo) {
            System.out.println("Nombre bd:"+usuario.getNombre());
            System.out.println("Contra bd:"+usuario.getFk_pregunta());
            System.out.println("X");
            if(usuario.getNombre().equals(nombre)){
                System.out.println("Correcto");
            }
            if(usuario.getNombre().equals(nombre) && usuario.getFk_pregunta().equals(contrasena)){
            System.out.println("se va hacer el primer for");
            return "logout";
          
            }else{return "cuestionario";}

        }
             
        return null;

    }
     
     
    
     public String Eliminar(Encuesta encuesta) {
      System.out.println("Id: "+encuesta.getId());
      EntityManagerFactory emf;
      EntityManager em;
      
     emf = Persistence.createEntityManagerFactory("CocoFormsPU");
     em = emf.createEntityManager();
    
   
    
    Query consulta =em.createNamedQuery("EliminarEncuesta");
    consulta.setParameter("user", encuesta.getId());
   consulta.executeUpdate();
    System.out.println("Mike");
    
    return "logout";
     }
    
    String toLogin(){
    
        return "login";
    }
    
    String hol(String respuesta,int fk_pregunta){
    
        System.out.println("se entro al index");
        
    return "index";
    }
    String toGrafica(){
    
        
        System.out.println("se entro a la grafica");
        
    return "graficas";
    }
    
    


}