package com.jjgomez2k.testesimios.util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class MatrixUtils {

    private static final List<String> validSimiosDna = Arrays.asList("CCCC","TTTT","GGGG","AAAA");
    private static final Pattern pattern = Pattern.compile("[^AacCgGtT]");

    public static void containVerticalOrHorizontal(char[][] matrix, AtomicInteger totalSimiosSequences){
        for (int i = 0; i < matrix.length ; i++) {
            StringBuilder sbVertical = new StringBuilder();
            StringBuilder sbHorizontal = new StringBuilder();

            for (int ii = 0; ii < matrix.length ; ii++) {
                sbVertical.append(matrix[ii][i]);
                sbHorizontal.append(matrix[i][ii]);
            }
            incrementIfExistsSimiosDNA(totalSimiosSequences, sbHorizontal.toString());
            incrementIfExistsSimiosDNA(totalSimiosSequences, sbVertical.toString());
        }
    }
    public static void findDiagonalyReverseTopDown(int tamanho, char[][] matrix, AtomicInteger totalSimiosSequences) {
        String  result = "";
        for(int linha = 1; tamanho-linha != 3; linha++){
            int oldCol = 0;
            int oldRow = 0;
            for(int coluna=0; coluna  < tamanho-linha ; coluna ++){
                if(coluna==0){
                    result = result + matrix[oldRow = linha][oldCol = tamanho - 1];
                }else{
                    result = result + matrix[oldRow += 1][oldCol = oldCol-1];
                }
            }
            incrementIfExistsSimiosDNA(totalSimiosSequences, result);
            result = "";
        }
    }
    public static void incrementIfExistsSimiosDNA(AtomicInteger totalSimiosFounded, String dnaSequence){
        if(containsSimiosDNA(dnaSequence))
            totalSimiosFounded.getAndIncrement();
    }
    public static void findDiagonalyReverseMidTop(int tamanho, char[][] matrix, AtomicInteger totalSimiosSequences) {
        String result = "";
        for(int linha = 1; tamanho-linha != 2; linha++){
            int oldCol = tamanho-linha;
            int oldRow = 0;
            for(int line=0; line < tamanho-(linha-1); line ++){
                if(line==0){
                    result = result + matrix[oldRow = 0][oldCol = tamanho - linha];
                }else{
                    result = result + matrix[oldRow += 1][ oldCol = oldCol-1];
                }
            }
            incrementIfExistsSimiosDNA(totalSimiosSequences, result);
            result = "";
        }
    }
    public static void findDiagonalyMidTop(int tamanho, char[][] matrix, AtomicInteger totalSimiosSequences) {
        String result = "";
        for(int coluna = 1; tamanho-coluna != 3; coluna++){
            int oldCol = 0;
            int oldRow = 0;
            for(int linha=0; linha < tamanho-coluna; linha ++){
                if(linha==0){
                    result = result + matrix[oldRow = 0][oldCol = coluna];
                }else{
                    result = result + matrix[oldRow+=1][ oldCol+=1];
                }
            }
            incrementIfExistsSimiosDNA(totalSimiosSequences, result);
            result = "";
        }
    }
    public static void findDiagonalyTopDown(int tamanho, char[][] matrix, AtomicInteger totalSimiosSequences) {
        String result = "";
        for(int line = 0; tamanho-line != 3; line++){
            for(int coluna=0; coluna < tamanho-line; coluna ++){
                if(coluna==0){
                    result = result + matrix[line][0];
                }else{
                    result = result + matrix[line+coluna][coluna];
                }
            }
            incrementIfExistsSimiosDNA(totalSimiosSequences, result);
            result = "";
        }
    }

    public static boolean isValidDnaSequence(String[] dnaSequence){
        return !Arrays.stream(dnaSequence)
                .anyMatch(dna -> pattern.matcher(dna).find() || dna.length() != dnaSequence.length );
    }
    private static boolean containsSimiosDNA(String sequenceDNA){
        return validSimiosDna.stream().anyMatch(p->
                sequenceDNA.contains(p));
    }
}