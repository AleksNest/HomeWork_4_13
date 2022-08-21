import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Task_13 {

    public static String infixToPostfix(String infix) {                 // метод конвертации выражения с инфексного в постфиксное

        HashMap<String, Integer> priority = new HashMap<>();            // инициализация словаря для хранения приоритета оп операции
        Stack<String> opStack = new Stack<>();                          // для хранения операторов
        ArrayList<String> postfixList = new ArrayList<>();               // список для формирования постинфексной записи выражения (для вывода)
        infix = infix.replace("(", "( ").replace(")", " )").replaceAll("\\s+", " ").trim();  // разделяем элементы строк через один пробел, удаляем крайние пробелы
        ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(infix.split(" ")));          // перобразуем строку в список строк


        priority.put("*", 3);                                             // в словарь по ключу опрерации проставляем приоритет, в порядке возрастания
        priority.put("/", 3);
        priority.put("+", 2);
        priority.put("-", 2);
        priority.put("(", 1);

        for (String token : tokenList) {
            if (token.matches("[-+]?\\d+")) {
                postfixList.add(token);                                     // добавить в выходной список
            } else if (token.contains("(")) {
                opStack.push(token);                                        //  Если токен является левой скобкой, положить его в opstack
            } else if (token.contains(")")) {
                String topToken = opStack.pop();                            // Если токен является правой скобкой, то выталкивать элементы из opstack пока не будет найдена соответствующая левая скобка. Каждый оператор добавлять в конец выходного списка.
                while (!topToken.contains("(")) {
                    postfixList.add(topToken);
                    topToken = opStack.pop();
                }
            } else {
                while ((!opStack.empty()) && (priority.get(opStack.peek()) >= priority.get(token))) {
                    postfixList.add(opStack.pop());
                }
                opStack.push(token);
            }
        }

        while (!opStack.isEmpty()) {
            postfixList.add(opStack.pop());
        }

        return postfixList.toString().replace("[", "").replace("]", "");
    }

    public static String PostfixCalc(String input) {                            // метод вычисления постфиксного выражения
        String[] arrayInput = input.split(", ");
        Stack<Integer> calc = new Stack<Integer>();
        int x = 0;
        int y = 0;
        int r = 0;

        for (int i = 0; i < arrayInput.length; i++) {
            if (arrayInput[i].matches("[-+]?\\d+")) {
                r = Integer.parseInt(arrayInput[i]);
                calc.push(r);
            } else if (arrayInput[i].contains("+")) {
                x = calc.pop();
                y = calc.pop();
                calc.push(x + y);
            } else if (arrayInput[i].contains("-")) {
                x = calc.pop();
                y = calc.pop();
                calc.push(y - x);
            } else if (arrayInput[i].contains("*")) {
                x = calc.pop();
                y = calc.pop();
                calc.push(x * y);
            } else {                                                         //if (arrayInput[i].contains("/")) {
                x = calc.pop();
                y = calc.pop();
                calc.push(x / y);
            }
        }
        return calc.pop().toString();
    }

    public static void main(String[] args) {
        String infix = "5 - (60  * 2) + (25 - 5 * 5)";
        System.out.println(infixToPostfix(infix));
        System.out.println(PostfixCalc(infixToPostfix(infix)));
    }
}
