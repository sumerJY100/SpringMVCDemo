package com;

import com.gaussic.model.dcs_history.H000Pojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.sql.Timestamp;
import java.util.List;

public class Test3 {
    public static void main(String[] args) throws IOException {

        for(int i=0;i<200;i++) {
            String name = "006";
            name = String.format("%03d",i);
            File file = new File("d:/H" + name + "Rep.java");
//        FileInputStream fileInputStream = new FileInputStream(file);
            FileWriter fileWriter = new FileWriter(file);
            String s1 = "package com.gaussic.repository.dcs_history;";

            String s2 = "";
            String s3 = "import com.gaussic.model.dcs_history.H" + name + "Pojo;";
            String s4 = "import org.springframework.data.jpa.repository.JpaRepository;";
            String s5 = " import org.springframework.stereotype.Repository;";

            String s51 = "import java.sql.Timestamp;";
            String s52 = "import java.util.List;";
            String s6 = "    @Repository";
            String s7 = " public interface H" + name + "Rep extends JpaRepository<H" + name + "Pojo,Long> {";
            String s71 = "   List<H"+name+"Pojo> findByVTimeBetween(Timestamp begin,Timestamp end);";
            String s8 = " }";


            fileWriter.write(s1 + "\n");
            fileWriter.write(s2 + "\n");
            fileWriter.write(s3 + "\n");
            fileWriter.write(s4 + "\n");
            fileWriter.write(s5 + "\n");
            fileWriter.write(s51 + "\n");
            fileWriter.write(s52+ "\n");
            fileWriter.write(s6 + "\n");
            fileWriter.write(s7 + "\n");
            fileWriter.write(s71 + "\n");
            fileWriter.write(s8 + "\n");
            fileWriter.close();
        }
    }
}
