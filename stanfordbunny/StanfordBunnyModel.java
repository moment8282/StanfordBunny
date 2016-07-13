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

public class StanfordBunnyModel extends Object{
    
    private static final String FilePath = "resource/StanfordBunny.ply";     
    
    public StanfordBunnyModel(){
        //this.fileRead(FilePath);
    }
    
    
    private void fileRead(String filePath) {
        FileReader      fr = null;
        BufferedReader  br = null;
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
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