package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import poslanciDB.PersistenceMap;

import java.io.File;


/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Wrong number of parameters.");
            System.out.println("Arguments: [path to database]");
            return;
        }
        if(!setDatabaseAbsolutePath(args[0])) {
            System.out.println("Wrong path to database.");
            System.out.println("Arguments: [path to database]");
            return;
        }
        SpringApplication.run(Application.class);
    }

    private static boolean setDatabaseAbsolutePath(String path) {
        File file = new File(path);
        if(!file.exists()) return false;
        String absolutePath = file.getAbsolutePath();
        PersistenceMap.setUrl(absolutePath);
        return true;
    }
}
