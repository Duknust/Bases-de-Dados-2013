/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.bmdb.dao.db.IConnectionBroker;
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.domain.Filme;
import org.bmdb.dao.exceptions.DatabaseConnectionDAOException;
import org.bmdb.dao.exceptions.StatementExecuteDAOException;

/**
 *
 * @author duarteduarte
 */
public class SystemDAO {

    private IConnectionBroker cb;

    public SystemDAO(IConnectionBroker cb) {
        this.cb = cb;
    }

    public Actor queryActorMaisOscares() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Actor> actorMaisOscares = new ArrayList<>();
        ResultSet actoresRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            actoresRs = pstmt.executeQuery("select nome, oscares from actores where oscares = (SELECT max( a.oscares) as maximo from actores a where a.oscares<100 and sexo='Masculino') and sexo = 'Masculino'");
            System.out.println(actoresRs);
            Actor actor = null;
            while (actoresRs.next()) {
                actor = new Actor();
                actor.setNomeActor(actoresRs.getString("NOME"));
                actor.setOscaresVencidos(actoresRs.getInt("OSCARES"));
                actorMaisOscares.add(actor);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (actoresRs != null) {
                    actoresRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return actorMaisOscares.get(0);

    }

    public Actor queryActrizMaisOscares() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Actor> actorMaisOscares = new ArrayList<>();
        ResultSet actoresRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            actoresRs = pstmt.executeQuery("select nome, oscares from actores where oscares = (SELECT max( a.oscares) as maximo from actores a where a.oscares<100 and sexo='Feminino') and sexo = 'Feminino'");
            Actor actor = null;
            while (actoresRs.next()) {
                actor = new Actor();
                actor.setNomeActor(actoresRs.getString("NOME"));
                actor.setOscaresVencidos(actoresRs.getInt("OSCARES"));
                actorMaisOscares.add(actor);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (actoresRs != null) {
                    actoresRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return actorMaisOscares.get(0);
    }

    public Filme queryFilmeMaisOscares() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Filme> filmeMaisOscares = new ArrayList<>();
        ResultSet filmesRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            filmesRs = pstmt.executeQuery("select titulo, oscares from filmes where oscares = (SELECT max( a.oscares) as maximo from filmes a where a.oscares<100)");
            Filme filme = null;
            while (filmesRs.next()) {
                filme = new Filme();
                filme.setTitulo(filmesRs.getString("TITULO"));
                filme.setOscares(filmesRs.getInt("OSCARES"));
                filmeMaisOscares.add(filme);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (filmesRs != null) {
                    filmesRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return filmeMaisOscares.get(0);
    }

    public Filme queryFilmeMaisGerou() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Filme> filmeMaisGerou = new ArrayList<>();
        ResultSet filmesRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            filmesRs = pstmt.executeQuery("select titulo, gross from filmes where gross = (SELECT max(a.gross) as maximo from filmes a)");
            Filme filme = null;
            while (filmesRs.next()) {
                filme = new Filme();
                filme.setTitulo(filmesRs.getString("TITULO"));
                filme.setGross(filmesRs.getLong("GROSS"));
                filmeMaisGerou.add(filme);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (filmesRs != null) {
                    filmesRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return filmeMaisGerou.get(0);
    }

    public Filme queryFilmeMenosGerou() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Filme> filmeMenosGerou = new ArrayList<>();
        ResultSet filmesRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            filmesRs = pstmt.executeQuery("select titulo, gross from filmes where gross = (SELECT min(a.gross) as maximo from filmes a where a.gross>0)");
            Filme filme = null;
            while (filmesRs.next()) {
                filme = new Filme();
                filme.setTitulo(filmesRs.getString("TITULO"));
                filme.setGross(filmesRs.getInt("GROSS"));
                filmeMenosGerou.add(filme);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (filmesRs != null) {
                    filmesRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return filmeMenosGerou.get(0);
    }

    public Filme queryFilmeMaisCaro() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Filme> filmeMaisCaro = new ArrayList<>();
        ResultSet filmesRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            filmesRs = pstmt.executeQuery("select titulo, budget from filmes where budget = (SELECT max(a.budget) as maximo from filmes a)");
            Filme filme = null;
            while (filmesRs.next()) {
                filme = new Filme();
                filme.setTitulo(filmesRs.getString("TITULO"));
                filme.setBudget(filmesRs.getInt("BUDGET"));
                filmeMaisCaro.add(filme);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (filmesRs != null) {
                    filmesRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return filmeMaisCaro.get(0);
    }

    public List<Actor> queryBlessedWinners() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Actor> blessedWinners = new ArrayList<>();
        ResultSet blessedWinnersRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            blessedWinnersRs = pstmt.executeQuery("select nome, premios_nomeado, premios_vencidos,oscares, imagem from actores where premios_nomeado = (premios_vencidos+oscares) and premios_nomeado > 10 order by premios_nomeado desc");
            Actor actor = null;
            while (blessedWinnersRs.next()) {
                actor = new Actor();
                actor.setNomeActor(blessedWinnersRs.getString("NOME"));
                actor.setPremiosNomeadoActor(blessedWinnersRs.getInt("PREMIOS_NOMEADO"));
                actor.setPremiosVencidosActor(blessedWinnersRs.getInt("PREMIOS_VENCIDOS"));
                actor.setOscaresVencidos(blessedWinnersRs.getInt("OSCARES"));
                actor.setImagem(blessedWinnersRs.getString("IMAGEM"));
                blessedWinners.add(actor);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (blessedWinnersRs != null) {
                    blessedWinnersRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return blessedWinners;
    }

    public List<Actor> queryBadLuckLosers() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Actor> badLuckLosers = new ArrayList<>();
        ResultSet badLuckRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            badLuckRs = pstmt.executeQuery("select nome, premios_nomeado, premios_vencidos, imagem from actores where premios_nomeado >(2*(premios_vencidos+oscares)) and premios_nomeado > 0 order by premios_nomeado desc");
            Actor actor = null;
            while (badLuckRs.next()) {
                actor = new Actor();
                actor.setNomeActor(badLuckRs.getString("NOME"));
                actor.setPremiosNomeadoActor(badLuckRs.getInt("PREMIOS_NOMEADO"));
                actor.setPremiosVencidosActor(badLuckRs.getInt("PREMIOS_VENCIDOS"));
                actor.setImagem(badLuckRs.getString("IMAGEM"));
                badLuckLosers.add(actor);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (badLuckRs != null) {
                    badLuckRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return badLuckLosers;
    }

    public String bestOfRealizador(int idRealizador) throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        String bestOfRealizador = null;
        ResultSet top = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            top = pstmt.executeQuery("select fil.* from filmes fil where fil.gross = (select max(filmes.gross) from filmes where id_realizador=" + idRealizador + ") and fil.id_realizador =" + idRealizador);
            while (top.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append(top.getString("Titulo"));
                sb.append(" - ");
                sb.append(top.getInt("Budget"));
                bestOfRealizador = sb.toString();
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (top != null) {
                    top.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return bestOfRealizador;
    }

    public List<Actor> queryHallofFame() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Actor> hallOfFame = new ArrayList<>();
        ResultSet hallOfFameRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            hallOfFameRs = pstmt.executeQuery("select nome, premios_nomeado, premios_vencidos,oscares, imagem from actores where premios_nomeado<2*(premios_vencidos+oscares) and premios_nomeado > 50 order by premios_vencidos desc;");
            Actor actor = null;
            while (hallOfFameRs.next()) {
                actor = new Actor();
                actor.setNomeActor(hallOfFameRs.getString("NOME"));
                actor.setPremiosNomeadoActor(hallOfFameRs.getInt("PREMIOS_NOMEADO"));
                actor.setPremiosVencidosActor(hallOfFameRs.getInt("PREMIOS_VENCIDOS"));
                actor.setImagem(hallOfFameRs.getString("IMAGEM"));
                hallOfFame.add(actor);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (hallOfFameRs != null) {
                    hallOfFameRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return hallOfFame;
    }

    public List<Actor> queryTheOtherWorld() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;
        List<Actor> hallOfFame = new ArrayList<>();
        ResultSet hallOfFameRs = null;

        Connection conn = cb.getConnection();

        try {
            System.out.println("isClosed = " + conn.isClosed());
            pstmt = conn.createStatement();
            hallOfFameRs = pstmt.executeQuery("select nome, premios_vencidos,imagem from actores where id_actor in (select id_actor from actores_filme where id_filme in (select id_filme from filmes where rating = 21) order by id_actor desc) order by premios_vencidos desc;");
            Actor actor = null;
            while (hallOfFameRs.next()) {
                actor = new Actor();
                actor.setNomeActor(hallOfFameRs.getString("NOME"));
                actor.setPremiosVencidosActor(hallOfFameRs.getInt("PREMIOS_VENCIDOS"));
                actor.setImagem(hallOfFameRs.getString("IMAGEM"));
                hallOfFame.add(actor);
            }
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (hallOfFameRs != null) {
                    hallOfFameRs.close();
                }
            } catch (SQLException sqle) {
            }
        }

        return hallOfFame;
    }

    public void desactivaUsers() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;

        Connection conn = cb.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute("call DESACTUSERS()");
            System.out.println("isClosed = " + conn.isClosed());
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException sqle) {
            }
        }
    }

    public void activaUsers() throws DatabaseConnectionDAOException, StatementExecuteDAOException {
        Statement pstmt = null;

        Connection conn = cb.getConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute("call ACTUSERS()");
            System.out.println("isClosed = " + conn.isClosed());
        } catch (SQLException sqle) {
            throw new StatementExecuteDAOException("Nao consegui executar query", sqle);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException sqle) {
            }
        }
    }

    public boolean resetDB(Connection conn) {
        return this.cb.destroy() && this.cb.init();
    }
}
