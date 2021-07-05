/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaApp.JavaApp;

import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smile.clustering.KMeans;
import smile.data.DataFrame;


/**
 *
 * @author original
 */
@RestController
@RequestMapping(path = "api/")
public class WebServices {
    DAO obj ;
    DataFrame df = null;
    List<POJO> listData = null;
    KmeansModel objkmean ;
   
    @Autowired
    public WebServices(){
        this.obj=new DAO();
        this.df = this.obj.readData_FromCSV("src/data/Wuzzuf_Jobs.csv");
        this.listData = this.obj.getPOJOList(df);
        this.df= this.obj.preprocessing(this.df);
        this.objkmean=new KmeansModel();

        
    }
   
    @GetMapping(path="summary")
    public DataFrame getSummary(){
        return this.df.summary();
    }
    
    @GetMapping(path="structure")
    public DataFrame getStructure(){
        return this.df.structure();
    }
    
    //pie chart for companies
    @GetMapping(path="CompaniesList")
    public LinkedHashMap<String, Long> getCompaniesList(){
        return this.obj.getCompaniesList(this.listData);
    }
    
    @GetMapping(path="TitlesList")
    public LinkedHashMap<String, Long> getTitlesList(){
        return this.obj.getTitlesList(this.listData);
    }
    
    @GetMapping(path="AreasList")
    public LinkedHashMap<String, Long> getAreasList(){
        return this.obj.getCountriesList(this.listData);
    }
    
    @GetMapping(path="skillsforeachcompany")
    public LinkedHashMap<String, List<String>> getSkillsForEachCompany(){
        return this.obj.getSkillsForEachCompany(listData);
    }
    
    @GetMapping(path="allskillsorders")
    public LinkedHashMap<String, Long> getAllSkill(){
        return this.obj.getAllSkill(obj.getSkillsForEachCompany(listData));
    }
    
    @GetMapping(path="yearsexpFactorize")
    public List<String> getDataFramewithYearExpfactorize(){
        return this.obj.factorizeYearExp(this.listData);
    }
    
    
    @GetMapping(path="kmeanmodel")
    public KMeans getKmeanModel()
    {
        
        return this.objkmean.modelTrain(this.df);
    }
    
    
   
    
    
    
    
    
    
        
        
    
}
