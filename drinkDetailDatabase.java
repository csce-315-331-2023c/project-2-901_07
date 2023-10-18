import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.awt.event.*;
import javax.swing.border.*;

/** 
* Create class to contain all member variable containing neccessary information
* for a chosen drink that is ready to be placed.
* @author Quy Van_ 
* @see drinkDetailDatabase
*/
public class drinkDetailDatabase{
    /**
     * drink ID
     */
    public Integer drinkID_; 
    /**
     * drink Name
     */
    public String drinkName_;  
    /**
     * sugar level
     */
    public String sugarLevel_;  
    /**
     * ice level
     */
    public String iceLevel_;  
    /**
     * base Price of the drink
     */
    public Double drinkPrice_;  
    /**
     * total price after adding total topping price
     */
    public Double totalPrice_; 
    /**
     * List of topping and count of each topping
     */
    public List<HashMap<String, Integer>> toppingList_;

    /** 
    * Constructor: Initialize the data structure for each variable
    * @see drinkDetailDatabase
    */
    public drinkDetailDatabase() {
        this.iceLevel_ = new String();
        this.sugarLevel_ = new String();
        this.toppingList_ = new ArrayList<>();
        this.toppingList_ = new ArrayList<>();
        this.drinkName_ = new String(); 
    }
}