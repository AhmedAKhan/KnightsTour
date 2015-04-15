/**
 * Created by ahmed on 4/15/15.
 */
public class FindMaxRecursiveDepth {

    public static void recFunc(int depth){
        System.out.println("depth: " + depth);
//        try {
            recFunc(++depth);
//        }catch (StackOverflowError error){
//            System.out.println("maximum depth is: " + depth);
//        }
    }

    public static void main(String[] args){
        recFunc(0);

    }
}
