/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author byde
 */
public class Espionaje {
    
    private String planeta;
    private double metal;
    private double cristal;
    private double duty;
    private int id;
    
    public Espionaje(String planeta, double metal, double cristal, double duty, int id){
        this.planeta = planeta;
        this.metal = metal;
        this.cristal = cristal;
        this.duty = duty;
        this.id = id;
    }
    
    public String getPlaneta()
    {
        return planeta;
    }
    
    
    public int getId()
    {
        return this.id;
    }
    
    public int getNaves()
    {
        Double d = Math.ceil(((metal+cristal+duty)/2)/25000);
        return d.intValue();
    }
}
