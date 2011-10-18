/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
/**
 *
 * @author byde
 */
public class OgameBot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        WebDriver driver = new FirefoxDriver();
        driver.get("http://ogame.com.es");
        Bot b = new Bot(driver);
        b.login();
        b.irAPlaneta(4);
        b.irAMenu("Galaxia");
        b.espiarPlanetas();
        int galaxia = b.getCurrentGalaxia();
        for(int i =0;i<14;i++)
        {
            galaxia = b.siguienteGalaxia(galaxia);
            b.espiarPlanetas();
        }
        b.irAMenu("Galaxia");
        galaxia = b.getCurrentGalaxia();
        for(int i =0;i<14;i++)
        {
            galaxia = b.anteriorGalaxia(galaxia);
            b.espiarPlanetas();
        }
        b.irAMenu("Recursos");
        b.abrirBuzon();
        b.leerReportesEspionaje();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(OgameBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        b.irAMenu("Flota");
        int naves = 220;
        try {
            naves = b.atacar(naves);
        } catch (InterruptedException ex) {
            Logger.getLogger(OgameBot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
