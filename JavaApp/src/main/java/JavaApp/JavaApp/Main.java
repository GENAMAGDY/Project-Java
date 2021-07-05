/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaApp.JavaApp;

import java.util.List;
import smile.data.DataFrame;
import smile.data.vector.StringVector;

/**
 *
 * @author original
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // TODO code application logic here
        DAO obj = new DAO();
        DataFrame df = obj.readData_FromCSV("src/data/Wuzzuf_Jobs.csv");
        List<POJO> listData= obj.getPOJOList(df);
        System.out.println("Summary of data: ");
        System.out.println(df.summary());
        System.out.println("value count"+String.valueOf(df.summary().column("min")));
        
        System.out.println("-----------------------------------------------------------");
        System.out.println("Structure of data: ");
        System.out.println(df.structure());
        System.out.println("-----------------------------------------------------------");
        
        df = obj.preprocessing(df);
        
        //pie chart for companies
        obj.pieChart(listData);
        
        //Count the jobs for each company and display that in order
        System.out.println("Count for each company");
        System.out.println(obj.getCompaniesList(listData));
        System.out.println("-----------------------------------------------------------");

        
        //pie chart for companies        
        obj.barChart_Title(listData);
        
        //Count the jobs for each company and display that in order
        System.out.println("Count for each Title");
        System.out.println(obj.getTitlesList(listData));
        System.out.println("-----------------------------------------------------------");

     
        //pie chart for companies        
        obj.barChart_Area(listData);
        
        //Count the jobs for each company and display that in order
        System.out.println("Count for each Countries");
        System.out.println(obj.getCountriesList(listData));
        System.out.println("-----------------------------------------------------------");

        
        System.out.println("Skills for each Title Job ");
        System.out.println(obj.getSkillsForEachCompany(listData));
        
        System.out.println("All Skills ordered");
        System.out.println(obj.getAllSkill(obj.getSkillsForEachCompany(listData)));
        System.out.println("------------------------------------------------------------");

        System.out.println("Factorize the YearsExp feature and convert it to numbers in new col:");
        System.out.println(obj.factorizeYearExp(listData));
        System.out.println("DataFrame after adding new column (YearExpFactorize)");
        String str[] = new String[obj.factorizeYearExp(listData).size()];
        int i=0;
        for(String s:obj.factorizeYearExp(listData)){
            str[i]=s;
            i+=1;
        }
        System.out.println(df.merge(StringVector.of("YearExpFactorize",str)));
        
        System.out.println("------------------------------------------------------------");
        
        System.out.println("Kmean Model");
        KmeansModel objKmean= new KmeansModel();
        System.out.println(objKmean.modelTrain(df));
        


        
    }
}
