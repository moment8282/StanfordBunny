package stanfordbunny;
/**

    PlyFaceData
    
    @update     2016/07/18
    @develop    K.Asai
    
*/


public class PlyFaceData extends Object{

    private Integer     _list;
    private Integer[]   _vertexIndices;
    
    public PlyFaceData(Integer list, Integer[] vertexIndices){
        this._list          = list;
        this._vertexIndices = vertexIndices;
    }
    
    public Integer getList(){ return this._list; }
    public Integer[] getVertexIndices(){ return this._vertexIndices; }
    
}