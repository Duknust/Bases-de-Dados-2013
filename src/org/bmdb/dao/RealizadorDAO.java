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
import org.bmdb.dao.domain.Realizador;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class RealizadorDAO extends GenericDAO<Realizador> implements IGenericDAO<Realizador> {

    /**
     * Column names
     */
    private final static String COL_ID_REALIZADOR = "ID_REALIZADOR";
    private final static String COL_NOME = "NOME";
    private final static String COL_DATA_NASCIMENTO = "DATA_NASCIMENTO";
    private final static String COL_NACIONALIDADE = "NACIONALIDADE";
    private final static String COL_SEXO = "SEXO";
    private final static String COL_IMAGEM = "IMAGEM";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into REALIZADORES ("
            + COL_NOME + ","
            + COL_DATA_NASCIMENTO + ","
            + COL_NACIONALIDADE + ","
            + COL_SEXO + ","
            + COL_IMAGEM + ") "
            + "values (?, ?, ?, ?, ?)";
    static String DELETE_STATEMENT = "delete from REALIZADORES "
            + "where ID_REALIZADOR=?";
    static String UPDATE_STATEMENT = "update REALIZADORES set "
            + COL_NOME + "=?, "
            + COL_DATA_NASCIMENTO + "=?, "
            + COL_NACIONALIDADE + "=?, "
            + COL_SEXO + "=?, "
            + COL_IMAGEM + "=? "
            + "where ID_REALIZADOR=?";

    public RealizadorDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Realizador> getAll() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        List<Realizador> realizadores = new ArrayList<Realizador>();
        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM realizadores");
            pstmt.execute();
            ResultSet realizadorRs = pstmt.getResultSet();

            Realizador realizador = null;
            while (realizadorRs.next()) {
                realizador = new Realizador(
                        realizadorRs.getString(COL_NOME),
                        realizadorRs.getString(COL_DATA_NASCIMENTO),
                        realizadorRs.getString(COL_NACIONALIDADE),
                        realizadorRs.getString(COL_SEXO),
                        realizadorRs.getString(COL_IMAGEM));
                realizador.setIdRealizador(realizadorRs.getInt(COL_ID_REALIZADOR));
                realizadores.add(realizador);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui obter a lista de todos os Realizadores", sqle);
        }
        return realizadores;
    }

    @Override
    public boolean insert(Realizador object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            Connection conn = this.cb.getConnection();

            pstmt = conn.prepareStatement(RealizadorDAO.INSERT_STATEMENT, new String[]{COL_ID_REALIZADOR});
            pstmt.setString(1, object.getNomeRealizador());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getDataNascimentoRealizador());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getNacionalidadeRealizador());
            pstmt.setString(4, object.getSexo()); // The 4th ? is for EMAIL
            pstmt.setString(5, object.getImagem()); // The 4th ? is for EMAIL

            // Do the insertion, check number of rows updated
            System.out.println("Query: " + pstmt.toString());
            pstmt.executeUpdate();
            ResultSet realizadorKeys = pstmt.getGeneratedKeys();
            int idRealizador = -1;
            if (result = (pstmt.getUpdateCount() != 0) && realizadorKeys.next()) {
                idRealizador = realizadorKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted realizador with ID: " + idRealizador);
                object.setIdRealizador(idRealizador);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(RealizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
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
    public boolean delete(Realizador object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(RealizadorDAO.DELETE_STATEMENT);
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
    public boolean update(Realizador object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(RealizadorDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getNomeRealizador());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getDataNascimentoRealizador());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getNacionalidadeRealizador());
            pstmt.setString(4, object.getSexo()); // The 4th ? is for EMAIL
            pstmt.setString(5, object.getImagem()); // The 4th ? is for EMAIL
            pstmt.setInt(6, object.getId());
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
    public Realizador getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Realizador realizador = null;

        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM REALIZADORES where " + COL_ID_REALIZADOR + "=?");
            if (id != 0) {
                pstmt.setInt(1, id);
            } else {
                pstmt.setNull(1, java.sql.Types.NUMERIC);
            }
            ResultSet realizadorRs = pstmt.executeQuery();

            while (realizadorRs.next()) {
                realizador = new Realizador(
                        realizadorRs.getString(COL_NOME),
                        realizadorRs.getString(COL_DATA_NASCIMENTO),
                        realizadorRs.getString(COL_NACIONALIDADE),
                        realizadorRs.getString(COL_SEXO),
                        realizadorRs.getString(COL_IMAGEM));
                realizador.setIdRealizador(realizadorRs.getInt(COL_ID_REALIZADOR));
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            throw new StatementExecuteDAOException("Error getting realizador by id ", sqle);
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return realizador;
    }

    @Override
    public boolean exists(Realizador object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM REALIZADORES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_REALIZADOR + "=" + tmpInt);
                fields++;
            }
            if ((tmpString = object.getNomeRealizador()) != null) {
                statement.append(COL_NOME + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getDataNascimentoRealizador()) != null) {
                statement.append(COL_DATA_NASCIMENTO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getNacionalidadeRealizador()) != null) {
                statement.append(COL_NACIONALIDADE + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getSexo()) != null) {
                statement.append(COL_SEXO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getImagem()) != null) {
                statement.append(COL_IMAGEM + "=" + tmpString);
                fields++;
            }
            if (fields > 0) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet realizadorRs = stmt.getResultSet();
                result = realizadorRs.next();
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error searching for realizador", sqle);
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return result;
    }

    @Override
    public List<Realizador> getByCriteria(Realizador object) throws GenericDAOException {
        List<Realizador> realizadores = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement realizadorStmt = null;
        RealizadorDAO realizadorDao = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_REALIZADOR + " FROM REALIZADORES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_REALIZADOR + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getNomeRealizador()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NOME + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getDataNascimentoRealizador()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_DATA_NASCIMENTO + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getNacionalidadeRealizador()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NACIONALIDADE + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getSexo()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_SEXO + ") LIKE UPPER('%" + tmpString + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getImagem()) != null) {
                if (tmpString.length() < 30) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_IMAGEM + ") LIKE UPPER('%" + tmpString + "%')");
                    fields++;
                }
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet realizadorRs = stmt.executeQuery(statement.toString());

                Realizador realizador = null;

                while (realizadorRs.next()) {
                    realizador = this.getById(realizadorRs.getInt(COL_ID_REALIZADOR));
                    realizadores.add(realizador);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Realizador by criteria", sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (realizadorStmt != null) {
                    realizadorStmt.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ActorDAO.class.getName()).log(Level.INFO, null, ex);
            }
        }

        return realizadores;
    }
}
