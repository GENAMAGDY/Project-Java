/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaApp.JavaApp;

import org.springframework.stereotype.Service;
import smile.clustering.KMeans;
import smile.clustering.PartitionClustering;
import smile.data.DataFrame;
import smile.data.measure.NominalScale;
import smile.data.vector.DoubleVector;


/**
 *
 * @author original
 */
@Service
public class KmeansModel {
    public KmeansModel(){
        
    }
    
    public KMeans modelTrain(DataFrame df){
        
        df = df.merge (DoubleVector.of ("TitleValues", encodeCategory (df, "Title")));
        df = df.merge (DoubleVector.of ("CompanyValues", encodeCategory (df, "Company")));
        
       
        
        double[][] data = new double[2][df.size()];
        for(int i =0 ;i<df.size();i++){
            data[0][i]= (double)df.column("TitleValues").get(i);
            data[1][i]= (double)df.column("CompanyValues").get(i);

        }
        return PartitionClustering.run(20, () -> KMeans.fit(data, 2));

    }
    public static double[] encodeCategory(DataFrame df, String columnName) {
        String[] values = df.stringVector (columnName).distinct ().toArray (new String[]{});
        double[] pclassValues = df.stringVector (columnName).factorize (new NominalScale (values)).toDoubleArray();
        return pclassValues;
    }

   
    
    
}
