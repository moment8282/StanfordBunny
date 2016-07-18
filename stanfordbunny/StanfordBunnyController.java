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
    public void mouseDraggedRotation(MouseEvent e){
        this.model.rotation(e.getX(), e.getY());
        this.view.setDegree(this.model.getDegree() );
    }
    
    
    /**
        キータイプによる回転イベントを受け取る
        @param key
        @return degree(角度情報)を返す
    */
    public void keyPressed(KeyEvent key){
        switch(key.getKeyChar()){
            case KeyEvent.VK_ESCAPE:
                this.resetAllParameter();
                break;   
                
            case 'x':
                this.model.rotaX();
                this.view.setDegree( this.model.getDegree() );
                break;
                
            case 'X':
                this.model.derotaX();
                this.view.setDegree( this.model.getDegree() );
                break;
                
            case 'y':
                this.model.rotaY();
                this.view.setDegree( this.model.getDegree() );
                break;
                
            case 'Y':
                this.model.derotaY();
                this.view.setDegree( this.model.getDegree() );
                break;
                
            case 'z':
                this.model.rotaZ();
                this.view.setDegree( this.model.getDegree() );
                break;
                
            case 'Z':
                this.model.derotaZ();
                this.view.setDegree( this.model.getDegree() );
                break;
                
            case 'r':
                this.resetAllParameter();
                break;
                
            case 'R':
                this.resetAllParameter();
                break;
                
            case 's':
                this.model.decreaseScale();
                this.view.setScale( this.model.getScale() );
                break;
                
            case 'S':
                this.model.increaseScale();
                this.view.setScale( this.model.getScale() );
                break;   
        }
        
    }
    
    
    
    /**
        アプリケーションを終了する
        @param code 終了コード
    */
    public void systemExit(Integer code){ System.exit(code); }
    
    
    /*
        degree及びscaleをリセットする
    */
    private void resetAllParameter(){
        this.model.resetAll();
        this.view.setDegree( this.model.getDegree() );
        this.view.setScale( this.model.getScale() );
    }
    
}