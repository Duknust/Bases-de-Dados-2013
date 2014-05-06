/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bmdb.dao.ActorDAO;
import org.bmdb.dao.FilmeDAO;
import org.bmdb.dao.GeneroDAO;
import org.bmdb.dao.ProdutoraDAO;
import org.bmdb.dao.RealizadorDAO;
import org.bmdb.dao.db.ConnectionBrokerFactory;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.db.SQLiteConnectionBroker;
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.domain.Filme;
import org.bmdb.dao.domain.Genero;
import org.bmdb.dao.domain.Personagem;
import org.bmdb.dao.domain.Produtora;
import org.bmdb.dao.domain.Realizador;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class PopulateDB {

    private static IConnectionBroker cb = SQLiteConnectionBroker.getInstance(null);

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            cb = ConnectionBrokerFactory.giveMeConnectionBrokerFromProperties();

            //destroyDB();
            Connection conn = cb.getConnection();
            //createDB(conn);

            /*String query = "INSERT INTO Produtoras values(1,'Paramount', 'USA') ;\n"
             + "INSERT INTO Produtoras values(1,'Warner','USA') ;\n"
             + "INSERT INTO Realizadores values(1,'Peter Jackson','1-1-111','USA','Masculino') ;\n"
             + "INSERT INTO Generos values(1,'Drama') ;\n"
             + "INSERT INTO Actores values(1,'Leo Capriz','1-1-1111',100,0,'USA','Masculino') ;\n"
             + "INSERT INTO Filmes values(1,'XXXX',1,1,1,1,2000,1000000,2000000,6,10.0,2,2,10,2,13,'AX91A1X','iasdasdasd.png');\n";
             */

            Produtora p1, p2;
            p1 = new Produtora("Banana", "USA", "");
            p2 = new Produtora("Pessego", "USA", "");

            Realizador r = new Realizador("Michael Jackson", "1-1-111", "USA", "Masculino", "");
            Genero g = new Genero("Fantasia");
            Genero g1 = new Genero("Accao");
            Actor a = new Actor("Manuel", "1-1-1111", "", "USA", 100, 0, 0, "Masculino", null);
            Actor a2 = new Actor("Maria", "1-1-2211", "", "USA", 1, 2, 3, "Feminino", null);

            Filme f = new Filme("AAAA", null, null, null, 2000, 100000000, 10000, 2, 1, 1, null, 12, "filme f muito feio", null, null);
            List<Personagem> personagensF2 = new ArrayList<Personagem>();
            personagensF2.add(new Personagem("Gandalf", a));

            List<Genero> generosF2 = new ArrayList<Genero>();
            generosF2.add(g);
            generosF2.add(g1);
            Filme f2 = new Filme("Eder", generosF2, null, null, 2000, 100000000, 10000, 2, 1, 1, personagensF2, 12, "filme f2 giro", null, null);

            RealizadorDAO realizadorDAO = new RealizadorDAO(cb);
            realizadorDAO.insert(r);

            ProdutoraDAO produtoraDAO = new ProdutoraDAO(cb);
            produtoraDAO.insert(p1);
            produtoraDAO.insert(p2);

            GeneroDAO generoDAO = new GeneroDAO(cb);
            generoDAO.insert(g);

            ActorDAO actorDAO = new ActorDAO(cb);
            actorDAO.insert(a);
            actorDAO.insert(a2);

            FilmeDAO filmeDAO = new FilmeDAO(cb);
            filmeDAO.insert(f);
            filmeDAO.insert(f2);

            List<Filme> filmes = filmeDAO.getAll();
            for (Filme fi : filmes) {
                System.out.println(fi.getTitulo().toString());
            }

            Actor actorCriteria = new Actor();
            actorCriteria.setNomeActor("Leo");
            // actorCriteria.setPremiosNomeadoActor(100);
            List<Actor> fetchedActor = actorDAO.getByCriteria(actorCriteria);
            for (Actor aaaa : fetchedActor) {
                System.out.println(aaaa.toString());
            }
            Realizador realizadorCriteria = new Realizador();
            realizadorCriteria.setNacionalidadeRealizador("U");
            List<Realizador> fetchedRealizador = realizadorDAO.getByCriteria(realizadorCriteria);

            System.out.println("-----------------");

            for (Realizador rrrr : fetchedRealizador) {
                System.out.println(rrrr.toString());
            }

            conn.close();

        } catch (GenericDAOException | SQLException ex) {
            ex.printStackTrace(System.err);
            Logger.getLogger(PopulateDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void destroyDB() {
        cb.destroy();
    }

    public static void createDB(Connection conn) {
        PreparedStatement pstmt = null;
        try {
            FileInputStream fs = new FileInputStream("sql/sql_creates.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs));

            String line;
            StringBuilder out = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
                if (line.endsWith(";")) {
                    System.out.println(out.toString());
                    pstmt = conn.prepareStatement(out.toString());
                    pstmt.execute();
                    out = new StringBuilder();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PopulateDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopulateDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PopulateDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(PopulateDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
