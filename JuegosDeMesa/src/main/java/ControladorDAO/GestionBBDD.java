/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorDAO;

import Excepciones.ExcepcionMia;
import Modelo.Juego;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.print.attribute.standard.NumberOfInterveningJobs;

public class GestionBBDD implements InterfazDAO {

    //Variables que almacenan en forma de string los datos para realizar la conexión con el servidor 
    //------ ATENCIÓN YO HE TRABAJADO CON EL PUERTO 3308, CAMBIAR SI UTILIZAS EL POR DEFECTO MYSQL (3306) --------
//    private final String JDBC_URL = "jdbc:mysql://localhost:3306";
    private final String JDBC_URL = "jdbc:mysql://localhost:3308";
    private final String JDBC_COMMU_OPT = "?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "";

    //NOMBRE DE la base de Datos y la tabla
    private final String JDBC_DDBB = "JuegosDeMesaBBDD";
    private final String JDBC_TABLE = "Juegos";
    private final String JDBC_DDBB_TABLE = JDBC_DDBB + "." + JDBC_TABLE;

    //Variables ---> consultas SQL
    private final String SQL_SELECT_ALL = "SELECT * FROM " + JDBC_DDBB_TABLE + " ORDER BY nombre;";
    private final String SQL_SELECT = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE (nombre = ";
    private final String SQL_SELECT_LISTAEX = "SELECT listaExpansiones FROM " + JDBC_DDBB_TABLE + " WHERE (nombre = ";
    private final String SQL_SELECT_LISTTEMATICA = "SELECT tematica FROM " + JDBC_DDBB_TABLE + " ORDER BY tematica;";
//    private final String SQL_SELECT_PORTEMATICA = "SELECT nombre FROM " + JDBC_DDBB_TABLE + " WHERE (tematica =";
//    private final String SQL_SELECT_GENERAL = "SELECT nombre FROM " + JDBC_DDBB_TABLE + " WHERE (";
    private final String SQL_SELECT_NOMBRE = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE nombre = ? ORDER BY nombre";
    private final String SQL_SELECT_DIFICULTAD = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE dificultad = ? ORDER BY nombre";
    private final String SQL_SELECT_NUMJUG = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE numJugMax >= ? AND numJugMin <= ? ORDER BY nombre";
    private final String SQL_SELECT_TEMATICAYDificultad = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND dificultad = ? ORDER BY nombre";
    private final String SQL_SELECT_TEMATICAYNumJug = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND numJugMax >= ? AND numJugMin <= ? ORDER BY nombre";
    private final String SQL_SELECT_NUmJugYDificultad = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE numJugMax >= ? AND numJugMin <= ? AND dificultad = ? ORDER BY nombre";
    private final String SQL_SELECT_NUmJugyTEMATICAYDificultad = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND numJugMax >= ? AND numJugMin <= ? AND dificultad = ? ORDER BY nombre";
    private final String SQL_SELECT_TEMATICAYDuracion = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND duracion BETWEEN ? AND ? ORDER BY nombre"; //ORDER BY nombre";
    private final String SQL_SELECT_duracion = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE duracion >= ? AND duracion <= ? ORDER BY nombre";
    private final String SQL_SELECT_TEMATICAYDuracionydificultad = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND duracion BETWEEN ? AND ? AND dificultad = ? ORDER BY nombre"; //ORDER BY nombre";
    private final String SQL_SELECT_TEMATICAYDuracionyNumJug = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND duracion BETWEEN ? AND ? AND numJugMax >= ? AND numJugMin <= ? ORDER BY nombre"; //ORDER BY nombre";
    private final String SQL_SELECT_los4 = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ? AND duracion BETWEEN ? AND ? AND numJugMax >= ? AND numJugMin <= ? AND dificultad = ? ORDER BY nombre"; //ORDER BY nombre";
    private final String SQL_SELECT_duracionyDificultad = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE duracion >= ? AND duracion <= ? AND dificultad = ? ORDER BY nombre";
    private final String SQL_SELECT_duracionyNumJug = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE duracion >= ? AND duracion <= ? AND numJugMax >= ? AND numJugMin <= ? ORDER BY nombre";
    private final String SQL_SELECT_duracionyNumJugydif = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE duracion >= ? AND duracion <= ? AND numJugMax >= ? AND numJugMin <= ? AND dificultad = ? ORDER BY nombre";

    private final String SQL_SELECT_TEMATICA2 = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";
    private final String SQL_SELECT_NOMBRE_LETRA = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE nombre LIKE ?";

    private final String SQL_SELECT_Total = "SELECT count(*) as total FROM " + JDBC_DDBB_TABLE;
    private final String SQL_SELECT_TotalDueno = "SELECT count(*) as total FROM " + JDBC_DDBB_TABLE + " WHERE dueno = ";
    private final String SQL_SELECT_JuegosporDueno = "SELECT nombre FROM " + JDBC_DDBB_TABLE + " WHERE dueno = ?";

// numJugMin, numJugMax, duracion, tematica, dificultad, estrategia, suerte, interaccion
//    private final String SQL_INSERT = "INSERT INTO " + JDBC_DDBB_TABLE + " (nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String SQL_INSERT_2 = "INSERT INTO " + JDBC_DDBB_TABLE + " (nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final String SQL_UPDATE_EXPANSIONES = "UPDATE " + JDBC_DDBB_TABLE + " SET listaExpansiones = ?  WHERE (nombre = ?);";

    private final String SQL_UPDATE = "UPDATE " + JDBC_DDBB_TABLE + " SET nombre = ?, disenador = ?, año = ?, numJugMax = ?, numJugMin = ?, duracion = ?, tematica = ?, dificultad = ?, estrategia = ?, suerte = ?, interaccion = ?, dueno = ?, expansion = ?, expansionDe = ?, descripcion = ? WHERE (id = ?);";
//    private final String SQL_UPDATE_NOMBRE = "UPDATE " + JDBC_DDBB_TABLE + " SET nombre = ?  WHERE (id = ?);";
//    private final String SQL_UPDATE_CURSO = "UPDATE " + JDBC_DDBB_TABLE + " SET curso = ?  WHERE (id = ?);";
//    private final String SQL_UPDATE_MEDIA = "UPDATE " + JDBC_DDBB_TABLE + " SET media = ?  WHERE (id = ?);";
    private final String SQL_DELETE = "DELETE FROM " + JDBC_DDBB_TABLE + " WHERE (nombre = ";

    @Override
    public ArrayList<String> nombresDeJuegosporDueño(String nombre) throws ExcepcionMia {

        ArrayList<String> lista = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
            ps = conexion.prepareStatement(SQL_SELECT_JuegosporDueno);
            ps.setString(1, nombre);
            rs = ps.executeQuery();

            while (rs.next()) {
                String nomb = rs.getString("nombre");
                lista.add(nomb);
            }
        } catch (SQLException ex) {
            System.out.println("No se puede leer de la base de datos: " + ex.getMessage());
            throw new ExcepcionMia("No se puede leer de la base de datos: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura: " + ex.getMessage());
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura: " + ex.getMessage());
            }
        }
        return lista;
    }

    @Override
    public int cantidadporDueño(String nombre) throws ExcepcionMia {
        int total = 0;
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        String query = SQL_SELECT_TotalDueno + "'" + nombre + "';";
        try {
            conexion = conectarse();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {

//                String nombre = rs.getString("dueno");
                total = rs.getInt("total");
//                boolean expansion = rs.getBoolean("expansion");
//                int edad = rs.getInt("edad");
//                String curso = rs.getString("curso");
//                double media = rs.getDouble("media");

            }
        } catch (SQLException ex) {
            System.out.println("No se puede leer de la base de datos ");
            throw new ExcepcionMia("No se puede leer de la base de datos ");
        } finally {
            try {
                rs.close();
                st.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura");
            }
        }
        return total;

    }

    @Override
    public int cantidadTotal() throws ExcepcionMia {

        int total = 0;
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            st = conexion.createStatement();
            rs = st.executeQuery(SQL_SELECT_Total);
            while (rs.next()) {

//                String nombre = rs.getString("dueno");
                total = rs.getInt("total");
//                boolean expansion = rs.getBoolean("expansion");
//                int edad = rs.getInt("edad");
//                String curso = rs.getString("curso");
//                double media = rs.getDouble("media");

            }
        } catch (SQLException ex) {
            System.out.println("No se puede leer de la base de datos ");
            throw new ExcepcionMia("No se puede leer de la base de datos ");
        } finally {
            try {
                rs.close();
                st.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura");
            }
        }
        return total;

    }

    @Override
    public ArrayList<Juego> leerTodos() throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            st = conexion.createStatement();
            rs = st.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) {

                String nombre = rs.getString("nombre");
                boolean expansion = rs.getBoolean("expansion");
//                int edad = rs.getInt("edad");
//                String curso = rs.getString("curso");
//                double media = rs.getDouble("media");

                juegos.add(new Juego(nombre, expansion));
            }
        } catch (SQLException ex) {
            System.out.println("No se puede leer de la base de datos ");
            throw new ExcepcionMia("No se puede leer de la base de datos ");
        } finally {
            try {
                rs.close();
                st.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura");
            }
        }
        return juegos;
    }

    @Override
    public ArrayList<Juego> buscarporNombreConLetras(String n) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
            String query = SQL_SELECT_NOMBRE_LETRA;
            ps = conexion.prepareStatement(query);
            //ps.setString(1, n + "%");
            ps.setString(1, "%"+n + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: " + n);
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

//SQL_SELECT_NOMBRE_LETRA 'a%';
    }

    @Override
    public int actualizarTodo(Juego a) throws ExcepcionMia {

        Connection conexion = null;
        PreparedStatement instruccion = null;
        int registrados = 0;
        try {
            conexion = conectarse();
            instruccion = conexion.prepareStatement(SQL_UPDATE);

            instruccion.setString(1, a.getNombre());
            instruccion.setString(2, a.getDisenador());
            instruccion.setInt(3, a.getAño());
            instruccion.setInt(4, a.getNumJugMax());
            instruccion.setInt(5, a.getNumJugMin());
            instruccion.setInt(6, a.getDuracion());
            instruccion.setString(7, a.getTematica());
            instruccion.setInt(8, a.getDificultad());
            instruccion.setInt(9, a.getEstrategia());
            instruccion.setInt(10, a.getSuerte());
            instruccion.setInt(11, a.getInteraccion());
            instruccion.setString(12, a.getDueño());
            instruccion.setBoolean(13, a.isExpansion());
            instruccion.setString(14, a.getExpansionDe());
            instruccion.setString(15, a.getDescripcion());
            instruccion.setInt(16, a.getId());

            registrados = instruccion.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("No se ha podido ejecutar la orden SQL_UPDATE. ");
            throw new ExcepcionMia("No se ha podido ejecutar la orden SQL_UPDATE. ");
        } finally {
            try {
                instruccion.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura ");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura ");
            }
        }
        return registrados;

    }

    @Override
    public Juego leerExpansiones(Juego j) throws ExcepcionMia {
        Juego juego = null;
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_LISTAEX + "'" + j.getNombre() + "');";
            ps = conexion.prepareStatement(query);

            rs = ps.executeQuery();

            if (rs.next()) {
                String listaExpansiones = rs.getString("listaExpansiones");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                juego = new Juego(j.getNombre(), lista);

            } else {
                System.out.println("No se encontró ningún juego con el ID proporcionado: " + j.getNombre());
            }
        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juego;
    }

    @Override
    public Juego leerJuegosPorNombre(String n) throws ExcepcionMia {

        Juego juego = null;
//        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_NOMBRE;

            ps = conexion.prepareStatement(query);
            ps.setString(1, n);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                if (expansion == true) {
                    juego = new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion);
                } else if (expansion != true && !lista.isEmpty()) {
                    juego = new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion);
                } else if (expansion != true && lista.isEmpty()) {
                    juego = new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion);

                }

