/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webapi;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 *
 * @author dmitry
 */
public class StaticHolder {
    public StaticHolder(){
    
        if(inst==null){
         inst=this;
         
        }
    }
    public static StaticHolder getInstance(){
     if(inst==null){
       return new StaticHolder().getInst();
     }
     return inst;
    }
    private StaticHolder getInst(){return inst;}
    private static StaticHolder inst=null; 
    
    // create gpio controller instance
    static final GpioController gpio = GpioFactory.getInstance();
    public static GpioController getGpioController(){
     return gpio;
    }
    
    public static void main(String arg[]){
      StaticHolder.getInstance();
      GpioController gpio=StaticHolder.getGpioController();
      
    }
    
}
