package stanfordbunny;

/**

    StanfordBunnyModel
    
    @update     2016/07/18
    @develop    K.Asai , Ehara
    
*/

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class StanfordBunnyModel extends Object{
    
    private static final String FilePath = "resource/StanfordBunny.ply";     
    private ArrayList<PlyVertexData> plyVertexData = new ArrayList<PlyVertexData>();
    private ArrayList<PlyFaceData> plyFaceData = new ArrayList<PlyFaceData>();

    // 角度情報
    private float[] degree  = {0.0f,0.0f,0.0f};
    // 角度フラグ
    private Integer prevX   = -1;
    private Integer prevY   = -1;
    
    //スケール
    private float scale = 1.0f;
    
    private int prNum = 0;
    private ArrayList<Integer> elementNumber = new ArrayList<Integer>();
    
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
                }else if(pts[0].equals("element")){
                    elementNumber.add( Integer.parseInt(pts[2]) );
                }
                
            }
            
            while ((line = br.readLine()) != null) {
                String[] pts = line.split(" ",0);
                if(pts.length == 5){
                    PlyVertexData data = new PlyVertexData(
                        Double.parseDouble(pts[0]),
                        Double.parseDouble(pts[1]),
                        Double.parseDouble(pts[2]),
                        Double.parseDouble(pts[3]),
                        Double.parseDouble(pts[4])
                    );
                    plyVertexData.add(data);
                }else if(pts.length == 4){
                    Integer[] vi = new Integer[3];
                    vi[0]=Integer.parseInt(pts[1])/*-prNum*/;
                    vi[1]=Integer.parseInt(pts[2])/*-prNum*/;
                    vi[2]=Integer.parseInt(pts[3])/*-prNum*/;
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
    
    
    
    // ゲッター
    public float getScale(){ return this.scale; }
    public float[] getDegree(){ return this.degree; }

    // セッター
    public void setScale(float s){ this.scale = s; }
    public void setDegree(float x,float y,float z){
        this.degree[0] = x;
        this.degree[1] = y;
        this.degree[2] = z;
    }
    
    /**
        scale及び、degreeを初期化する
    */
    public void resetAll(){
        this.resetDegree();
        this.resetScale();
    }
    
    /**
        degreeの値を加算する
    */
    public void increaseDegree(float x,float y,float z){
        this.degree[0] += x;
        this.degree[1] += y;
        this.degree[2] += z;
    }

    /**
        degreeの値を減算する
    */
    public void decreaseDegree(float x,float y,float z){
        this.degree[0] -= x;
        this.degree[1] -= y;
        this.degree[2] -= z;
    }
    
    /**
        degreeの値を初期化する
    */
    public void resetDegree(){
        this.setDegree(0.0f,0.0f,0.0f);
    }
    
    /**
        scaleの値を0.1加算する
    */
    public void increaseScale(){
        scale += 0.1;
    }
    
    /**
        scaleの値を0.1減算する
    */
    public void decreaseScale(){
        scale -= 0.1;
    }
    
    /**
        scaleの値を初期化する
    */
    public void resetScale(){
        this.setScale(1.0f);
    }
    
    /**
        角度情報のフラグをリセットする(詳細未確認)
    */
    public void resetPrev(){
        this.prevX = -1;
        this.prevY = -1;
    }
    
    /**
        角度情報を更新する
        @param x
        @param y
        @return degree(角度情報)を返す
    */
    public float[] rotation(Integer x, Integer y){
                
        if(!prevX.equals(-1) || !prevY.equals(-1)){
            Integer distanceX = (x - this.prevX) / 2;
            Integer distanceY = (y - this.prevY) / 2;
            
            this.increaseDegree(distanceY.floatValue(),distanceX.floatValue(),0);
        }       
        
        this.prevX = x;
        this.prevY = y;
        return this.getDegree();
    }
    

    /**
        角度情報をXの正の方向に更新する
        @return degree(角度情報)を返す
    */
    public float[] rotaX(){
        this.increaseDegree(1,0,0);
        return this.getDegree();
    }
    
    /**
        角度情報をXの負の方向に更新する
        @return degree(角度情報)を返す
    */
    public float[] derotaX(){
        this.decreaseDegree(1,0,0);
        return this.getDegree();
    }
    
    
    /**
        角度情報をYの正の方向に更新する
        @return degree(角度情報)を返す
    */
    public float[] rotaY(){
        this.increaseDegree(0,1,0);
        return this.getDegree();
    }
    
    /**
        角度情報をYの負の方向に更新する
        @return degree(角度情報)を返す
    */
    public float[] derotaY(){
        this.decreaseDegree(0,1,0);
        return this.getDegree();
    }
    
    /**
        角度情報をZの正の方向に更新する
        @return degree(角度情報)を返す
    */
    public float[] rotaZ(){
        this.increaseDegree(0,0,1);
        return this.getDegree();
    }
    
    /**
        角度情報をZの負の方向に更新する
        @return degree(角度情報)を返す
    */
    public float[] derotaZ(){
        this.decreaseDegree(0,0,1);
        return this.getDegree();
    }
    
    
}