package utils;

import java.util.Random;

public class ObjectsGenerator {
    public static final char polybiusSquare[][] = {
            {'А', 'а', 'Б', 'б', 'В', 'в', 'Г', 'г', 'Д'},
            {'д', 'Е', 'е', 'Ё', 'ё', 'Ж', 'ж', 'З', 'з'},
            {'И', 'и', 'Й', 'й', 'К', 'к', 'Л', 'л', 'М'},
            {'м', 'Н', 'н', 'О', 'о', 'П', 'п', 'Р', 'р'},
            {'С', 'с', 'Т', 'т', 'У', 'у', 'Ф', 'ф', 'Х'},
            {'х', 'Ц', 'ц', 'Ч', 'ч', 'Ш', 'ш', 'Щ', 'щ'},
            {'Ъ', 'ъ', 'Ы', 'ы', 'Ь', 'ь', 'Э', 'э', 'Ю'},
            {'ю', 'Я', 'я', ' ', ',', '.', '!', '?', ':'}
    };

    public static final String alphabet72 = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя,.!?: *";
//    public static final String alphabet72 = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";


    public static int[][] generateMatrix(int size){
        int matrix[][];
        Random random = new Random(100);
        matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt();
            }
        }
        return matrix;
    }
}
