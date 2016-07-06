/**

    StanfordBunnyView
    
    @update     2016/06/22
    @develop    K.Asai ,
    
*/

package stanfordbunny;

import javax.swing.JPanel;

public class StanfordBunnyView extends JPanel{
    // -------------------------------------------------
    // フィールド
    // -------------------------------------------------
    private StanfordBunnyModel      model;
    private StanfordBunnyController controller;
    
    // -------------------------------------------------
    // コンストラクタ
    // -------------------------------------------------
    public StanfordBunnyView(StanfordBunnyModel aModel){
        this.model = aModel;
    }
    
    // -------------------------------------------------
    // セッター
    // -------------------------------------------------
    public void setController(StanfordBunnyController aController){
        this.controller = aController;
    }
    
    
}