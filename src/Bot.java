/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Espionaje;
import com.opera.core.systems.interaction.Mouse;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author byde
 */
public class Bot {

    private WebDriver driver;
    private int planeta = 0;

    Bot(WebDriver d) {
        driver = d;
    }

    public void consola(String m) {
        System.out.println(m);
    }

    public void login() {
        //loginBtn
        WebElement btn_entrar = driver.findElement(By.id("loginBtn"));
        btn_entrar.click();
        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.findElement(By.id("loginSubmit")).isDisplayed();
                }
            });
        } catch (Exception e) {
            consola("Error: Login: " + e.getMessage());
        }

        WebElement txt_user = driver.findElement(By.id("usernameLogin"));
        WebElement txt_pass = driver.findElement(By.id("passwordLogin"));
        WebElement btn_subm = driver.findElement(By.id("loginSubmit"));
        //WebElement opt_univ = new Select(driver.findElement(By.id("serverLogin")));
        Select opt_univ = new Select(driver.findElement(By.id("serverLogin")));

        // Enter something to search for passwordLogin
        //inputUsername.sendKeys("ulises");
        txt_user.sendKeys(Model.Config.getUsuario());
        txt_pass.sendKeys(Model.Config.getPass());
        opt_univ.selectByValue("uni106.ogame.com.es");

        // Now submit the form. WebDriver will Botfind the form for us from the element
        btn_subm.submit();

        // Check the title of the page
        //System.out.println("Page title is: " + driver.getTitle());

        // Google's search is rendered dynamically with JavaScript.
        // Wait for the page to load, timeout after 10 seconds
        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.getTitle().startsWith("Fornax");
                }
            });
            driver.switchTo().defaultContent();
            consola("Log: Login: Ingreso correctamente");
        } catch (Exception e) {
            consola("Error: Login: " + e.getMessage());
        }
    }

    public void irAPlaneta(int idplaneta) {
        List<WebElement> list_planetas = driver.findElements(By.className("planetlink"));
        WebElement btn_planeta = list_planetas.get(idplaneta);
        btn_planeta.click();
        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.getTitle().startsWith("Fornax");
                }
            });
            driver.switchTo().defaultContent();
            planeta = idplaneta;
            consola("Log: Planeta: Cambio de planeta exitoso");
        } catch (Exception e) {
            consola("Error: Planeta: Error en cambio de planeta - " + e.getMessage());
        }
    }

    public void irAMenu(String menu) {
        WebElement btn_menu = driver.findElement(By.linkText(menu));
        btn_menu.click();
        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.getTitle().startsWith("Fornax");
                }
            });
            driver.switchTo().defaultContent();
            consola("Log: Menu: Carga de menu a " + menu);
        } catch (Exception e) {
            consola("Error: Menu: Error en carga de menu " + menu + " - " + e.getMessage());
        }
    }

    public void espiarPlanetas() {
        //galaxytable

        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.findElement(By.id("galaxytable")).isEnabled();
                }
            });
            consola("Log: Galaxia: Carga galaxia");
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            consola("Error: Galaxia: Error en carga de galaxia - " + e.getMessage());
        }
        WebElement tab_gal = driver.findElement(By.id("galaxytable"));
        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.findElement(By.className("row")).isEnabled();
                }
            });
        } catch (Exception e) {
            consola("Error: Galaxia: Error en carga de galaxia en row - " + e.getMessage());
        }
        List<WebElement> rows = tab_gal.findElements(By.className("row"));
        WebElement target = null;
        List<WebElement> targets;

        for (WebElement row : rows) {
            target = row.findElement(By.className("playername"));
            consola("Checando: " + row.findElement(By.className("position")).getText() + " estado: " + row.findElement(By.className("playername")).getAttribute("class"));
            if (target.getAttribute("class").endsWith("playername inactive") || target.getAttribute("class").endsWith("playername longinactive")) {
                //row.findElement(By.className("tipsGalaxy")).click();
                row.findElement(By.className("tipsGalaxy")).click();
                
                        try {
                            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                                public Boolean apply(WebDriver d) {
                                    return d.findElement(By.className("ListLinks")).isEnabled();
                                }
                            });
                        } catch (Exception e) {
                            consola("Error: Galaxia: Error en carga de galaxia en row - " + e.getMessage());
                        }
                
                targets = row.findElements(By.className("ListLinks"));
                consola((targets.get(0)).findElement(By.tagName("a")).getAttribute("onclick"));
                //(targets.get(0)).findElement(By.tagName("a")).click();
                ((JavascriptExecutor) driver).executeScript((targets.get(0)).findElement(By.tagName("a")).getAttribute("onclick"));
                //Mouse.moveOn();
                //targets.get(0).click();
                try {
                    (new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {

                        public Boolean apply(WebDriver d) {
                            return d.findElement(By.className("success")).isEnabled();
                        }
                    });
                    consola("Log: Galaxia: Empionaje realizado");
                    ((JavascriptExecutor) driver).executeScript("$(\"#" + driver.findElement(By.className("success")).getAttribute("id") + "\").remove();");
                } catch (Exception e) {
                    consola("Error: Galaxia: Error al espiar - " + e.getMessage());
                }
            }

        }
    }

    public int siguienteGalaxia(int galaxia) {
        //forwardGalaxy
        boolean re = false;
        int numre=0;
        do{
        ((JavascriptExecutor) driver).executeScript("submitOnKey(39);");
            try {
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("galaxyLoading")).isDisplayed();
                    }
                });
                consola("Log: Galaxia: Cargada siguiente - "  + String.valueOf(getCurrentGalaxia()));
                re = false;
            } catch (Exception e) {
                consola("Error: Galaxia: Error recargando siguiente galaxia - " + e.getMessage());
                numre++;
                re = true;
                consola("Reintento numero " + String.valueOf(numre));
            }
            re = (numre == 3 || (getCurrentGalaxia() > galaxia)) ? false : true;
        }while(re);
        
        try {
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return !d.findElement(By.id("galaxyLoading")).isDisplayed();
                    }
                });
            } catch (Exception e) {
                consola("Error: Galaxia: Error recargando siguiente galaxia, no se quita la espera - " + e.getMessage());
            }
        return getCurrentGalaxia();
    }

    public int anteriorGalaxia(int galaxia) {
        //forwardGalaxy
        boolean re = false;
        int numre=0;
        do{
        ((JavascriptExecutor) driver).executeScript("submitOnKey(37);");
            try {
                (new WebDriverWait(driver, 15)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("galaxyLoading")).isDisplayed();
                    }
                });
                re = false;
                consola("Log: Galaxia: Cargada anterior: " + String.valueOf(getCurrentGalaxia()));
            } catch (Exception e) {
                consola("Error: Galaxia: Error recargando - " + e.getMessage());
                numre++;
                re = true;
                consola("Reintento numero " + String.valueOf(numre));
            }
            re = (numre == 3 || (getCurrentGalaxia() < galaxia)) ? false : true;
        }while(re);
        try {
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return !d.findElement(By.id("galaxyLoading")).isDisplayed();
                    }
                });
            } catch (Exception e) {
                consola("Error: Galaxia: Error recargando anterior galaxia, no se quita la espera - " + e.getMessage());
            }
        return getCurrentGalaxia();
    }
    
    public int getCurrentGalaxia()
    {
        try {
                (new WebDriverWait(driver, 15)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("system_input")).isEnabled();
                    }
                });
                consola("Log: Galaxia: Cargada " + driver.findElement(By.id("system_input")).getText());
            } catch (Exception e) {
                consola("Error: Galaxia: Error recargando en getcurrentgalaxia - " + e.getMessage());
            }
        //return Integer.parseInt(driver.findElement(By.id("system_input")).getText());
        return Integer.parseInt(String.valueOf(((JavascriptExecutor) driver).executeScript("return system;")));
    }

    public void abrirBuzon() {
        //message_alert_box
        driver.findElement(By.id("message_alert_box")).click();

        try {
            (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                public Boolean apply(WebDriver d) {
                    return d.findElement(By.id("mailz")).isEnabled();
                }
            });
            consola("Log: Mensajes: lista de mensajes");
            driver.switchTo().defaultContent();
            //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
        } catch (Exception e) {
            consola("Error: Mensajes: Carga lista de mensajes - " + e.getMessage());
        }
    }

    public void leerReportesEspionaje() {
        Db db = new Db();
        double metal, cristal, duty;
        List<WebElement> espionajes = driver.findElements(By.partialLinkText("Reporte de espionaje de"));
        WebElement espia;
        List<WebElement> flotas;
        boolean sindef = true;
        String helper;
        for (WebElement row : espionajes) {
            driver.switchTo().defaultContent();
            row.click();
            try {
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("TB_iframeContent")).isEnabled();
                    }
                });
                //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
            } catch (Exception e) {
                consola("Error: Mensajes: No abre Iframe - " + e.getMessage());
                consola("Reintentando");
                row.click();
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("TB_iframeContent")).isEnabled();
                    }
                });

            }
            driver.switchTo().frame("TB_iframeContent");

            try {
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("showmessage")).isEnabled();
                    }
                });
                consola("Log: Mensajes: Espionaje cargado");
                //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
            } catch (Exception e) {
                consola("Error: Mensajes: Error Espionaje cargado - " + e.getMessage());
            }
            espia = driver.findElement(By.className("spy2"));
            for (WebElement td : espia.findElements(By.tagName("td"))) {
                consola(td.getText());
            }
            helper = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[3]/td")).getText();
            consola(helper.substring(helper.indexOf("[") + 1, helper.indexOf("]")));
            //fleetdefbuildings spy
            flotas = driver.findElements(By.className("fleetdefbuildings"));
            if (flotas.size() > 0)
            if (("Flotas".equals(flotas.get(0).findElement(By.tagName("th")).getText())) && (flotas.get(0).findElements(By.tagName("td")).size() == 0))
            {
                consola("no hay flotas");
                if ((flotas.size() > 1)&&("Defensa".equals(flotas.get(1).findElement(By.tagName("th")).getText())))
                {
                    for(WebElement d : flotas.get(1).findElements(By.className("key")))
                    {
                        if(!d.getText().startsWith("Misil"))
                            sindef = false;
                        
                        consola("Aqui hay " + d.getText());
                    }
                    if(sindef)
                    {
                        metal = Double.valueOf(EliminaCaracteres(espia.findElements(By.tagName("td")).get(1).getText(),"."));
                        cristal = Double.valueOf(EliminaCaracteres(espia.findElements(By.tagName("td")).get(3).getText(),"."));
                        duty = Double.valueOf(EliminaCaracteres(espia.findElements(By.tagName("td")).get(5).getText(),"."));

                        db.setEspionaje(helper.substring(helper.indexOf("[") + 1, helper.indexOf("]")), metal, cristal, duty, planeta);
                    }
                    sindef = true;
                }
            }
            driver.findElement(By.className("closeTB")).click();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                consola("Error: no duerme para cerrar mensajes");
                //Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int atacar(int naves) throws InterruptedException {
        Db db = new Db();
        for(Espionaje es : db.getEspionaje())
        {
            if(naves > 0)
            {
                naves -= es.getNaves();
                if(setAtaque(es.getPlaneta(), es.getNaves()))
                    db.deleteEspionaje(es.getId());
            }
        }
        return naves;
    }
    
    public boolean setAtaque(String planeta, int naves) throws InterruptedException
    {
        consola("Intentando atacar a: " + planeta);
        String[] coor = planeta.split(":");
        irAMenu("Flota");
        int re = 0;
        try {
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("ship_203")).isEnabled();
                    }
                });
                consola("Log: Ataque: Cargando flota");
                //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
            } catch (Exception e) {
                consola("Error: Ataque: No carga flota - " + e.getMessage());
                return false;
            }
        WebElement txt_naves = driver.findElement(By.id("ship_203"));
        do{
            txt_naves.sendKeys(String.valueOf(naves));
            Thread.sleep(500);
        }while("".equals(driver.findElement(By.id("ship_203")).getAttribute("value")));
        Thread.sleep(500);
        driver.findElement(By.id("continue")).click();
        //galaxy            
        driver.switchTo().defaultContent();

        try {
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("galaxy")).isEnabled();
                    }
                });
                consola("Log: Ataque: Cargando coordenadas");
                //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
            } catch (Exception e) {
                consola("Error: Ataque: No carga coordenadas - " + e.getMessage());
                return false;
            }
        //driver.findElement(By.id("galaxy")).sendKeys(coor[0]);
        consola("Galaxia " + coor[0]);
        //do{
        //driver.findElement(By.id("system")).sendKeys(coor[1]);
        ((JavascriptExecutor) driver).executeScript("$(\"#system\").val(\""+ coor[1] +"\")");
        driver.findElement(By.id("position")).sendKeys(coor[2]);
        ((JavascriptExecutor) driver).executeScript("$(\"#position\").val(\""+ coor[2] +"\")");
        Thread.sleep(500);
       // re++;
        consola("Fijando coordenadas a " + String.valueOf(coor[1]) + ":" + String.valueOf(coor[2]));
    //}while((!String.valueOf(coor[1]).equals(driver.findElement(By.id("system")).getAttribute("value")) && !String.valueOf(coor[2]).equals(driver.findElement(By.id("position")).getAttribute("value"))) || re < 3);
        
        driver.findElement(By.id("continue")).click();
        //missionButton1            
        driver.switchTo().defaultContent();
        try {
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.id("missionButton1")).isEnabled();
                    }
                });
                consola("Log: Ataque: Cargando ataque");
                //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
            } catch (Exception e) {
                consola("Error: Ataque: No carga ataque - " + e.getMessage());
                return false;
            }
        driver.findElement(By.id("missionButton1")).click();
        //start
        Thread.sleep(200);
        driver.findElement(By.id("start")).click();
        //Recargar
        try {
                (new WebDriverWait(driver, 30)).until(new ExpectedCondition<Boolean>() {

                    public Boolean apply(WebDriver d) {
                        return d.findElement(By.linkText("Recargar")).isEnabled();
                    }
                });
                consola("Log: Ataque: Ataque Realizado");
                //((JavascriptExecutor) driver).executeScript("$(\".success\").remove();");
            } catch (Exception e) {
                consola("Error: Ataque: Ataque Fallido - " + e.getMessage());
                return false;
            }
        return true;
    }

    public String EliminaCaracteres(String s_cadena, String s_caracteres) {
        String nueva_cadena = "";
        Character caracter = null;
        boolean valido = true;

        /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
        sólo los caracteres que no estén en la cadena s_caracteres */
        for (int i = 0; i < s_cadena.length(); i++) {
            valido = true;
            for (int j = 0; j < s_caracteres.length(); j++) {
                caracter = s_caracteres.charAt(j);

                if (s_cadena.charAt(i) == caracter) {
                    valido = false;
                    break;
                }
            }
            if (valido) {
                nueva_cadena += s_cadena.charAt(i);
            }
        }

        return nueva_cadena;
    }
}
