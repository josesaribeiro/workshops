package workshops.functional.fp2.exercises;

import workshops.functional.fp2.answers.LoggerModuleAnswer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by pwlodarski on 2016-03-08.
 */
public class ImperativeLoop1 {

    static Consumer<String> logger = LoggerModuleAnswer.defaultLogger;

    static void usersWhoPurchasedMostProducts(){
        try{
            List<String> lines=Files.readAllLines(
                    Paths.get(
                            ClassLoader.getSystemResource("fpjava/purchases.csv").toURI()
                    )
            );

            lines.remove(0); //header

            Map<String,Integer> counts=new HashMap<>();

            for (String line : lines) {
                // ????
                // split ","
                // get user
                // map compute
            }

            counts.entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .map(entry -> entry.getKey()+" : "+entry.getValue())
                    .forEach(logger);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        usersWhoPurchasedMostProducts();
    }

}
