public class Simple{
    private String word;
    private String phrase;
    public Simple(int number, String w) {
        word = w;
        phrase = mystery(number, w);
    }
    private String mystery(int num, String s) {
        String answer = "";
        for (int k=0; k<num; k++) {
            answer = answer + s;
        }
        return answer;
    }

    public String toString() {
        return phrase + " is " + word + " repeated";
    }

    public class TestSimple{
        public void print() {
            Simple item = new Simple(3, "blue");
            System.out.println(item);
            System.out.println(item.mystery(5,"ho"));
        }
    }

    public static void main(String[] args) {
        Simple s = new Simple(3,"blue");
        System.out.println(s);
    }
}

