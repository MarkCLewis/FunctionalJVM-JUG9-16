/*
 * Created on Sep 5, 2016
 */
package jugmeetup.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorts {
    private static <A> List<A> merge(List<A> lst1, List<A> lst2, List<A> merged, Comparator<A> comp) {
        if (lst1.isEmpty()) {
            merged.addAll(lst2);
            return merged;
        } else if (lst2.isEmpty()) {
            merged.addAll(lst1);
            return merged;
        } else if (comp.compare(lst1.get(0), lst2.get(0)) < 0) {
            merged.add(lst1.get(0));
            return merge(lst1.subList(1, lst1.size()), lst2, merged, comp);
        } else {
            merged.add(lst2.get(0));
            return merge(lst1, lst2.subList(1, lst2.size()), merged, comp);
        }
    }

    public static <A> List<A> mergeSort(List<A> lst, Comparator<A> comp) {
        if (lst.size() < 2)
            return lst;
        else {
            List<A> left = lst.subList(0, lst.size() / 2);
            List<A> right = lst.subList(lst.size() / 2, lst.size());
            return merge(mergeSort(left, comp), mergeSort(right, comp), new ArrayList<A>(), comp);
        }
    }

    public static void main(String[] args) {
        List<Double> nums = new ArrayList<Double>();
        for (int i = 0; i < 10; ++i)
            nums.add(Math.random());
        System.out.println(mergeSort(nums, (x, y) -> Double.compare(x, y)));
    }
}
