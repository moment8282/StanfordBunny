/**

    StanfordBunnyController
    
    @update     2016/06/22
    @develop    K.Asai ,
    
*/

package stanfordbunny;

public class StanfordBunnyController extends Object{
    // -------------------------------------------------
    // フィールド
    // -------------------------------------------------    
    private StanfordBunnyView   view;
    private StanfordBunnyModel  model;
    
    
    // -------------------------------------------------
    // セッター
    // -------------------------------------------------    
    public void setView(StanfordBunnyView    aView)  { this.view     = aView; }
    public void setModel(StanfordBunnyModel  aModel) { this.model    = aModel;}
    
    
}