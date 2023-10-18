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
*/
public class drinkDetailDatabase{
    public Integer drinkID_; 
    public String drinkName_;  
    public String sugarLevel_;  
    public String iceLevel_;  
    public Double drinkPrice_;  
    public Double totalPrice_; 
    public List<HashMap<String, Integer>> toppingList_;

    /** 
    * Constructor: Initialize the data structure for each variable
    */
    public drinkDetailDatabase() {
        this.iceLevel_ = new String();
        this.sugarLevel_ = new String();
        this.toppingList_ = new ArrayList<>();
        this.toppingList_ = new ArrayList<>();
        this.drinkName_ = new String(); 
    }
}