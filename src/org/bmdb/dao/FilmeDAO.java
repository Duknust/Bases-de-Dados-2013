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
public class FilmeDAO extends GenericDAO<Filme> implements IGenericDAO<Filme> {

    /**
     * Column names
     */
    private final static String COL_ID_FILME = "ID_FILME";
    private final static String COL_TITULO = "TITULO";
    private final static String COL_ID_REALIZADOR = "ID_REALIZADOR";
    private final static String COL_ID_PRODUTORA = "ID_PRODUTORA";
    private final static String COL_ANO = "ANO";
    private final static String COL_BUDGET = "BUDGET";
    private final static String COL_GROSS = "GROSS";
    private final static String COL_PREMIOS_NOMEADO = "PREMIOS_NOMEADO";
    private final static String COL_PREMIOS_VENCIDOS = "PREMIOS_VENCIDOS";
    private final static String COL_OSCARES = "OSCARES";
    private final static String COL_RATING = "RATING";
    private final static String COL_SINOPSE = "SINOPSE";
    private final static String COL_TRAILER = "TRAILER";
    private final static String COL_POSTER = "POSTER";
    /**
     * Prepared statements
     */
    static String INSERT_STATEMENT = "insert into Filmes "
            + "( "
            + COL_TITULO + ","
            + COL_ID_REALIZADOR + ","
            + COL_ID_PRODUTORA + ","
            + COL_ANO + ","
            + COL_BUDGET + ","
            + COL_GROSS + ","
            + COL_PREMIOS_NOMEADO + ","
            + COL_PREMIOS_VENCIDOS + ","
            + COL_OSCARES + ","
            + COL_RATING + ","
            + COL_SINOPSE + ","
            + COL_TRAILER + ","
            + COL_POSTER + ") "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static String DELETE_STATEMENT = "delete from Filmes "
            + "where ID_FILME=?";
    static String UPDATE_STATEMENT = "update Filmes set "
            + COL_TITULO + "=?, "
            + COL_ID_REALIZADOR + "=?, "
            + COL_ID_PRODUTORA + "=?, "
            + COL_ANO + "=?, "
            + COL_BUDGET + "=?, "
            + COL_GROSS + "=?, "
            + COL_PREMIOS_NOMEADO + "=?, "
            + COL_PREMIOS_VENCIDOS + "=?, "
            + COL_OSCARES + "=?, "
            + COL_RATING + "=?, "
            + COL_SINOPSE + "=?"
            + COL_TRAILER + "=?, "
            + COL_POSTER + "=? "
            + "where ID_FILME=?";
    private static String ADD_RELATED_GENERO = "insert into lista_generos (ID_FILME,ID_GENERO) values (?, ?)";
    private static String DELETE_RELATED_GENEROS = "delete from lista_generos where ID_FILME=?";
    private String ADD_RELATED_ACTOR = "insert into Actores_filme (ID_FILME,ID_ACTOR,PERSONAGEM) values (?, ?, ?)";
    private static String DELETE_RELATED_ACTORES = "delete from Actores_filme where ID_FILME=?";

