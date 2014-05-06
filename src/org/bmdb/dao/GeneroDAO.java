/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Genero;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class GeneroDAO extends GenericDAO<Genero> implements IGenericDAO<Genero> {

    /**
     * Column names
     */
    private static String COL_ID_GENERO = "ID_GENERO";
    private static String COL_NOME = "NOME";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into GENEROS( "
            + COL_NOME
            + ") values (?)";
    static String DELETE_STATEMENT = "delete from GENEROS "
            + "where ID_GENERO=?";
    static String UPDATE_STATEMENT = "update GENEROS set "
            + COL_NOME + "=? "
            + "where ID_GENERO=?";

    public GeneroDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Genero> getAll() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        List<Genero> generos = new ArrayList<Genero>();
        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM generos");
            pstmt.execute();
            ResultSet generosRs = pstmt.getResultSet();
            Genero genero = null;
            while (generosRs.next()) {
                genero = new Genero(
                        generosRs.getString(COL_NOME));
                genero.setIdGenero(generosRs.getInt(COL_ID_GENERO));
                generos.add(genero);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui obter a lista de todos os Generos", sqle);

        }
        return generos;
    }

    @Override
    public boolean insert(Genero object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            Connection conn = this.cb.getConnection();
            pstmt = conn.prepareStatement(GeneroDAO.INSERT_STATEMENT, new String[]{COL_ID_GENERO});


            pstmt.setString(1, object.getNomeGenero());          // The first ? is for EMPLOYEE_ID

            // Do the insertion, check number of rows updated
            System.out.println("Query: " + pstmt.toString());
            pstmt.execute();
            ResultSet generoKeys = pstmt.getGeneratedKeys();
            int idGenero = -1;
            if (result = (pstmt.getUpdateCount() != 0) && generoKeys.next()) {
                idGenero = generoKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted genero with ID: " + idGenero);
                object.setIdGenero(idGenero);
                pstmt.close();
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(GeneroDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
            }
        }
        return result;
    }

    @Override
    public boolean delete(Genero object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(GeneroDAO.DELETE_STATEMENT);
            pstmt.setInt(1, object.getId());

            // Do the insertion, check number of rows updated
            pstmt.execute();
            result = pstmt.getUpdateCount() > 0;
            System.out.println(pstmt.getUpdateCount() + " rows updated");
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing delete statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean update(Genero object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(GeneroDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getNomeGenero());          // The first ? is for EMPLOYEE_ID
            pstmt.setInt(2, object.getId());
            pstmt.execute();

            result = pstmt.getUpdateCount() > 0;
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing update statement ", sqle);
        }

        return result;
    }

    @Override
    public Genero getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Genero genero = null;

        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM GENEROS where " + COL_ID_GENERO + " = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet generosRs = pstmt.getResultSet();
            while (generosRs.next()) {
                genero = new Genero(
                        generosRs.getString(COL_NOME));
                genero.setIdGenero(generosRs.getInt(COL_ID_GENERO));
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Genero by id ", sqle);

        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return genero;
    }

    @Override
    public boolean exists(Genero object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM GENEROS where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_GENERO + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getNomeGenero()) != null) {
                statement.append(COL_NOME + "=" + tmpString);
                fields++;
            }

            if (fields > 0) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet generoRs = stmt.getResultSet();
                result = generoRs.next();
            }
        } catch (SQLException sqle) {
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return result;
    }

    @Override
    public List<Genero> getByCriteria(Genero object) throws GenericDAOException {
        List<Genero> generos = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement generoStmt = null;
        GeneroDAO generoDao = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_GENERO + " FROM GENEROS where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_GENERO + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getNomeGenero()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NOME + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet generoRs = stmt.executeQuery(statement.toString());

                Genero genero = null;

                while (generoRs.next()) {
                    genero = this.getById(generoRs.getInt(COL_ID_GENERO));
                    generos.add(genero);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Genero by criteria", sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (generoStmt != null) {
                    generoStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ActorDAO.class.getName()).log(Level.INFO, null, ex);
            }
        }

        return generos;
    }
}
