import Model.Model;
import Model.WanrersRule.WarnsdorffRule;
import Model.WorstCase.WorstCase;

public class Main {


    //so you write the board size as rows cols
    public static void main(String[] args){
        if(args.length < 2)
        { System.out.println("please enter more arguments"); return; }

        int rows;
        int cols;
        try{
            rows = Integer.parseInt(args[0]);
            cols = Integer.parseInt(args[1]);
        }catch (Exception e){
            System.out.println("the configuration is not possible");
            System.out.println("People enter the row and column");
            return;
        }
        if(rows < 5 && cols < 5){
            System.out.println("the current board size has no solutions");
            return;
        }

        Model model = new WarnsdorffRule(rows, cols);;
//        if(args.length >= 3) model = new WorstCase(rows, cols, args[2]);
//        else  model = new WorstCase(rows, cols);

        String result = "";
        for(int c1 = 0; c1 < rows; c1++){
            result += "|";
            for(int c2 = 0; c2 < cols; c2++){
                String value = String.valueOf(model.getValueAtPosition(c1, c2));
                while (value.length() < 3)  value = value+ " ";
                result += value+"|";
            }
            result += "\n";
        }
        System.out.println(result);
    }//end main function
}//end class
