/*
 * Created on Sep 6, 2016
 */
package jugmeetup.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import jugmeetup.java.CollectionExamples.BabyName;

public class CollectionExamplesJ8 {
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

            List<BabyName> commonNames = allNames.stream().filter(bn -> bn.count > 10000).collect(Collectors.toList());
            long numberOfFemaleNames = allNames.stream().filter(bn -> bn.gender == 'F').collect(Collectors.counting());
            long numberOfMaleNames = allNames.stream().filter(bn -> bn.gender == 'M').collect(Collectors.counting());
            int numberOfFemaleBabies = allNames.stream().filter(bn -> bn.gender == 'F').mapToInt(bn -> bn.count).sum();
                    
            List<BabyName> sortedByCount = Sorts.mergeSort(allNames, (bn1, bn2) -> Integer.compare(bn1.count, bn2.count));
            for(BabyName bn : sortedByCount.subList(sortedByCount.size()-3, sortedByCount.size()))
                System.out.println(bn);
            
            int nameCount = allNames.stream().filter(bn -> bn.firstName == "Mark").mapToInt(bn -> bn.count).findFirst().orElse(-1);          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
