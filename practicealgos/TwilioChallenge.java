package practicealgos;



	import java.io.*;
	import java.math.*;
	import java.security.*;
	import java.text.*;
	import java.util.*;
	import java.util.concurrent.*;
	import java.util.function.*;
	import java.util.regex.*;
	import java.util.stream.*;
	import static java.util.stream.Collectors.joining;
	import static java.util.stream.Collectors.toList;
	
	public class TwilioChallenge {
	    /*
	     * Complete the 'vanity' function below.
	     *
	     * The function is expected to return a STRING_ARRAY.
	     * The function accepts following parameters:
	     *  1. STRING_ARRAY codes
	     *  2. STRING_ARRAY numbers
	     */

	    public static List<String> vanity(List<String> codes, List<String> numbers) {
	        // list of code string and list of number strings as input
	        // match each letter with a given digit 0-9
	        // loop over numbers, for each code: convert codes into digits
	        // find if this nb exists inside the number (get index)
	        // create new string with this number (substring), replacing its digits part with the letters
	        // add this number that is in numbers array to resulting array
	        // sort resulting array
	        
	        // hashmap to be used to convert codes into numbers
	        HashMap<Character, Integer> convert = new HashMap<Character, Integer>();
	        int keypad = 2;
	        for(int i = 65; i < 90; i = i+3){
	            convert.put((char) i, keypad);
	            convert.put((char) (i+1), keypad);
	            convert.put((char) (i+2), keypad);
	            keypad++;
	        }
	        
	        // resulting array
	        List<String> result = new ArrayList<String>();
	        
	        // getting the resulting array
	        for(int i = 0; i < codes.size(); i++){
	            for(int j = 0; j < numbers.size(); j++){
	                String code = codes.get(i);
	                // convert to number, better to use stringbuilder
	                String codeNb = "";
	                for(int k = 0; k < code.length(); k++){
	                    // get the nb value of the letter
	                    codeNb += convert.get(code.charAt(k));
	                }
	                
	                // check if exists inside number, if yes, concat and add to result
	                String nb = numbers.get(j);
	                if(nb.indexOf(codeNb) != -1){
	                   int indexStart = nb.indexOf(codeNb);
	                   int indexEnd = indexStart + (codeNb.length()-1);
	                    result.add(nb.substring(0, indexStart) + codeNb + nb.substring(indexEnd+1));
	                }
	            }
	        }
	     Collections.sort(result);
	     return result;
	    }

	
	    public static void main(String[] args) throws IOException {
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

	        int codesCount = Integer.parseInt(bufferedReader.readLine().trim());

	        List<String> codes = IntStream.range(0, codesCount).mapToObj(i -> {
	            try {
	                return bufferedReader.readLine();
	            } catch (IOException ex) {
	                throw new RuntimeException(ex);
	            }
	        })
	            .collect(toList());

	        int numbersCount = Integer.parseInt(bufferedReader.readLine().trim());

	        List<String> numbers = IntStream.range(0, numbersCount).mapToObj(i -> {
	            try {
	                return bufferedReader.readLine();
	            } catch (IOException ex) {
	                throw new RuntimeException(ex);
	            }
	        })
	            .collect(toList());

	        List<String> result = TwilioChallenge.vanity(codes, numbers);

	        bufferedWriter.write(
	            result.stream()
	                .collect(joining("\n"))
	            + "\n"
	        );

	        bufferedReader.close();
	        bufferedWriter.close();
	    }
}
