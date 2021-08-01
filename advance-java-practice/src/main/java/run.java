import java.util.*;

public class run {
    public static void main(String[] arg){
       System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 3));
    }

    static int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> meanHeap = new PriorityQueue<>((a,b) -> b - a);
        for(int i : nums){
            meanHeap.add(i);
            if(meanHeap.size() > k){
                meanHeap.remove();
            }
        }
        return meanHeap.remove();
    }

    static int lengthOfLongestSubstring(String s) {
        if(s==null)
            return 0;
        int size = 0, left = 0;
        Set<Character> seen = new HashSet<>();
        for(int j=0;j<s.length();j++){
            while(seen.contains(s.charAt(j))){
                seen.remove(s.charAt(left++));
            }
            seen.add(s.charAt(j));
            size = Math.max(size, j - left + 1);
        }
        return size;
    }
}
