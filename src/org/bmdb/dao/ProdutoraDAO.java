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
import org.bmdb.dao.domain.Produtora;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class ProdutoraDAO extends GenericDAO<Produtora> implements IGenericDAO<Produtora> {

    /**
     * Column names
     */
    private final static String COL_ID_PRODUTORA = "ID_PRODUTORA";
    private final static String COL_NOME = "NOME";
    private final static String COL_NACIONALIDADE = "NACIONALIDADE";
    private final static String COL_IMAGEM = "IMAGEM";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into PRODUTORAS ("
            + COL_NOME + ","
            + COL_NACIONALIDADE + ","
            + COL_IMAGEM + ")"
            + "values (?, ?, ?)";
    static String DELETE_STATEMENT = "delete from PRODUTORAS "
            + "where ID_PRODUTORA=?";
    static String UPDATE_STATEMENT = "update PRODUTORAS set "
            + COL_NOME + "=?, "
            + COL_NACIONALIDADE + "=?, "
            + COL_IMAGEM + "=? "
            + "where ID_PRODUTORA=?";

    public ProdutoraDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Produtora> getAll() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        List<Produtora> produtoras = new ArrayList<Produtora>();
        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM produtoras");
            pstmt.execute();
            ResultSet produtorasRs = pstmt.getResultSet();
            Produtora produtora = null;
            while (produtorasRs.next()) {

                produtora = new Produtora(
                        produtorasRs.getString(COL_NOME),
                        produtorasRs.getString(COL_NACIONALIDADE),
                        produtorasRs.getString(COL_IMAGEM));
                produtora.setIdProdutora(produtorasRs.getInt(COL_ID_PRODUTORA));
                produtoras.add(produtora);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui obter a lista de todas as Produtoras", sqle);
        }
        return produtoras;
    }

    @Override
    public boolean insert(Produtora object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            Connection conn = this.cb.getConnection();

            pstmt = conn.prepareStatement(ProdutoraDAO.INSERT_STATEMENT, new String[]{COL_ID_PRODUTORA});
            pstmt.setString(1, object.getNomeProdutora());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getNacionalidadeProdutora());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getImagem());   // The second ? is for FIRST_NAME

            // Do the insertion, check number of rows updated
            System.out.println("Query: " + pstmt.toString());
            pstmt.execute();
            ResultSet produtoraKeys = pstmt.getGeneratedKeys();
            int idProdutora = -1;
            if ((result = pstmt.getUpdateCount() != 0) && produtoraKeys.next()) {
                idProdutora = produtoraKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted produtora with ID: " + idProdutora);
                object.setIdProdutora(idProdutora);
                pstmt.close();
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ProdutoraDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean delete(Produtora object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(ProdutoraDAO.DELETE_STATEMENT);
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
    public boolean update(Produtora object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(ProdutoraDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getNomeProdutora());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getNacionalidadeProdutora());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getImagem());   // The second ? is for FIRST_NAME
            pstmt.setInt(4, object.getId());
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
    public Produtora getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Produtora produtora = null;
        Connection conn = cb.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM PRODUTORAS where " + COL_ID_PRODUTORA + " = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet produtoraRs = pstmt.getResultSet();
            while (produtoraRs.next()) {
                produtora = new Produtora(
                        produtoraRs.getString(COL_NOME),
                        produtoraRs.getString(COL_NACIONALIDADE),
                        produtoraRs.getString(COL_IMAGEM));
                produtora.setIdProdutora(produtoraRs.getInt(COL_ID_PRODUTORA));
            }
        } catch (SQLException sqle) {
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return produtora;
    }

    @Override
    public boolean exists(Produtora object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM PRODUTORAS where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_PRODUTORA + "=" + tmpInt);
                fields++;
            }
            if ((tmpString = object.getNomeProdutora()) != null) {
                statement.append(COL_NOME + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getNacionalidadeProdutora()) != null) {
                statement.append(COL_NACIONALIDADE + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getImagem()) != null) {
                statement.append(COL_IMAGEM + "=" + tmpString);
                fields++;
            }
            if (fields > 0) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet produtoraRs = stmt.getResultSet();
                result = produtoraRs.next();
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error searching for produtora", sqle);
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return result;
    }

    @Override
    public List<Produtora> getByCriteria(Produtora object) throws GenericDAOException {
        List<Produtora> produtoras = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement produtoraStmt = null;
        ProdutoraDAO produtoraDao = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_PRODUTORA + " FROM Produtoras where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_PRODUTORA + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getNomeProdutora()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NOME + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getNacionalidadeProdutora()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NACIONALIDADE + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getImagem()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_IMAGEM + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet produtoraRs = stmt.executeQuery(statement.toString());

                Produtora produtora = null;

                while (produtoraRs.next()) {
                    produtora = this.getById(produtoraRs.getInt(COL_ID_PRODUTORA));
                    produtoras.add(produtora);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Produtora by criteria", sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (produtoraStmt != null) {
                    produtoraStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ActorDAO.class.getName()).log(Level.INFO, null, ex);
            }
        }

        return produtoras;
    }
}
