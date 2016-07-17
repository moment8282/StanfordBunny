package stanfordbunny;
/**

    StanfordBunny
    
    @update     2016/06/22
    @develop    K.Asai ,
    
*/


public class StanfordBunny extends Object{
    // -------------------------------------------------
    // フィールド
    // -------------------------------------------------    
    private StanfordBunnyModel      model;
    private StanfordBunnyView       view;
    private StanfordBunnyController controller;
    
    // -------------------------------------------------
    // メイン
    // -------------------------------------------------
    public static void main(String[] args){ new StanfordBunny(); }
    
    // -------------------------------------------------
    // コンストラクタ
    // -------------------------------------------------
    public StanfordBunny(){
        super();
        System.out.println("立ち上げ");
        
        // MVCの初期化と紐付け
        this.model      = new StanfordBunnyModel();
        this.view       = new StanfordBunnyView(this.model);
        this.controller = new StanfordBunnyController(); 
        this.view.setController(this.controller);
        this.controller.setModel(this.model);
        this.controller.setView(this.view);
        
    }
    
}