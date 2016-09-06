/*
 * Created on Sep 6, 2016
 */
package jugmeetup.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class CollectionExamples {
    static class BabyName {
        public String firstName;
        public char gender;
        public int count;
        public BabyName(String firstName, char gender, int count) {
            this.firstName = firstName;
            this.gender = gender;
            this.count = count;
        }
    }
    
    private static BabyName parseLine(String line) {
        String[] p = line.split(",");
        return new BabyName(p[0], p[1].charAt(0), Integer.parseInt(p[2]));
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(new File("yob2015.txt"))) {
            List<BabyName> allNames = new ArrayList<>();
            while(sc.hasNextLine()) allNames.add(parseLine(sc.nextLine()));

            List<BabyName> commonNames = new ArrayList<BabyName>();
            for(BabyName bn : allNames) if(bn.count > 10000) commonNames.add(bn);
            
            int numberOfFemaleNames = 0;
            for(BabyName bn : allNames) if(bn.gender == 'F') numberOfFemaleNames++;
            
            int numberOfMaleNames = 0;
            for(BabyName bn : allNames) if(bn.gender == 'M') numberOfMaleNames++;

            int numberOfFemaleBabies = 0;
            for(BabyName bn : allNames) if(bn.gender == 'F') numberOfFemaleBabies += bn.count;
                    
            List<BabyName> sortedByCount = Sorts.mergeSort(allNames, new Comparator<BabyName>() {
                public int compare(BabyName bn1, BabyName bn2) {
                    return Integer.compare(bn1.count, bn2.count);
                }
            });
            for(BabyName bn : sortedByCount.subList(sortedByCount.size()-3, sortedByCount.size()))
                System.out.println(bn);
            
            int nameCount = -1;
            for(BabyName bn : allNames) if(bn.firstName == "Mark") nameCount = bn.count;  // Something different
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