    public FilmeDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    @Override
    public List<Filme> getAll() throws DatabaseConnectionDAOException, StatementExecuteDAOException {

        Statement pstmt = null;
        List<Filme> filmes = new ArrayList<Filme>();
        RealizadorDAO realizadorDAO = new RealizadorDAO(this.cb);
        ProdutoraDAO produtoraDAO = new ProdutoraDAO(this.cb);
        ResultSet filmesRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            filmesRs = pstmt.executeQuery("SELECT * FROM filmes");
            Filme filme = null;
            while (filmesRs.next()) {
                int realizadorId = filmesRs.getInt(COL_ID_REALIZADOR);
                int produtoraId = filmesRs.getInt(COL_ID_PRODUTORA);

                Realizador r = realizadorDAO.getById(realizadorId);
                Produtora p = produtoraDAO.getById(produtoraId);

                filme = new Filme(
                        filmesRs.getString(COL_TITULO),
                        null, // Ir buscar apenas quando é um filme
                        r,
                        p,
                        filmesRs.getInt(COL_ANO),
                        filmesRs.getInt(COL_BUDGET),
                        filmesRs.getLong(COL_GROSS),
                        filmesRs.getInt(COL_PREMIOS_NOMEADO),
                        filmesRs.getInt(COL_PREMIOS_VENCIDOS),
                        filmesRs.getInt(COL_OSCARES),
                        null,
                        filmesRs.getInt(COL_RATING),
                        filmesRs.getString(COL_SINOPSE),
                        filmesRs.getString(COL_TRAILER),
                        filmesRs.getString(COL_POSTER));
                filme.setIdFilme(filmesRs.getInt(COL_ID_FILME));
                filmes.add(filme);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui abrir ligacao a BD", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (filmesRs != null) {
                    filmesRs.close();
                }
            } catch (SQLException sqle) {
                throw new DatabaseConnectionDAOException("Nao consegui abrir ligacao a BD", sqle);
            }
        }

        return filmes;
    }

    @Override
    public boolean insert(Filme object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        PreparedStatement pstmt = null;
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            pstmt = conn.prepareStatement(FilmeDAO.INSERT_STATEMENT, new String[]{COL_ID_FILME});

            pstmt.setString(1, object.getTitulo());          // The first ? is for EMPLOYEE_ID
            if (object.getRealizador() != null) {
                pstmt.setInt(2, object.getRealizador().getId());   // The second ? is for FIRST_NAME
            } else {
                pstmt.setNull(2, java.sql.Types.NUMERIC);
            }
            if (object.getProdutora() != null) {
                pstmt.setInt(3, object.getProdutora().getId());
            } else {
                pstmt.setNull(3, java.sql.Types.NUMERIC);
            }
            pstmt.setInt(4, object.getAno()); // The 4th ? is for EMAIL
            pstmt.setInt(5, object.getBudget());
            pstmt.setLong(6, object.getGross());
            pstmt.setInt(7, object.getPremiosNomeado());
            pstmt.setInt(8, object.getPremiosVencidos());
            pstmt.setInt(9, object.getOscares());
            pstmt.setInt(10, object.getRating());
            pstmt.setString(11, object.getSinopse());
            pstmt.setString(12, object.getTrailerUri());
            pstmt.setString(13, object.getPosterUri());

            // Do the insertion, check number of rows updated
            System.out.println("Query: " + pstmt.toString());
            pstmt.execute();
            //Statement s = conn.createStatement();
            //ResultSet filmeKeys = s.executeQuery("select last_insert_rowid();");
            ResultSet filmeKeys = pstmt.getGeneratedKeys();
            int idFilme = -1;
            if ((result = pstmt.getUpdateCount() != 0) && filmeKeys.next()) {
                idFilme = filmeKeys.getInt(1);
                System.out.println(pstmt.getUpdateCount() + " rows updated");
                System.out.println("Inserted filme with ID: " + idFilme);
                object.setIdFilme(idFilme);
                pstmt.close();
                List<Genero> generosFilme = null;
                if ((generosFilme = object.getGeneros()) != null) {
                    for (Genero g : generosFilme) {
                        if (g.getId() > 0) {
                            pstmt = conn.prepareStatement(ADD_RELATED_GENERO);
                            pstmt.setInt(1, object.getId());
                            pstmt.setInt(2, g.getId());
                            System.out.println("Query: " + pstmt.toString());
                            pstmt.execute();
                            pstmt.close();
                        }
                    }
                }

                List<Personagem> personagensFilme = null;
                if ((personagensFilme = object.getPersonagens()) != null) {
                    for (Personagem p : personagensFilme) {
                        if (p.getActor().getId() > 0) {
                            pstmt = conn.prepareStatement(ADD_RELATED_ACTOR);
                            pstmt.setInt(1, object.getId());
                            pstmt.setInt(2, p.getActor().getId());
                            pstmt.setString(3, p.getNome());
                            System.out.println("Query: " + pstmt.toString());
                            pstmt.execute();
                            pstmt.close();
                        }
                    }
                }


            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing insert statement ", sqle);

        } catch (DatabaseConnectionDAOException ex) {
            System.err.println(FilmeDAO.class.getName() + " [SEVERE] " + ex.toString());
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        return result;
    }

    @Override
    public boolean delete(Filme object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(FilmeDAO.DELETE_STATEMENT);
            pstmt.setInt(1, object.getId());
            pstmt.execute();
            System.out.println(pstmt.getUpdateCount() + " rows updated");
            pstmt.close();

            if ((result = pstmt.getUpdateCount() > 0)) {
                pstmt = conn.prepareStatement(DELETE_RELATED_GENEROS);
                pstmt.setInt(1, object.getId());
            }

        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing delete statement ", sqle);

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(FilmeDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    @Override
    public boolean update(Filme object) throws StatementExecuteDAOException, DatabaseConnectionDAOException {
        boolean result = false;
        try {
            Connection conn = this.cb.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(FilmeDAO.UPDATE_STATEMENT);

            pstmt.setInt(1, object.getId());          // The first ? is for EMPLOYEE_ID
            pstmt.setString(2, object.getTitulo());          // The first ? is for EMPLOYEE_ID
            pstmt.setInt(3, object.getRealizador().getId());   // The second ? is for FIRST_NAME
            pstmt.setInt(4, object.getProdutora().getId());
            pstmt.setInt(5, object.getAno()); // The 4th ? is for EMAIL
            pstmt.setInt(6, object.getBudget());
            pstmt.setLong(7, object.getGross());
            //           pstmt.setDouble(8, object.getPontuacao());
            //           pstmt.setInt(9, object.getPontuacaoMax());
            //           pstmt.setInt(10, object.getPontuacaoMin());
            //           pstmt.setInt(11, object.getNumeroVotos());
            pstmt.setInt(8, object.getPremiosNomeado());
            pstmt.setInt(9, object.getPremiosVencidos());
            pstmt.setInt(10, object.getRating());
            pstmt.setString(11, object.getSinopse());
            pstmt.setString(12, object.getTrailerUri());
            pstmt.setString(13, object.getPosterUri());
            pstmt.setInt(14, object.getId());
            pstmt.execute();

            result = pstmt.getUpdateCount() > 0;

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(FilmeDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error executing update statement ", sqle);
        }

        return result;
    }

    @Override
    public Filme getById(int id) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Filme filme = null;
        RealizadorDAO realizadorDAO = new RealizadorDAO(cb);
        ProdutoraDAO produtoraDAO = new ProdutoraDAO(cb);
        GeneroDAO generoDAO = new GeneroDAO(cb);
        ActorDAO actorDAO = new ActorDAO(cb);
        List<Genero> generosFilme = new ArrayList<>();
        List<Personagem> personagensFilme = new ArrayList<>();


        Connection conn = cb.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM lista_generos where " + COL_ID_FILME + " =?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet generosFilmeRs = pstmt.getResultSet();
            while (generosFilmeRs.next()) {
                generosFilme.add(generoDAO.getById(generosFilmeRs.getInt("ID_GENERO")));
            }
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT * FROM actores_filme where " + COL_ID_FILME + "=?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet personagensFilmeRs = pstmt.getResultSet();
            Actor actor = null;
            while (personagensFilmeRs.next()) {
                actor = actorDAO.getById(personagensFilmeRs.getInt("ID_ACTOR"));
                personagensFilme.add(new Personagem(personagensFilmeRs.getString("PERSONAGEM"), actor));
            }
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT * FROM filmes where " + COL_ID_FILME + " = ?");
            pstmt.setInt(1, id);
            pstmt.execute();
            ResultSet filmesRs = pstmt.getResultSet();
            while (filmesRs.next()) {
                int realizadorId = filmesRs.getInt(COL_ID_REALIZADOR);
                int produtoraId = filmesRs.getInt(COL_ID_PRODUTORA);

                Realizador r = realizadorDAO.getById(realizadorId);
                Produtora p = produtoraDAO.getById(produtoraId);

                filme = new Filme(
                        filmesRs.getString(COL_TITULO),
                        generosFilme, // Ir buscar apenas quando é um filme
                        r,
                        p,
                        filmesRs.getInt(COL_ANO),
                        filmesRs.getInt(COL_BUDGET),
                        filmesRs.getLong(COL_GROSS),
                        filmesRs.getInt(COL_PREMIOS_NOMEADO),
                        filmesRs.getInt(COL_PREMIOS_VENCIDOS),
                        filmesRs.getInt(COL_OSCARES),
                        personagensFilme,
                        filmesRs.getInt(COL_RATING),
                        filmesRs.getString(COL_SINOPSE),
                        filmesRs.getString(COL_TRAILER),
                        filmesRs.getString(COL_POSTER));
                filme.setIdFilme(filmesRs.getInt(COL_ID_FILME));
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting filme by id ", sqle);

        }

        return filme;
    }

    @Override
    public boolean exists(Filme object) throws GenericDAOException {
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        double tmpDouble = 0.0;
        long tmpLong = 0;
        boolean result = false;
        try {
            StringBuilder statement = new StringBuilder("SELECT * FROM FILMES where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_FILME + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getRealizador().getId()) > 0) {
                statement.append(COL_ID_REALIZADOR + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getProdutora().getId()) > 0) {
                statement.append(COL_ID_PRODUTORA + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getAno()) > 0) {
                statement.append(COL_ANO + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getBudget()) > 0) {
                statement.append(COL_BUDGET + "=" + tmpInt);
                fields++;
            }
            if ((tmpLong = object.getGross()) > 0) {
                statement.append(COL_GROSS + "=" + tmpLong);
                fields++;
            }
            if ((tmpInt = object.getPremiosNomeado()) > 0) {
                statement.append(COL_PREMIOS_NOMEADO + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getPremiosVencidos()) > 0) {
                statement.append(COL_PREMIOS_VENCIDOS + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getOscares()) > 0) {
                statement.append(COL_OSCARES + "=" + tmpInt);
                fields++;
            }
            if ((tmpInt = object.getRating()) > 0) {
                statement.append(COL_RATING + "=" + tmpInt);
                fields++;
            }
            if ((tmpString = object.getSinopse()) != null) {
                statement.append(COL_SINOPSE + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getTrailerUri()) != null) {
                statement.append(COL_TRAILER + "=" + tmpString);
                fields++;
            }
            if ((tmpString = object.getPosterUri()) != null) {
                statement.append(COL_POSTER + "=" + tmpString);
                fields++;
            }

            if (fields > 0) {
                Statement stmt = conn.createStatement();
                stmt.executeQuery(statement.toString());
                ResultSet filmeRs = stmt.getResultSet();
                result = filmeRs.next();

            }
        } catch (SQLException sqle) {
        }
        return result;
    }

    @Override
    public List<Filme> getByCriteria(Filme object) throws GenericDAOException {
        List<Filme> filmes = new ArrayList<>();
        Connection conn = cb.getConnection();
        int fields = 0;
        String tmpString = null;
        int tmpInt = 0;
        double tmpDouble = 0;
        long tmpLong = 0;
        boolean result = false;
        Statement stmt = null;
        PreparedStatement filmeStmt = null;

        try {
            StringBuilder statement = new StringBuilder("SELECT " + COL_ID_FILME + " FROM Filmes where ");
            if ((tmpInt = object.getId()) > 0) {
                statement.append(COL_ID_FILME + "=" + tmpInt);
                fields++;
            }
            if ((tmpString = object.getTitulo()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_TITULO + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            /*if ((tmpString = object.getRealizador()) != null) {
             statement.append(((fields != 0) ? " AND " : "") + COL_NOME + " LIKE '%" + tmpString + "%'");
             fields++;
             }
             if ((tmpString = object.getProdutora()) != null) {
             statement.append(((fields != 0) ? " AND " : "") + COL_DATA_NASCIMENTO + " LIKE '%" + tmpString + "%'");
             fields++;
             }*/
            if ((tmpInt = object.getAno()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_ANO + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getBudget()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_BUDGET + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpLong = object.getGross()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_GROSS + ") LIKE ('%" + tmpLong + "%')");
                fields++;
            }
            if ((tmpInt = object.getPremiosNomeado()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_PREMIOS_NOMEADO + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getPremiosVencidos()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_PREMIOS_VENCIDOS + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getOscares()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_OSCARES + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpInt = object.getRating()) > -1) {
                statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_RATING + ") LIKE ('%" + tmpInt + "%')");
                fields++;
            }
            if ((tmpString = object.getSinopse()) != null) {
                if (tmpString.length() < 10) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_SINOPSE + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getTrailerUri()) != null) {
                if (tmpString.length() < 30) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_TRAILER + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }
            if ((tmpString = object.getPosterUri()) != null) {
                if (tmpString.length() < 30) {
                    statement.append(((fields != 0) ? " AND " : "") + "UPPER(" + COL_POSTER + ") LIKE ('%" + tmpString.toUpperCase() + "%')");
                    fields++;
                }
            }

            if (fields > 0) {
                stmt = conn.createStatement();
                ResultSet filmeRs = stmt.executeQuery(statement.toString());

                Filme filme = null;
                while (filmeRs.next()) {
                    filme = this.getById(filmeRs.getInt(COL_ID_FILME));
                    filmes.add(filme);
                }
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting Filme by criteria", sqle);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (filmeStmt != null) {
                    filmeStmt.close();
                }
            } catch (SQLException sqle) {
                throw new StatementExecuteDAOException("Error closing stmt by criteria", sqle);
            }
        }

        return filmes;
    }

    public List<Actor> getActoresFilme(Filme filme) throws StatementExecuteDAOException {
        List<Actor> actoresFilme = null;
        try {
            PreparedStatement actorStmt = null;
            Connection conn = cb.getConnection();
            ActorDAO actorDao = new ActorDAO(cb);
            actorStmt = conn.prepareStatement("SELECT * FROM actores_filme where ID_FILME =?");
            actorStmt.setInt(1, filme.getId());
            actorStmt.execute();
            actoresFilme = new ArrayList<>();
            ResultSet actoresFilmeRs = actorStmt.getResultSet();
            while (actoresFilmeRs.next()) {
                actoresFilme.add(actorDao.getById(actoresFilmeRs.getInt("ID_ACTOR")));
            }

        } catch (DatabaseConnectionDAOException ex) {
            Logger.getLogger(ActorDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Error getting ActoresFilme", sqle);
        }
        return actoresFilme;
    }

    public boolean adicionaActorFilme(Filme f, Personagem p) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        boolean res = false;
        PreparedStatement pstmt = null;
        try {

            Connection conn = cb.getConnection();
            if (p.getActor().getId() > 0) {
                pstmt = conn.prepareStatement(ADD_RELATED_ACTOR);
                pstmt.setInt(1, f.getId());
                pstmt.setInt(2, p.getActor().getId());
                pstmt.setString(3, p.getNome());
                System.out.println("Query: " + pstmt.toString());
                pstmt.execute();
                res = pstmt.getUpdateCount() != 0;
                pstmt.close();

            }
        } catch (DatabaseConnectionDAOException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw new StatementExecuteDAOException("Não consegui adicionar personagem do actor", ex);
        }
        return res;
    }

    public boolean adicionaGeneroFilme(Filme f, Genero g) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        boolean res = false;
        PreparedStatement pstmt = null;
        try {

            Connection conn = cb.getConnection();
            if (g.getId() > 0) {
                pstmt = conn.prepareStatement(ADD_RELATED_GENERO);
                pstmt.setInt(1, f.getId());
                pstmt.setInt(2, g.getId());
                System.out.println("Query: " + pstmt.toString());
                pstmt.execute();
                res = pstmt.getUpdateCount() != 0;
                pstmt.close();

            }
        } catch (DatabaseConnectionDAOException ex) {
            throw ex;
        } catch (SQLException ex) {
            throw new StatementExecuteDAOException("Não consegui adicionar personagem do actor", ex);
        }
        return res;
    }
}
