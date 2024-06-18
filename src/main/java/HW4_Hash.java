/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Erkan_Kaya_HW4;

/**
 *
 * @author eerka
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HW4_Hash implements Hash_Interface {

    private int counterForCollision;
    private int sizeOfTable;
    private Node[] tableOfHash;

    public HW4_Hash() {
        this.counterForCollision = 0;
        this.sizeOfTable = sizeOfTable;
        this.tableOfHash = new Node[sizeOfTable];
    }

    private static class Node {

        String word;
        int position;
        Node next;

        public Node(String word, int position) {
            this.word = word;
            this.position = position;
            this.next = null;
        }
    }

    @Override
    public int checkWord(String theWord) {
        int currentHash = GetHash(theWord);
        Node present = tableOfHash[currentHash];
        int currentOccurrences = 0;

        while (present != null) {
            if (present.word.equalsIgnoreCase(theWord)) {
                currentOccurrences++;
                System.out.println(theWord + " is found in the text. Repeated " + currentOccurrences + " time. Location: " + present.position);
            }
            present = present.next;
        }

        if (currentOccurrences == 0) {
            System.out.println(theWord + " is not found in the text.");
            return -1;
        }

        return currentOccurrences;
    }

    @Override
    public Integer GetHash(String stringOfWord) {
        int sum;
        int hashValue = 0;

        for (int i = 0; i < stringOfWord.length(); i++) {
            if (stringOfWord.length() - i >= 2) {
                sum = 0;
                for (int j = 0; j < 2; j++) {
                    sum += Math.abs(Character.getNumericValue(stringOfWord.charAt(i++))
                            * (i + 1) * (i + 1) * (i + 1));
                }
                if (hashValue == 0) {
                    hashValue = sum;
                } else {
                    hashValue *= sum;
                }
            } else {
                sum = 0;
                for (; i < stringOfWord.length(); i++) {
                    sum += Math.abs(Character.getNumericValue(stringOfWord.charAt(i))
                            * (i + 1) * (i + 1) * (i + 1));
                }
                if (hashValue == 0) {
                    hashValue = sum;
                } else {
                    hashValue *= sum;
                }
            }
        }

        hashValue = Math.abs(hashValue);
        return hashValue % sizeOfTable;
    }

    @Override
    public void ReadFileandGenerateHash(String filename, int size) {
        sizeOfTable = size;
        tableOfHash = new Node[sizeOfTable];
        counterForCollision = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int pos = 1;
            String row;

            while ((row = br.readLine()) != null) {
                String[] wordsOfArc = processLine(row);

                for (String theWord : wordsOfArc) {
                    int takeOfhash = GetHash(theWord);

                    if (tableOfHash[takeOfhash] == null) {
                        tableOfHash[takeOfhash] = new Node(theWord, pos);
                    } else {
                        Node present = tableOfHash[takeOfhash];

                        while (present != null) {
                            if (present.word.equals(theWord)) {
                                break;
                            }

                            if (present.next == null) {
                                present.next = new Node(theWord, pos);
                                counterForCollision++;
                                break;
                            }

                            present = present.next;
                        }
                    }

                    pos++;
                }
            }
        } catch (IOException e) {

        }
    }

    private String[] processLine(String line) {
        return line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
    }

    @Override
    public void DisplayResult(String outputfile) {
        try (FileWriter Documenter = new FileWriter(outputfile)) {
            for (Node theNode : tableOfHash) {
                while (theNode != null) {
                    Documenter.write(theNode.word + ": " + theNode.position + "\n");
                    theNode = theNode.next;
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void DisplayResultOrdered(String outputfile) {
        for (Node theNode : tableOfHash) {

            Node sorted = null;
            Node present = theNode;

            while (present != null) {
                Node next = present.next;

                if (sorted == null || sorted.position >= present.position) {
                    present.next = sorted;
                    sorted = present;
                } else {
                    Node searching = sorted;
                    while (searching.next != null && searching.next.position < present.position) {
                        searching = searching.next;
                    }
                    present.next = searching.next;
                    searching.next = present;
                }

                present = next;
            }

            try (FileWriter documenter = new FileWriter(outputfile, true)) {
                while (sorted != null) {
                    documenter.write(sorted.word + ": " + sorted.position + "\n");
                    sorted = sorted.next;
                }
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void DisplayResult() {
        for (Node theNode : tableOfHash) {
            while (theNode != null) {
                System.out.println(theNode.word + ": " + theNode.position);
                theNode = theNode.next;
            }
        }
    }

    @Override
    public int showFrequency(String myword) {
        int theHash = GetHash(myword);
        Node present = tableOfHash[theHash];
        int occurrencesofWords = 0;

        while (present != null) {
            if (present.word.equals(myword)) {
                occurrencesofWords++;
            }
            present = present.next;
        }

        if (occurrencesofWords == 0) {
            return -1;
        }

        return occurrencesofWords;
    }

    @Override
    public String showMaxRepeatedWord() {
        String repeatedMax = "";
        int freqMax = 0;

        for (Node node : tableOfHash) {
            while (node != null) {
                int frequency = showFrequency(node.word);
                if (frequency > freqMax) {
                    freqMax = frequency;
                    repeatedMax = node.word;
                }
                node = node.next;
            }
        }

        return repeatedMax;
    }

    @Override
    public float TestEfficiency() {
        int sumOfWords = 0;

        for (Node theNode : tableOfHash) {
            Node present = theNode;

            while (present != null) {
                sumOfWords++;
                present = present.next;
            }
        }

        float loadFactor = (float) sumOfWords / sizeOfTable;
        return loadFactor * 100;
    }

    @Override
    public int NumberOfCollusion() {
        return counterForCollision;

    }

}