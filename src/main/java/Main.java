/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Erkan_Kaya_HW4;

/**
 *
 * @author eerka
 */
public class Main {
     public static void main(String[] args){
     
     HW4_Hash hashObj = new HW4_Hash();
     hashObj.ReadFileandGenerateHash("C:\\Users\\eerka\\Desktop\\HW4Data.txt", 10000 ); 
     hashObj.checkWord("Acayip");
     hashObj.checkWord("cold");
     System.out.println("There are " + hashObj.NumberOfCollusion()+ " collisions occured");
     System.out.println("Load Factor-based Efficiency: " + hashObj.TestEfficiency() + "%");
     
     
      
     
    
      
     
     }
    
}