//                juego = new Juego(nombre, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, expansion);
            }
            if (juego == null) {
                System.out.println("No se encontró ningún juego con esta temática: " + n);
            }

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juego;

    }

    @Override
    public ArrayList<Juego> buscarporDuracionyNumJug(int min, int max, int numJug) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_duracionyNumJug);

            ps.setInt(1, min);
            ps.setInt(2, max);
            ps.setInt(3, numJug);
            ps.setInt(4, numJug);
            System.out.println("Ejecutando consulta: " + SQL_SELECT_TEMATICAYDuracion);
            System.out.println("Parámetros:  duracionMin=" + min + ", duracionMax=" + max + ", NUM JUGADORES" + numJug);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta DURACION: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;
    }

    @Override
    public ArrayList<Juego> buscarporDuracionyDificultad(int min, int max, int dif) throws ExcepcionMia {
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_duracionyDificultad);

            ps.setInt(1, min);
            ps.setInt(2, max);
            ps.setInt(3, dif);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta DURACION: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporDuracion(int min, int max) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_duracion);

            ps.setInt(1, min);
            ps.setInt(2, max);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta DURACION: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporlas4(String n, int min, int max, int numJug, int dif) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_los4);
            ps.setString(1, n);
            ps.setInt(2, min);
            ps.setInt(3, max);
            ps.setInt(4, numJug);
            ps.setInt(5, numJug);
            ps.setInt(6, dif);

            System.out.println("Ejecutando consulta: " + SQL_SELECT_TEMATICAYDuracion);
            System.out.println("Parámetros: tematica=" + n + ", duracionMin=" + min + ", duracionMax=" + max + ", NUM JUGADORES" + numJug + " , dificultad " + dif);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTematicayDuracionyNumJug(String n, int min, int max, int numJug) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_TEMATICAYDuracionyNumJug);
            ps.setString(1, n);
            ps.setInt(2, min);
            ps.setInt(3, max);
            ps.setInt(4, numJug);
            ps.setInt(5, numJug);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTematicayDuracionyDificultad(String n, int min, int max, int dif) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_TEMATICAYDuracionydificultad);
            ps.setString(1, n);
            ps.setInt(2, min);
            ps.setInt(3, max);
            ps.setInt(4, dif);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporDuracionyNumJugyDificultad(int min, int max, int numJug, int dif) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_duracionyNumJugydif);
            ps.setInt(1, min);
            ps.setInt(2, max);
            ps.setInt(3, numJug);
            ps.setInt(4, numJug);
            ps.setInt(5, dif);

