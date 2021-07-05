/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaApp.JavaApp;

import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Read;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.ListIterator;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;



import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.springframework.stereotype.Service;

/**
 *
 * @author original
 */
@Service
public class DAO {
    private LinkedHashMap<String, Long> reverseSortedCompanies= new LinkedHashMap<>();
    private LinkedHashMap<String, Long> reverseSortedTitles= new LinkedHashMap<>();
    private LinkedHashMap<String, Long> reverseSortedCountries= new LinkedHashMap<>();

    public DAO(){}

    public DataFrame readData_FromCSV(String fileName){
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        try {
            df = (DataFrame) Read.csv (fileName, format);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace ();
        }
        return df;
    }
    public List<POJO> getPOJOList(DataFrame df){
        assert df != null;
        List<POJO> data = new ArrayList<>();
        ListIterator<Tuple> iterator = df.stream().collect (Collectors.toList ()).listIterator ();
        while (iterator.hasNext ()) {
            Tuple t = iterator.next ();
            POJO p = new POJO();
            p.setTitle((String)t.get ("Title"));
            p.setCompany ((String) t.get ("Company"));
            p.setLocation((String)t.get ("Location"));
            p.setType ((String) t.get ("Type"));
            p.setLevel((String)t.get ("Level"));
            p.setYearsExp ((String) t.get ("YearsExp"));
            p.setCountry ((String) t.get ("Country"));
            p.setSkills ((String) t.get ("Skills"));
            data.add (p);
        }
        return data;
    }

    public DataFrame preprocessing(DataFrame df){
        df.stream().distinct();
        df = df.omitNullRows();

        return df;
    }


    public void pieChart(List<POJO> listData)
    {

        Map<String, Long> companies = listData.stream()
            .collect(Collectors.groupingBy(POJO::getCompany,Collectors.counting()));
        
        companies.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedCompanies.put(x.getKey(), x.getValue()));
        
        
        PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
        companies.keySet().forEach(key -> {
          chart.addSeries(key,companies.get(key));
        });
        new SwingWrapper(chart).displayChart();

    }
    
    public LinkedHashMap<String, Long> getCompaniesList(List<POJO> listData)
    {
        Map<String, Long> companies = listData.stream()
            .collect(Collectors.groupingBy(POJO::getCompany,Collectors.counting()));
        
        companies.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedCompanies.put(x.getKey(), x.getValue()));
        
        return reverseSortedCompanies;
    }
    
    
    
    public void barChart_Title(List<POJO> listData)
    {
        Map<String, Long> Titles = listData.stream()
            .collect(Collectors.groupingBy(POJO::getCompany,Collectors.counting()));
        
        Titles.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedTitles.put(x.getKey(), x.getValue()));
        
        CategoryChart chart = new CategoryChartBuilder().width(1000).height(900).title("Titles Histogram").xAxisTitle("Score").yAxisTitle("Number").build();        
        List<String> keys = new ArrayList();
        List<Long> values = new ArrayList();
        int count = 0;
        for (String key : reverseSortedTitles.keySet()){
            keys.add(key);
            values.add(reverseSortedTitles.get(key));
            count =count+1;
            if(count==10)
                break;
        }
    
        chart.addSeries("Test 1 ",keys,values);
        new SwingWrapper(chart).displayChart();

    }
    public LinkedHashMap<String, Long> getTitlesList(List<POJO> listData)
    {
        Map<String, Long> Titles = listData.stream()
            .collect(Collectors.groupingBy(POJO::getCompany,Collectors.counting()));
        
        Titles.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedTitles.put(x.getKey(), x.getValue()));
        return reverseSortedTitles;
    }
    
    
    public void barChart_Area(List<POJO> listData)
    {
        Map<String, Long> Titles = listData.stream()
            .collect(Collectors.groupingBy(POJO::getCountry,Collectors.counting()));
        
        Titles.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedCountries.put(x.getKey(), x.getValue()));
        
        CategoryChart chart = new CategoryChartBuilder().width(1000).height(900).title("Countries Histogram").xAxisTitle("Score").yAxisTitle("Number").build();        
        List<String> keys = new ArrayList();
        List<Long> values = new ArrayList();
        int count = 0;
        for (String key : reverseSortedCountries.keySet()){
            keys.add(key);
            values.add(reverseSortedCountries.get(key));
            count =count+1;
            if(count==10)
                break;
        }
        chart.addSeries("Test 1 ",keys,values);
        new SwingWrapper(chart).displayChart();

    }
    
     public LinkedHashMap<String, Long> getCountriesList(List<POJO> listData)
    {
         Map<String, Long> Titles = listData.stream()
            .collect(Collectors.groupingBy(POJO::getCountry,Collectors.counting()));
        
        Titles.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedCountries.put(x.getKey(), x.getValue()));
        
        return reverseSortedCountries;
    }
    
    public LinkedHashMap<String, List<String>> getSkillsForEachCompany(List<POJO> listData){
        LinkedHashMap<String, List<String>> skills= new LinkedHashMap<>();
        listData.forEach(obj -> {
            String[] s = obj.getSkills().split(", ");
            skills.put(obj.getTitle(),Arrays.asList( s ));
        });
        return skills;
     }
     
     
    public LinkedHashMap<String, Long> getAllSkill( LinkedHashMap<String, List<String>> skills){
        List<String> skillsList = new ArrayList<>();
        skills.keySet().forEach(key -> {
            skills.get(key).forEach(s -> {
                skillsList.add(s);
            });
        });
        LinkedHashMap<String, Long> reverseSortedSkills= new LinkedHashMap<>();
        Map<String, Long> counts =skillsList.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        
        counts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
        .forEachOrdered(x -> reverseSortedSkills.put(x.getKey(), x.getValue()));
        return reverseSortedSkills;
        
    }
    
    
    public List<String> factorizeYearExp(List<POJO> listData){
        List<String> yearExp = new ArrayList() ;
        for(POJO obj : listData){
            List<Integer> years = new ArrayList(); 
            if(obj.getYearsExp().contains("+")){
                String replace = obj.getYearsExp().replace("+", "");
                String s =  replace.split(" Yrs of Exp")[0];
                years.add(Integer.valueOf(s));  
            } 
            else if(obj.getYearsExp().contains("null")) {
               years.add(-1);  
            }
            else{
                String s =  obj.getYearsExp().split(" Yrs of Exp")[0];
                String[] ss = s.split("-");
                years.add(Integer.valueOf(ss[0]));
                years.add(Integer.valueOf(ss[1])); 

            }
            yearExp.add(String.valueOf(years));
        }
        return yearExp;
    }
    
}
