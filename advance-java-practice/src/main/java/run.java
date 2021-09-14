import java.util.*;

public class run {
    public static void main(String[] arg){
       System.out.println(uniqueCharacters("adfg"));
    }

    static boolean uniqueCharacters(String str)
    {
        // Assuming string can have characters a-z
        // this has 32 bits set to 0
        int checker = 0;

        for (int i = 0; i < str.length(); i++) {
            int bitAtIndex = str.charAt(i) - 'a';

            // if that bit is already set in checker,
            // return false
            if ((checker & (1 << bitAtIndex)) > 0)
                return false;

            // otherwise update and continue by
            // setting that bit in the checker
            checker = checker | (1 << bitAtIndex);
        }

        // no duplicates encountered, return true
        return true;
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
