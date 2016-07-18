package stanfordbunny;

/**

    StanfordBunnyController
    
    @update     2016/07/18
    @develop    K.Asai ,
    
*/


import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;


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
    
    
    
    
    // -------------------------------------------------
    // メソッド
    // -------------------------------------------------
    
    /**
        クリックイベントが話されたときイベントを受け取る
        @param e
        @return degree(角度情報)を返す
    */
    public void mouseReleased(MouseEvent e){
        this.model.resetPrev();
    }
    
    /**
        マウス操作による回転イベントを受け取る
        @param e
        @return degree(角度情報)を返す
    */
    public float[] mouseDraggedRotation(MouseEvent e){
        return this.model.rotation(e.getX(), e.getY());
    }
    
    
    /**
        キータイプによる回転イベントを受け取る
        @param key
        @return degree(角度情報)を返す
    */
    public float[] keyPressedRotation(KeyEvent key){
        switch(key.getKeyChar()){
            case KeyEvent.VK_ESCAPE:
                this.model.resetAll();
                break;   
                
            case 'x':
                return this.model.rotaX();
        
            case 'X':
                return this.model.derotaX();
                
            case 'y':
                return this.model.rotaY();
            
            case 'Y':
                return this.model.derotaY();
                
            case 'z':
                return this.model.rotaZ();
                
            case 'Z':
                return this.model.derotaZ();
                
            case 'r':
                this.model.resetAll();
                break;
                
            case 'R':
                this.model.resetAll();
                break;
                
            case 's':
                this.model.decreaseScale();
                break;
                
            case 'S':
                this.model.increaseScale();
                break;   
        }
        
        return this.model.getDegree();
    }
    
    
    
    /**
        アプリケーションを終了する
        @param code 終了コード
    */
    public void systemExit(Integer code){ System.exit(code); }
    
}