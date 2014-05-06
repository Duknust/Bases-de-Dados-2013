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
import org.bmdb.dao.domain.Noticia;
import org.bmdb.dao.domain.Produtora;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.GenericDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class NoticiaDAO extends GenericDAO<Noticia> implements IGenericDAO<Noticia> {

    /**
     * Column names
     */
    private final static String COL_ID_NOTICIA = "ID_NOTICIA";
    private final static String COL_FONTE = "FONTE";
    private final static String COL_DATA = "DATA";
    private final static String COL_TITULO = "TITULO";
    private final static String COL_SINOPSE = "SINOPSE";
    private final static String COL_CORPO = "CORPO";
    private final static String COL_IMAGEM = "IMAGEM";
    private final static String COL_VIDEO = "VIDEO";
    private final static String COL_ID_FILME = "ID_FILME";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into NOTICIAS ("
            + COL_FONTE + ","
            + COL_DATA + ","
            + COL_TITULO + ","
            + COL_SINOPSE + ","
            + COL_CORPO + ","
            + COL_IMAGEM + ","
            + COL_VIDEO + ","
            + COL_ID_FILME + ")"
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";
    static String DELETE_STATEMENT = "delete from NOTICIAS "
            + "where ID_NOTICIA=?";
    static String UPDATE_STATEMENT = "update NOTICIAS set "
            + COL_FONTE + "=?, "
            + COL_DATA + "=?, "
            + COL_TITULO + "=?, "
            + COL_SINOPSE + "=?, "
            + COL_CORPO + "=?, "
            + COL_IMAGEM + "=?, "
            + COL_VIDEO + "=?, "
            + COL_ID_FILME + "=? "
            + "where ID_NOTICIA=?";

    public NoticiaDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Noticia> getAll() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        List<Noticia> noticias = new ArrayList<Noticia>();
        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM noticias");
            pstmt.execute();
            ResultSet noticiasRs = pstmt.getResultSet();
            Noticia noticia = null;
            while (noticiasRs.next()) {
                noticia = new Noticia(
                        noticiasRs.getString(COL_FONTE),
                        noticiasRs.getString(COL_DATA),
                        noticiasRs.getString(COL_TITULO),
                        noticiasRs.getString(COL_SINOPSE),
                        noticiasRs.getString(COL_CORPO),
                        noticiasRs.getString(COL_IMAGEM),
                        noticiasRs.getString(COL_VIDEO),
                        noticiasRs.getInt(COL_ID_FILME),
                        null);
                noticia.setIdNoticia(noticiasRs.getInt(COL_ID_NOTICIA));
                noticias.add(noticia);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui obter a lista de todas as Noticias", sqle);
        }
        return noticias;
    }

    @Override
    public boolean insert(Noticia object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;

        try {
            Connection conn = this.cb.getConnection();

            pstmt = conn.prepareStatement(NoticiaDAO.INSERT_STATEMENT, new String[]{COL_ID_NOTICIA});
            pstmt.setString(1, object.getFonte());
            pstmt.setString(2, object.getData());
            pstmt.setString(3, object.getTitulo());
            pstmt.setString(4, object.getSinopse());
            pstmt.setString(5, object.getCorpo());
            pstmt.setString(6, object.getImagem());
            pstmt.setString(7, object.getVideo());
            pstmt.setInt(8, object.getIdFilme());

            // Do the insertion, check number of rows updated
            System.out.println("Query: " + pstmt.toString());
            pstmt.execute();
            ResultSet noticiaKeys = pstmt.getGeneratedKeys();
            int idNoticia = -1;
            if ((result = pstmt.getUpdateCount() != 0) && noticiaKeys.next()) {
                idNoticia = noticiaKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted noticia with ID: " + idNoticia);
                object.setIdNoticia(idNoticia);
                pstmt.close();
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);
        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(NoticiaDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean delete(Noticia object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(NoticiaDAO.DELETE_STATEMENT);
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
    public boolean update(Noticia object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(NoticiaDAO.UPDATE_STATEMENT);

            pstmt.setString(1, object.getFonte());
            pstmt.setString(2, object.getData());
            pstmt.setString(3, object.getTitulo());
            pstmt.setString(4, object.getSinopse());
            pstmt.setString(5, object.getCorpo());
            pstmt.setString(6, object.getImagem());
            pstmt.setString(7, object.getVideo());
            pstmt.setInt(8, object.getIdFilme());
            pstmt.setInt(9, object.getId());
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
    public Noticia getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Noticia noticia = null;
        Connection conn = cb.getConnection();

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM NOTICIAS where " + COL_ID_NOTICIA + " = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet noticiaRs = pstmt.getResultSet();
            while (noticiaRs.next()) {
                noticia = new Noticia(
                        noticiaRs.getString(COL_FONTE),
                        noticiaRs.getString(COL_DATA),
                        noticiaRs.getString(COL_TITULO),
                        noticiaRs.getString(COL_SINOPSE),
                        noticiaRs.getString(COL_CORPO),
                        noticiaRs.getString(COL_IMAGEM),
                        noticiaRs.getString(COL_VIDEO),
                        noticiaRs.getInt(COL_ID_FILME),
                        null);
                noticia.setIdNoticia(noticiaRs.getInt(COL_ID_NOTICIA));
            }
        } catch (SQLException sqle) {
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return noticia;
    }

    @Override
    public boolean exists(Noticia object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM NOTICIAS where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_NOTICIA + "=" + tmpInt);
                fields++;
            }
            if ((tmpString = object.getFonte()) != null) {
                statement.append(COL_FONTE + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getData()) != null) {
                statement.append(COL_DATA + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getTitulo()) != null) {
                statement.append(COL_TITULO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getSinopse()) != null) {
                statement.append(COL_SINOPSE + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getCorpo()) != null) {
                statement.append(COL_CORPO + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getImagem()) != null) {
                statement.append(COL_IMAGEM + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getVideo()) != null) {
                statement.append(COL_VIDEO + "=" + tmpString);
                fields++;
            }
            if ((tmpInt = object.getIdFilme()) > 0) {
                statement.append(COL_ID_FILME + "=" + tmpInt);
                fields++;
            }
            if (fields > 0) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet noticiaRs = stmt.getResultSet();
                result = noticiaRs.next();
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error searching for noticia", sqle);
        }
        // PROCESSAR DataSource e criar Filmees e inserir na lista a devolver

        return result;
    }

    @Override
    public List<Noticia> getByCriteria(Noticia object) throws GenericDAOException {
        List<Noticia> noticias = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement produtoraStmt = null;
        NoticiaDAO noticiaDao = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_NOTICIA + " FROM Noticias where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_NOTICIA + "=" + tmpInt);
                fields++;
            }

            if ((tmpString = object.getFonte()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_FONTE + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getData()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_DATA + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getTitulo()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_TITULO + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getSinopse()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_SINOPSE + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getCorpo()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_CORPO + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getImagem()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_IMAGEM + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }
            if ((tmpString = object.getVideo()) != null) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_VIDEO + ") LIKE UPPER('%" + tmpString + "%')");
                fields++;
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet noticiaRs = stmt.executeQuery(statement.toString());

                Noticia noticia = null;

                while (noticiaRs.next()) {
                    noticia = this.getById(noticiaRs.getInt(COL_ID_NOTICIA));
                    noticias.add(noticia);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Noticia by criteria", sqle);
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

        return noticias;
    }
}
