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
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.domain.Filme;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class ActorDAO extends GenericDAO<Actor> implements IGenericDAO<Actor> {

    /**
     * Column names
     */
    private final static String COL_ID_ACTOR = "ID_ACTOR";
    private final static String COL_NOME = "NOME";
    private final static String COL_DATA_NASCIMENTO = "DATA_NASCIMENTO";
    private final static String COL_DATA_MORTE = "DATA_MORTE";
    private final static String COL_PREMIOS_NOMEADO = "PREMIOS_NOMEADO";
    private final static String COL_PREMIOS_VENCIDOS = "PREMIOS_VENCIDOS";
    private final static String COL_OSCARES = "OSCARES";
    private final static String COL_NACIONALIDADE = "NACIONALIDADE";
    private final static String COL_SEXO = "SEXO";
    private final static String COL_IMAGEM = "IMAGEM";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into ACTORES ( "
            + COL_NOME + ","
            + COL_DATA_NASCIMENTO + ","
            + COL_DATA_MORTE + ","
            + COL_NACIONALIDADE + ","
            + COL_PREMIOS_NOMEADO + ","
            + COL_PREMIOS_VENCIDOS + ","
            + COL_OSCARES + ","
            + COL_SEXO + ","
            + COL_IMAGEM + ") "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static String DELETE_STATEMENT = "delete from ACTORES "
            + "where ID_ACTOR=?";
    static String UPDATE_STATEMENT = "update ACTORES set "
            + COL_NOME + "=?, "
            + COL_DATA_NASCIMENTO + "=?, "
            + COL_DATA_MORTE + "=?,"
            + COL_NACIONALIDADE + "=?, "
            + COL_PREMIOS_NOMEADO + "=?, "
            + COL_PREMIOS_VENCIDOS + "=?, "
            + COL_OSCARES + "=?, "
            + COL_SEXO + "=?, "
            + COL_IMAGEM + "=? "
            + "where ID_ACTOR=?";

    public ActorDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Actor> getAll() throws GenericDAOException {
        List<Actor> actores = new ArrayList<Actor>();
        PreparedStatement pstmt = null;
        ResultSet actoresRs = null;

        try {
            Connection conn = cb.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM actores");

            try {
                pstmt.execute();
                actoresRs = pstmt.getResultSet();
                Actor actor = null;
                while (actoresRs.next()) {
                    actor = new Actor(
                            actoresRs.getString(COL_NOME),
                            actoresRs.getString(COL_DATA_NASCIMENTO),
                            actoresRs.getString(COL_DATA_MORTE),
                            actoresRs.getString(COL_NACIONALIDADE),
                            actoresRs.getInt(COL_PREMIOS_NOMEADO),
                            actoresRs.getInt(COL_PREMIOS_VENCIDOS),
                            actoresRs.getInt(COL_OSCARES),
                            actoresRs.getString(COL_SEXO),
                            actoresRs.getString(COL_IMAGEM));
                    actor.setIdActor(actoresRs.getInt(COL_ID_ACTOR));
                    actores.add(actor);
                }
            } catch (SQLException ex) {
                throw new StatementExecuteDAOException("Nao consegui obter a lista de todos os Actores", ex);
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (actoresRs != null) {
                        actoresRs.close();
                    }
                } catch (SQLException e) {
                }
            }
        } catch (SQLException sqle) {
            throw new DatabaseConnectionDAOException("Nao consegui abrir ligacao a BD", sqle);
        } catch (GenericDAOException gde) {
            throw gde;
        }

        return actores;
    } //done

    @Override
    public boolean insert(Actor object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();

            pstmt = conn.prepareStatement(ActorDAO.INSERT_STATEMENT, new String[]{COL_ID_ACTOR});


            pstmt.setString(1, object.getNomeActor());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getDataNascimentoActor());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getDataMorteActor());   // The second ? is for FIRST_NAME
            pstmt.setString(4, object.getNacionalidadeActor());
            pstmt.setInt(5, object.getPremiosNomeadoActor()); // The 4th ? is for EMAIL
            pstmt.setInt(6, object.getPremiosVencidosActor());
            pstmt.setInt(7, object.getOscaresVencidos());
            pstmt.setString(8, object.getSexo());
            pstmt.setString(9, object.getImagem());

            // Do the insertion, check number of rows updated
            pstmt.execute();
            //Statement s = conn.createStatement();
            //ResultSet filmeKeys = s.executeQuery("select last_insert_rowid();");
            ResultSet actorKeys = pstmt.getGeneratedKeys();
            int idActor = -1;
            if ((result = pstmt.getUpdateCount() != 0) && actorKeys.next()) {
                idActor = actorKeys.getInt(1);
                System.out.println(result + " rows updated");
                System.out.println("Inserted actor with ID: " + idActor);
                object.setIdActor(idActor);
                pstmt.close();
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean delete(Actor object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(ActorDAO.DELETE_STATEMENT);
            pstmt.setInt(1, object.getId());

            // Do the insertion, check number of rows updated
            pstmt.execute();
            result = pstmt.getUpdateCount() != 0;
            System.out.println(pstmt.getUpdateCount() + " rows updated");
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing delete statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean update(Actor object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(ActorDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getNomeActor());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getDataNascimentoActor());   // The second ? is for FIRST_NAME
            pstmt.setString(3, object.getDataMorteActor());   // The second ? is for FIRST_NAME
            pstmt.setString(4, object.getNacionalidadeActor());
            pstmt.setInt(5, object.getPremiosNomeadoActor()); // The 4th ? is for EMAIL
            pstmt.setInt(6, object.getPremiosVencidosActor());
            pstmt.setInt(7, object.getOscaresVencidos());
            pstmt.setString(8, object.getSexo());
            pstmt.setString(9, object.getImagem());
            pstmt.setInt(10, object.getId());
            pstmt.execute();
            result = pstmt.getUpdateCount() != 0;

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing update statement ", sqle);
        }
        return result;
    }

    @Override
    public Actor getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Actor actor = null;
        PreparedStatement pstmt = null, filmesStmt = null;
        Connection conn = cb.getConnection();
        try {
            pstmt = conn.prepareStatement("SELECT * FROM ACTORES where " + COL_ID_ACTOR + " = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet actorRs = pstmt.getResultSet();

            while (actorRs.next()) {
                actor = new Actor(
                        actorRs.getString(COL_NOME),
                        actorRs.getString(COL_DATA_NASCIMENTO),
                        actorRs.getString(COL_DATA_MORTE),
                        actorRs.getString(COL_NACIONALIDADE),
                        actorRs.getInt(COL_PREMIOS_NOMEADO),
                        actorRs.getInt(COL_PREMIOS_VENCIDOS),
                        actorRs.getInt(COL_OSCARES),
                        actorRs.getString(COL_SEXO),
                        actorRs.getString(COL_IMAGEM));
                actor.setIdActor(actorRs.getInt(COL_ID_ACTOR));
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
                throw new StatementExecuteDAOException("Error getting actor by id ", sqle);
            }
        }

        return actor;
    }

    @Override
    public boolean exists(Actor object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM ACTORES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_ACTOR + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getNomeActor()) != null) {
                statement.append(COL_NOME + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getDataNascimentoActor()) != null) {
                statement.append(COL_DATA_NASCIMENTO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getDataMorteActor()) != null) {
                statement.append(COL_DATA_MORTE + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getNacionalidadeActor()) != null) {
                statement.append(COL_NACIONALIDADE + "=" + tmpString);
                fields++;
            }
            if ((tmpInt = object.getPremiosNomeadoActor()) > 0) {
                statement.append(COL_PREMIOS_NOMEADO + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getPremiosVencidosActor()) > 0) {
                statement.append(COL_PREMIOS_VENCIDOS + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getOscaresVencidos()) > 0) {
                statement.append(COL_OSCARES + "=" + tmpInt);
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
                throw new StatementExecuteDAOException("Error searching for actor", sqle);
            }
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return result;
    }

    @Override
    public List<Actor> getByCriteria(Actor object) throws GenericDAOException {
        List<Actor> actores = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement filmesStmt = null;
        FilmeDAO filmeDao = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_ACTOR + " FROM ACTORES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_ACTOR + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getNomeActor()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NOME + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getDataNascimentoActor()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_DATA_NASCIMENTO + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getDataMorteActor()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_DATA_MORTE + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getNacionalidadeActor()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_NACIONALIDADE + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpInt = object.getPremiosNomeadoActor()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_PREMIOS_NOMEADO + ") LIKE UPPER('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getPremiosVencidosActor()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_PREMIOS_VENCIDOS + ") LIKE UPPER('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getOscaresVencidos()) > 0) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_OSCARES + ") LIKE UPPER('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpString = object.getSexo()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_SEXO + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getImagem()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_IMAGEM + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet actorRs = stmt.executeQuery(statement.toString());

                Actor actor = null;
                while (actorRs.next()) {
                    actor = this.getById(actorRs.getInt(COL_ID_ACTOR));
                    actores.add(actor);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Actor by criteria " + sqle.getMessage(), sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (filmesStmt != null) {
                    filmesStmt.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return actores;
    }

    public List<Filme> getFilmesActor(Actor actor) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        List<Filme> filmesActor = null;
        try {
            PreparedStatement filmesStmt = null;
            Connection conn = cb.getConnection();
            FilmeDAO filmeDao = new FilmeDAO(cb);
            filmesStmt = conn.prepareStatement("SELECT * FROM actores_filme where ID_ACTOR =?");
            filmesStmt.setInt(1, actor.getId());
            ResultSet actoresFilmeRs = filmesStmt.executeQuery();
            filmesActor = new ArrayList<>();

            while (actoresFilmeRs.next()) {
                filmesActor.add(filmeDao.getById(actoresFilmeRs.getInt("ID_FILME")));
            }

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting FilmesActor", sqle);
        }
        return filmesActor;
    }
}
