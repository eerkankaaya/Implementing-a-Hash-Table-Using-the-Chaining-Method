/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Erkan_Kaya_HW4;

/**
 *
 * @author eerka
 */
public interface Hash_Interface {
 public Integer GetHash(String mystring);
 public void ReadFileandGenerateHash(String filename, int size);
 public void DisplayResult(String Outputfile);
 public void DisplayResult();
 public void DisplayResultOrdered(String Outputfile);
 public int showFrequency(String myword);
 public String showMaxRepeatedWord();
 public int checkWord(String myword);
 public float TestEfficiency();
 public int NumberOfCollusion();
}

