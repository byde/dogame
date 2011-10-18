/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author byde
 */
public class Config {
    private static String usuario = "byde";
    private static String pass = "fernando";
    private static String universo = "Fornax";
    private static int no_planetas = 2;
    private static String[] planetas = {"X-I","X-II"};

    /**
     * @return the usuario
     */
    public static String getUsuario() {
        return usuario;
    }

    /**
     * @return the pass
     */
    public static String getPass() {
        return pass;
    }

    /**
     * @return the universo
     */
    public static String getUniverso() {
        return universo;
    }

    /**
     * @return the no_planetas
     */
    public static int getNo_planetas() {
        return no_planetas;
    }
}