//             System.out.println("Ejecutando consulta: " + SQL_SELECT_TEMATICAYDuracion);
//        System.out.println("Parámetros: tematica=" + n + ", duracionMin=" + min + ", duracionMax=" + max);
//
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTematicayDuracion(String n, int min, int max) throws ExcepcionMia {
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_TEMATICAYDuracion);
            ps.setString(1, n);
            ps.setInt(2, min);
            ps.setInt(3, max);

            System.out.println("Ejecutando consulta: " + SQL_SELECT_TEMATICAYDuracion);
            System.out.println("Parámetros: tematica=" + n + ", duracionMin=" + min + ", duracionMax=" + max);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTematica(String n) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_TEMATICA2);
            ps.setString(1, n);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
                
                
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: " + n);
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTematicaYNumJug(String t, int numjug) throws ExcepcionMia {

        Juego juego = null;
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_TEMATICAYNumJug;

            ps = conexion.prepareStatement(query);
            ps.setString(1, t);
            ps.setInt(2, numjug);
            ps.setInt(3, numjug);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }
            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática y dificultad");
            }
            juegos.toString();

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juegos;
    }

    @Override
    public ArrayList<Juego> buscarporNumJugYDificultad(int numjug, int dific) throws ExcepcionMia {

        Juego juego = null;
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_NUmJugYDificultad;

            ps = conexion.prepareStatement(query);
            ps.setInt(1, numjug);
            ps.setInt(2, numjug);
            ps.setInt(3, dific);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }
            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática y dificultad");
            }
            juegos.toString();

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTemáticaYDificultadyNumJu(String p, int numjug, int dific) throws ExcepcionMia {

        Juego juego = null;
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_NUmJugyTEMATICAYDificultad;

            ps = conexion.prepareStatement(query);
            ps.setString(1, p);
            ps.setInt(2, numjug);
            ps.setInt(3, numjug);
            ps.setInt(4, dific);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }
            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática y dificultad");
            }
            juegos.toString();

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juegos;
    }

    @Override
    public ArrayList<Juego> buscarporNumJugadores(int n) throws ExcepcionMia {
        Juego juego = null;
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_NUMJUG;

            ps = conexion.prepareStatement(query);
            ps.setInt(1, n);
            ps.setInt(2, n);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }
            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática y dificultad");
            }
            juegos.toString();

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporDificultad(int n) throws ExcepcionMia {

//        Juego juego = null;
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_DIFICULTAD;

            ps = conexion.prepareStatement(query);
            ps.setInt(1, n);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }
            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática y dificultad");
            }
            juegos.toString();

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juegos;

    }

    @Override
    public ArrayList<Juego> buscarporTematicaYDificultad(String t, int n) throws ExcepcionMia {

//        Juego juego = null;
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            String query = SQL_SELECT_TEMATICAYDificultad;

            ps = conexion.prepareStatement(query);
            ps.setString(1, t);
            ps.setInt(2, n);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }
            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática y dificultad");
            }
            juegos.toString();

        } catch (SQLException ex) {

            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }
        return juegos;
    }

    /**
     * Actualiza solamente el nombre del alumno
     *
     * @param a
     * @return --> integer
     * @throws ExcepcionMia
     */
    @Override
    public int actualizarExpansiones(Juego j) throws ExcepcionMia {
        Connection conexion = null;
        PreparedStatement instruccion = null;
        int registrados = 0;
        try {
            conexion = conectarse();
            instruccion = conexion.prepareStatement(SQL_UPDATE_EXPANSIONES);
            instruccion.setString(1, j.getListaExpansiones().toString());
            instruccion.setString(2, j.getNombre());
            registrados = instruccion.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("No se ha podido ejecutar la orden SQL_UPDATE. ");
            throw new ExcepcionMia("No se ha podido ejecutar la orden SQL_UPDATE. ");

        } finally {
            try {
                instruccion.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura ");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura ");
            }
        }
        return registrados;

    }

    @Override
    public ArrayList<Juego> leerTodos2() throws ExcepcionMia {
        ArrayList<Juego> juegos = new ArrayList<>();
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = conectarse();
//            String query = "SELECT * FROM " + JDBC_DDBB_TABLE + " WHERE tematica = ?";

            ps = conexion.prepareStatement(SQL_SELECT_ALL);

            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                String dueno = rs.getString("dueno");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");

                ArrayList<String> lista = new ArrayList<>();
                if (listaExpansiones != null) {
                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));
                }

                 if (expansion == true) {
//                    juegos.add(new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, descripcion));
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion));
                } else if (expansion != true && !lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion));
                } else if (expansion != true && lista.isEmpty()) {
                    juegos.add(new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion));

                }
            }

            if (juegos.isEmpty()) {
                System.out.println("No se encontró ningún juego con esta temática: ");
            }

        } catch (SQLException ex) {
            System.out.println("Error al ejecutar la consulta SQL: " + ex.getMessage());
            throw new ExcepcionMia("Error al ejecutar la consulta SQL: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("Error al cerrar los recursos: " + ex.getMessage());
                throw new ExcepcionMia("Error al cerrar los recursos: " + ex.getMessage());
            }
        }

        return juegos;

    }

    @Override
    public ArrayList<String> leerTematica() throws ExcepcionMia {
        ArrayList<String> tematicas = new ArrayList<>();
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            st = conexion.createStatement();
            rs = st.executeQuery(SQL_SELECT_LISTTEMATICA);
            while (rs.next()) {
                String tematica = rs.getString("tematica");
//                boolean expansion = rs.getBoolean("expansion");
//                int edad = rs.getInt("edad");
//                String curso = rs.getString("curso");
//                double media = rs.getDouble("media");
                if (!tematicas.contains(tematica)) {
                    tematicas.add(tematica);
                }

            }
        } catch (SQLException ex) {
            System.out.println("No se puede leer de la base de datos ");
            throw new ExcepcionMia("No se puede leer de la base de datos ");
        } finally {
            try {
                rs.close();
                st.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura");
            }
        }
        return tematicas;
    }

    @Override
    public Juego leerJuego(Juego j) throws ExcepcionMia {

        Juego juego = null;
        Connection conexion = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conexion = conectarse();
            st = conexion.createStatement();
            rs = st.executeQuery(SQL_SELECT + "'" + j.getNombre() + "');");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String disenador = rs.getString("disenador");
                int año = rs.getInt("año");
                int numJugMax = rs.getInt("numJugMax");
                int numJugMin = rs.getInt("numJugMin");
                int duracion = rs.getInt("duracion");
                String tematica = rs.getString("tematica");
                int dificultad = rs.getInt("dificultad");
                int estrategia = rs.getInt("estrategia");
                int suerte = rs.getInt("suerte");
                int interaccion = rs.getInt("interaccion");
                boolean expansion = rs.getBoolean("expansion");
                String expansionDe = rs.getString("expansionDe");
                String listaExpansiones = rs.getString("listaExpansiones");
                String descripcion = rs.getString("descripcion");
                String dueno = rs.getString("dueno");

                ArrayList<String> lista = new ArrayList<>();
                if (expansion == false && listaExpansiones != null) {

                    String listaStringLimpia = listaExpansiones.substring(1, listaExpansiones.length() - 1);
                    String[] items = listaStringLimpia.split(", ");
                    lista = new ArrayList<>(Arrays.asList(items));

//                    juego = new Juego(nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, disenador, expansion, expansionDe, lista);
                }

                if (expansion == true) {
                    juego = new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, expansionDe, new ArrayList<>(), descripcion);
                } else if (expansion != true && !lista.isEmpty()) {
                    juego = new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, lista, descripcion);
                } else if (expansion != true && lista.isEmpty()) {
                    juego = new Juego(id, nombre, disenador, año, numJugMax, numJugMin, duracion, tematica, dificultad, estrategia, suerte, interaccion, dueno, expansion, null, new ArrayList<>(), descripcion);

                }

            }
        } catch (SQLException ex) {
            System.out.println("No se puede leer de la base de datos ");
            throw new ExcepcionMia("No se puede leer de la base de datos ");
        } finally {
            try {
                rs.close();
                st.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                System.out.println("No se ha podido cerrar el proceso de escritura");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura");
            }
        }
        return juego;
    }

    @Override
    public ArrayList<Juego> buscarTotal(int numJug, int duracionmin, int duracionmax, int dif, int estra, int suerte, int interac, String tema) throws ExcepcionMia {

        ArrayList<Juego> juegos = new ArrayList<>();
        try (Connection connection = conectarse(); PreparedStatement statement = connection.prepareStatement("SELECT nombre FROM juegos WHERE numJugMin <= ? AND numJugMax >= ? AND duracion BETWEEN ? AND ? AND dificultad = ? AND estrategia = ? AND suerte = ? AND interaccion = ? AND tematica = ?");) {
            statement.setInt(1, numJug);
            statement.setInt(2, numJug);
            statement.setInt(3, duracionmin);
            statement.setInt(4, duracionmax);
            statement.setInt(5, dif);
            statement.setInt(6, estra);
            statement.setInt(7, suerte);
            statement.setInt(8, interac);
            statement.setString(9, tema);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Juego j = new Juego(resultSet);
                    juegos.add(j);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error en la consola
            throw new ExcepcionMia("No se puede leer de la base de datos");
        }
        return juegos;

    }

    /**
     * Inserta en la base de datos el objeto alumno introducido como parámetro
     *
     * @param a
     * @return ---> Entero
     * @throws ExcepcionMia
     */
    @Override
    public int insertar(Juego j) throws ExcepcionMia {

        Connection conexion = null;
        PreparedStatement instruccion = null;
        int registrados = 0;
        try {
            //trata de ejecutar la query sql obteniendo los valores de los atributos del objeto alumno introducido
            conexion = conectarse();
            instruccion = conexion.prepareStatement(SQL_INSERT_2);

            instruccion.setString(1, j.getNombre());
            instruccion.setString(2, j.getDisenador());
            instruccion.setInt(3, j.getAño());
            instruccion.setInt(4, j.getNumJugMax());
            instruccion.setInt(5, j.getNumJugMin());
            instruccion.setInt(6, j.getDuracion());
            instruccion.setString(7, j.getTematica());
            instruccion.setInt(8, j.getDificultad());
            instruccion.setInt(9, j.getEstrategia());
            instruccion.setInt(10, j.getSuerte());
            instruccion.setInt(11, j.getInteraccion());
            instruccion.setString(12, j.getDueño());
            instruccion.setBoolean(13, j.isExpansion());
            instruccion.setString(14, j.getExpansionDe());
//            instruccion.setString(14, j.getListaExpansiones().toString());
            instruccion.setString(15, j.getDescripcion());

            registrados = instruccion.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("No se ha podido ejecutar la orden SQL-INSERT. ");
            throw new ExcepcionMia("No se ha podido ejecutar la orden SQL-INSERT. ");
        } finally {
            try {
                instruccion.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                //ex.printStackTrace(System.out);
                System.out.println("No se ha podido cerrar el proceso de escritura ");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura ");
            }
        }
        return registrados;

    }

    /**
     * Se conecta con mysql a traves del puerto, url, usuario y pasword
     * indicados.
     *
     * @return ---> Connetion
     * @throws ExcepcionMia
     */
    public Connection conectarse() throws ExcepcionMia {

        /*
        creo una conexión con las variables correspondientes y trato de crear la base de datos y la tabla si no existe.
        En caso contrario imprimpo pr pantalla error y lanzo excepcion mía
         */
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(JDBC_URL + JDBC_COMMU_OPT, JDBC_USER, JDBC_PASSWORD);
            crearBBDD(conexion);
            crearTablaAlumnos(conexion);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("No se puede conectar o crear las base de datos con tablas: " + JDBC_DDBB);
            throw new ExcepcionMia("No se puede conectar o crear las base de datos con tablas: " + JDBC_DDBB);

        }
        return conexion;
    }

    /**
     * Ejecuta la instruccion para crear una base de datos con el nombre
     * seleccionado como variable JDBC.
     *
     * @param conexion
     * @throws SQLException
     * @throws ExcepcionMia
     */
    public void crearBBDD(Connection conexion) throws SQLException {

        String instruction = "create database if not exists " + JDBC_DDBB + ";";
        Statement stmt = null;
        stmt = conexion.createStatement();  //creo un objeto a partir de la conexion introducida como parámetro
        stmt.executeUpdate(instruction);    //Ejecuto la query en la base de  datos

        stmt.close(); // libero recursos
    }

    /**
     * Ejecuta la query de mysql para crear la tabla juegos a través de xampp
     * mediante la conexión introducida como parámetro
     *
     * @param conexion
     * @throws SQLException
     */
    public void crearTablaAlumnos(Connection conexion) throws SQLException {
        String instruccion = "create table if not exists " + JDBC_DDBB_TABLE + "("
                + "id BIGINT primary key auto_increment, " //el id se incrementará cada vez que se agregue un alumno obligatoriamente
                + "nombre VARCHAR(50), "
                + "disenador VARCHAR (50),"
                + "año int, "
                + "numJugMax int, "
                + "numJugMin int, "
                + "duracion int, "
                + "tematica VARCHAR (50),"
                + "dificultad int, "
                + "estrategia int, "
                + "suerte int, "
                + "interaccion int, "
                + "dueno VARCHAR (50),"
                + "expansion BOOLEAN,"
                + "expansionDe VARCHAR (50),"
                + "listaExpansiones VARCHAR (1000),"
                + "descripcion TEXT);";

        Statement stmt = null;
        stmt = conexion.createStatement();
        stmt.executeUpdate(instruccion);
        stmt.close();
    }

    /**
     * Cierra la conexión que se le introduzca como parámetro
     *
     * @param conexion
     * @throws ExcepcionMia
     */
    public void desconectarse(Connection conexion) throws ExcepcionMia {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException ex) {
                System.out.println("No se puede desconectar de la base de datos " + JDBC_DDBB);
                throw new ExcepcionMia("No se puede desconectar de la base de datos " + JDBC_DDBB);
            }
        }
    }

    @Override
    public int elimniar(Juego j) throws ExcepcionMia {

        Connection conexion = null;
        PreparedStatement instruccion = null;

        int registrados = 0;
        try {
            conexion = conectarse();
            String query = SQL_DELETE + "'" + j.getNombre() + "');";
            instruccion = conexion.prepareStatement(query);
            registrados = instruccion.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("No se ha podido ejecutar la orden SQL-DELETE. ");
            throw new ExcepcionMia("No se ha podido ejecutar la orden SQL-DELETE. ");
        } finally {
            try {
                instruccion.close();
                desconectarse(conexion);
            } catch (SQLException ex) {
                //ex.printStackTrace(System.out);
                System.out.println("No se ha podido cerrar el proceso de escritura ");
                throw new ExcepcionMia("No se ha podido cerrar el proceso de escritura ");
            }
        }
        return registrados;

    }

}
