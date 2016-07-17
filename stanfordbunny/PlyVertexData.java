package stanfordbunny;

public class PlyVertexData extends Object{

    private Double _x;
    private Double _y;
    private Double _z;
    private Double _confidence;
    private Double _intencity;
    
    public PlyVertexData(Double x,Double y,Double z,Double confidence,Double intencity){
        this._x = x;
        this._y = y;
        this._z = z;
        this._confidence = confidence;
        this._intencity = intencity;
    }
    
    
    public Double getX(){
        return this._x;    
    }
    
    public Double getY(){
        return this._y;    
    }
    
    public Double getZ(){
        return this._z;    
    }
    
    public Double getConfidence(){
        return this._confidence;    
    }
    
    public Double getIntencity(){
        return this._intencity;    
    }
    
}