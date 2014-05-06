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
import org.bmdb.dao.domain.Utilizador;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class UtilizadorDAO extends GenericDAO<Utilizador> implements IGenericDAO<Utilizador> {

    /**
     * Column names
     */
    private final static String COL_ID_UTILIZADOR = "ID_UTILIZADOR";
    private final static String COL_USERNAME = "USERNAME";
    private final static String COL_EMAIL = "EMAIL";
    private final static String COL_PASSWORD = "PASSWORD";
    private final static String COL_DATA_NASCIMENTO = "DATA_NASCIMENTO";
    private final static String COL_AVATAR = "AVATAR";
    private final static String COL_ESTADO = "ESTADO";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into UTILIZADORES ( "
            + COL_USERNAME + ","
            + COL_EMAIL + ","
            + COL_DATA_NASCIMENTO + ","
            + COL_AVATAR + ","
            + COL_ESTADO + ") "
            + "values (?, ?, ?, ?, ?)";
    static String DELETE_STATEMENT = "delete from UTILIZADORES "
            + "where ID_UTILIZADOR=?";
    static String UPDATE_STATEMENT = "update UTILIZADORES set "
            + COL_USERNAME + "=?, "
            + COL_EMAIL + "=?, "
            + COL_DATA_NASCIMENTO + "=?,"
            + COL_AVATAR + "=?, "
            + COL_ESTADO + "=? "
            + "where ID_UTILIZADOR=?";

    public UtilizadorDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Utilizador> getAll() throws GenericDAOException {
        List<Utilizador> utilizadores = new ArrayList<Utilizador>();
        PreparedStatement pstmt = null;
        ResultSet utilizadoresRs = null;

        try {
            Connection conn = cb.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM utilizadores");

            try {
                pstmt.execute();
                utilizadoresRs = pstmt.getResultSet();
                Utilizador utilizador = null;
                while (utilizadoresRs.next()) {
                    utilizador = new Utilizador(
                            utilizadoresRs.getString(COL_USERNAME),
                            utilizadoresRs.getString(COL_EMAIL),
                            utilizadoresRs.getString(COL_DATA_NASCIMENTO),
                            utilizadoresRs.getString(COL_AVATAR),
                            utilizadoresRs.getInt(COL_ESTADO));
                    utilizador.setIdUtilizador(utilizadoresRs.getInt(COL_ID_UTILIZADOR));
                    utilizadores.add(utilizador);
                }
            } catch (SQLException ex) {
                throw new StatementExecuteDAOException("Nao consegui obter a lista de todos os Utilizadores", ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (utilizadoresRs != null) {
                        utilizadoresRs.close();
                    }
                } catch (SQLException e) {
                }
            }
        } catch (SQLException sqle) {
            throw new DatabaseConnectionDAOException("Nao consegui abrir ligacao a BD", sqle);
        } catch (GenericDAOException gde) {
            throw gde;
        }

        return utilizadores;
    } //done

    @Override
    public boolean insert(Utilizador object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();

            pstmt = conn.prepareStatement(UtilizadorDAO.INSERT_STATEMENT, new String[]{COL_ID_UTILIZADOR});


            pstmt.setString(1, object.getUsername());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getEmail());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getDataNascimento());   // The second ? is for FIRST_NAME
            pstmt.setString(4, object.getAvatar());
            pstmt.setInt(5, object.getEstado());

            // Do the insertion, check number of rows updated
            pstmt.execute();
            //Statement s = conn.createStatement();
            //ResultSet filmeKeys = s.executeQuery("select last_insert_rowid();");
            ResultSet utilizadorKeys = pstmt.getGeneratedKeys();
            int idUtilizador = -1;
            if ((result = pstmt.getUpdateCount() != 0) && utilizadorKeys.next()) {
                idUtilizador = utilizadorKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted utilizador with ID: " + idUtilizador);
                object.setIdUtilizador(idUtilizador);
                pstmt.close();
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException sqle) {
                throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
            }
        }
        return result;
    }

    @Override
    public boolean delete(Utilizador object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(UtilizadorDAO.DELETE_STATEMENT);
            pstmt.setInt(1, object.getId());

            // Do the insertion, check number of rows updated
            pstmt.execute();
            result = pstmt.getUpdateCount() != 0;
            System.out.println(pstmt.getUpdateCount() + " rows updated");
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing delete statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean update(Utilizador object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(UtilizadorDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getUsername());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getEmail());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getDataNascimento());   // The second ? is for FIRST_NAME
            pstmt.setString(4, object.getAvatar());
            pstmt.setInt(5, object.getEstado());
            pstmt.setInt(6, object.getId());
            pstmt.execute();
            result = pstmt.getUpdateCount() != 0;

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(UtilizadorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing update statement ", sqle);
        }
        return result;
    }

    @Override
    public Utilizador getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Utilizador utilizador = null;
        PreparedStatement pstmt = null, filmesStmt = null;
        Connection conn = cb.getConnection();
        try {
            pstmt = conn.prepareStatement("SELECT * FROM UTILIZADORES where " + COL_ID_UTILIZADOR + " = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet actorRs = pstmt.getResultSet();

            while (actorRs.next()) {
                utilizador = new Utilizador(
                        actorRs.getString(COL_USERNAME),
                        actorRs.getString(COL_EMAIL),
                        actorRs.getString(COL_DATA_NASCIMENTO),
                        actorRs.getString(COL_AVATAR),
                        actorRs.getInt(COL_ESTADO));
                utilizador.setIdUtilizador(actorRs.getInt(COL_ID_UTILIZADOR));
            }
        } catch (SQLException sqle) {
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (filmesStmt != null) {
                    filmesStmt.close();
                }
            } catch (SQLException sqle) {
                throw new StatementExecuteDAOException("Error getting utilizador by id ", sqle);
            }
        }

        return utilizador;
    }

    @Override
    public boolean exists(Utilizador object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM UTILIZADORES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_UTILIZADOR + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getUsername()) != null) {
                statement.append(COL_USERNAME + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getEmail()) != null) {
                statement.append(COL_EMAIL + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getDataNascimento()) != null) {
                statement.append(COL_DATA_NASCIMENTO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getAvatar()) != null) {
                statement.append(COL_AVATAR + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getAvatar()) != null) {
                statement.append(COL_ESTADO + "=" + tmpString);
                fields++;
            }


            if (fields > 0) {
                stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet actorRs = stmt.getResultSet();
                result = actorRs.next();
            }
        } catch (SQLException sqle) {
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }

            } catch (SQLException sqle) {
                throw new StatementExecuteDAOException("Error searching for utilizador", sqle);
            }
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return result;
    }

    @Override
    public List<Utilizador> getByCriteria(Utilizador object) throws GenericDAOException {
        List<Utilizador> utilizadores = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement filmesStmt = null;
        UtilizadorDAO utilizadorDAO = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_UTILIZADOR + " FROM UTILIZADORES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_UTILIZADOR + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getUsername()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + COL_USERNAME + " LIKE '%" + tmpString + "%'");
                fields++;
            }
            if ((tmpString = object.getEmail()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + COL_EMAIL + " LIKE '%" + tmpString + "%'");
                fields++;
            }
            if ((tmpString = object.getDataNascimento()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + COL_DATA_NASCIMENTO + " LIKE '%" + tmpString + "%'");
                fields++;
            }
            if ((tmpString = object.getAvatar()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + COL_AVATAR + " LIKE '%" + tmpString + "%'");
                fields++;
            }
            if ((tmpInt = object.getEstado()) >= 0) {
                statement.append(((fields != 0) ? " AND " : "") + COL_ESTADO + " LIKE '%" + tmpString + "%'");
                fields++;
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet utilizadorRS = stmt.executeQuery(statement.toString());

                Utilizador utilizador = null;

                while (utilizadorRS.next()) {
                    utilizador = this.getById(utilizadorRS.getInt(COL_ID_UTILIZADOR));
                    utilizadores.add(utilizador);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Utilizador by criteria", sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (filmesStmt != null) {
                    filmesStmt.close();
                }
            } catch (SQLException sqle) {
                throw new StatementExecuteDAOException("Error closing stmt by criteria", sqle);
            }
        }

        return utilizadores;
    }
}
