// Shivangi Khanna

// This class is used to form sentences or expressions from the BFN form. Each symbol is created 
// randomly. It can form as many sentences as the user wants.
// This class can check if a symbol is terminal or not. 

import java.util.*;

public class GrammarSolver {
   
   private SortedMap<String, String[]> grammarMap;
   
   // Constructs GrammarSolver using BNF rules given by the user. The rules are stored in a
   // SortedMap. 
   // Throws an IllegalArgumentException if the list is empty or if there are two or more 
   // entries of the same non-terminal.
   public GrammarSolver(List<String> rules) {
      if(rules.isEmpty()) {
         throw new IllegalArgumentException();
      }
      grammarMap = new TreeMap<String, String[]>();
      for(String s: rules) {
         String[] sep = s.split("::+");
         String key = sep[0];
         sep = sep[1].split("\\|");
         if(grammarMap.containsKey(key)) {
            throw new IllegalArgumentException();
         }
         grammarMap.put(key, sep);
      }
   }
   
   // Returns true if the passed symbol is a non-terminal
   public boolean grammarContains(String symbol) {
      return grammarMap.containsKey(symbol);
   }
   
   // Returns a string form of the non-terminals. Comma separated and sorted.
   public String getSymbols() {
      return grammarMap.keySet().toString();
   }
   
   // Returns terminals for a symbol inputted by the user for as many times as the 
   // user inputs.
   // Throws IllegalArgumentException if the times is less than 0 or if the symbol 
   // entered is not a terminal
   public String[] generate(String symbol, int times) {
      if(times < 0 || !grammarContains(symbol)) {
         throw new IllegalArgumentException();
      }
      String[] lines = new String[times];
      for(int i = 0; i < times; i++) {
         lines[i] =  recGenerate(symbol);
      }
      return lines;
   }
   
   // Returns random terminals for a symbol inputted by the user.
   private String recGenerate(String symbol) {
      if(!grammarContains(symbol)) {
         return symbol;
      }
      String sentence = "";
      String[] rules = grammarMap.get(symbol);
      Random rand = new Random();
      int n = rand.nextInt(rules.length);
      String[] rule = rules[n].trim().split("\\s+");
      for(int i = 0; i < rule.length; i++) {
         sentence += recGenerate(rule[i]);
      }
      return sentence.trim();
   }
}