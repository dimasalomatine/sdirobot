/*
 * Copyright (C) 2017 dmitry
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
