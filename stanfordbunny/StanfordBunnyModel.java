/**

    StanfordBunnyModel
    
    @update     2016/06/22
    @develop    K.Asai ,
    
*/

package stanfordbunny;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StanfordBunnyModel extends Object{
    
    private static final String FilePath = "resource/StanfordBunny.ply";     
    private PlyData[] points = new PlyData[35947];
    private int[][] face = new int[69451][4];
    private int prNum = 0;
    private int j = 0; 
    private int k = 0;
    
    public StanfordBunnyModel(){
        this.fileRead(FilePath);
    }
    
    public PlyData[] getPoints(){
        return points;
    }
    
    public int [][] getFace(){
        return face;
    }
    
    
    private void fileRead(String filePath) {
        FileReader      fr = null;
        BufferedReader  br = null;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] pts = line.split(" ",0);
                System.out.println(pts[0]);
                prNum++;
                if(pts[0].equals("end_header")){
                        break;
                }
                
            }
            
            while ((line = br.readLine()) != null) {
                String[] pts = line.split(" ",0);
                if(pts.length == 5){
                        points[j] = new PlyData(Double.parseDouble(pts[0]),Double.parseDouble(pts[1]),Double.parseDouble(pts[2]),Double.parseDouble(pts[3]),Double.parseDouble(pts[4]));
                    j++;
                }else if(pts.length == 4){
                    for(int i=0;i<pts.length;i++){
                        if(i == 0){
                            face[k][i] = Integer.parseInt(pts[i]); 
                        }else{
                            face[k][i] = Integer.parseInt(pts[i])-prNum;
                        }
                    }
                    k++;
                }
                
            }
            System.out.println("points size = " + points.length );
            System.out.println("face size = " + face.length );
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}