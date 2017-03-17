package main.java.com.github.sergeyrebrov.brainfuck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sergey on 03.03.2017.
 */


/*
++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.

++++++++[>++++++++<-]>+.
*/

public class Brainfuck {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        toHumanLanguage();
        toBrainfuckLanguage();
    }

    private static void toHumanLanguage() throws IOException {
        char[] bfs = reader.readLine().toCharArray();
        char[] array = new char[30000];

        StringBuilder stringBuilder = new StringBuilder();
        int position = 0;
        int marker = 0;

        for (int i = 0; i < bfs.length; i++) {
            switch (bfs[i]) {
                case ('>'):
                    position++;
                    break;
                case ('<'):
                    position--;
                    break;
                case ('+'):
                    array[position]++;
                    break;
                case ('-'):
                    array[position]--;
                    break;
                case ('.'):
                    stringBuilder.append(array[position]);
                    break;
                case (','):
                    array[position] = (char) reader.read();
                    break;
                case ('['):
                    if (array[position] == 0) {
                        marker++;
                        while (marker > 0) {
                            i++;
                            if (bfs[i] == '[') marker++;
                            if (bfs[i] == ']') marker--;
                        }
                    }
                    break;
                case (']'):
                    if (array[position] != 0) {
                        if (bfs[i] == ']') marker++;
                        while (marker > 0) {
                            i--;
                            if (bfs[i] == '[') marker--;
                            if (bfs[i] == ']') marker++;
                        }
                        i--;
                    }
            }
        }

        System.out.println(stringBuilder.toString());
    }

    private static void toBrainfuckLanguage() throws IOException {
        char[] chars = reader.readLine().toCharArray();

        StringBuilder stringBuilder = new StringBuilder();

        int distance = 0;
        for (int i = 0; i < chars.length; i++) {
            if ((int) chars[i] - distance < 10 && (int) chars[i] - distance > 0) {

                for (int j = 0; j < (int) chars[i] - distance; j++)
                    stringBuilder.append('+');

                stringBuilder.append('.');
                distance = chars[i];
            } else if (distance - (int) chars[i] < 10 && distance - (int) chars[i] > 0) {

                for (int j = 0; j < distance - (int) chars[i]; j++)
                    stringBuilder.append('-');

                stringBuilder.append('.');
                distance = chars[i];
            } else {
                distance = chars[i];

                if (i > 0) stringBuilder.append('>');

                int a = chars[i] / 10;
                int b = chars[i] % 10;

                for (int j = 0; j < 10; j++)
                    stringBuilder.append('+');

                stringBuilder.append('[').append('>');

                for (int j = 0; j < a; j++)
                    stringBuilder.append('+');

                stringBuilder.append('<').append('-').append(']').append('>');

                if (b > 0)
                    for (int j = 0; j < b; j++)
                        stringBuilder.append('+');

                stringBuilder.append('.');
            }
        }

        System.out.println(stringBuilder.toString());
    }
}
