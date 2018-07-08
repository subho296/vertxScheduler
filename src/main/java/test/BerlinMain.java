package test;

public class BerlinMain {

    public static void main(String[] args) {
        System.out.println(solution(18, 2));
    }

    public static int solution(int n, int k){
        int count = 0;
        while (n > 1 ){

            if (n % 2 == 0 && k > 0){
                n /=2;
                count ++;
                k--;
            } else {
                count ++;
                n--;
            }
        }
        return count;
    }
}
