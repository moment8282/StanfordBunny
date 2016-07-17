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
    private ArrayList<PlyVertexData> plyVertexData = new ArrayList<PlyVertexData>();
    private ArrayList<PlyFaceData> plyFaceData = new ArrayList<PlyFaceData>();
    private int prNum = 0;
    
    public StanfordBunnyModel(){
        this.fileRead(FilePath);
    }
    
    public ArrayList<PlyVertexData> getPlyVertexData(){
        return plyVertexData;
    }
    
    public ArrayList<PlyFaceData> getPlyFaceData(){
        return plyFaceData;
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
                //System.out.println(pts[0]);
                prNum++;
                if(pts[0].equals("end_header")){
                        break;
                }
                
            }
            
            while ((line = br.readLine()) != null) {
                String[] pts = line.split(" ",0);
                if(pts.length == 5){
                        PlyVertexData data = new PlyVertexData(Double.parseDouble(pts[0]),Double.parseDouble(pts[1]),Double.parseDouble(pts[2]),Double.parseDouble(pts[3]),Double.parseDouble(pts[4]));
                    plyVertexData.add(data);
                }else if(pts.length == 4){
                    Integer[] vi = new Integer[3];
                    vi[0]=Integer.parseInt(pts[1])-prNum;
                    vi[1]=Integer.parseInt(pts[2])-prNum;
                    vi[2]=Integer.parseInt(pts[3])-prNum;
                    PlyFaceData fData = new PlyFaceData(Integer.parseInt(pts[0]),vi);
                    plyFaceData.add(fData); 
                }
                
            }
            //System.out.println("vertex size = " + plyVertexData.size() );
            //System.out.println("face size = " + plyFaceData.size() );
            
            
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