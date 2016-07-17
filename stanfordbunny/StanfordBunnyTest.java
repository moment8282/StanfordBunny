package stanfordbunny;

/**

    StanfordBunnyTest
    
    @update     2016/07/07
    @develop    K.Asai ,
    
*/

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities; 
import com.jogamp.opengl.GLEventListener; 
import com.jogamp.opengl.GLProfile;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent; 
import com.jogamp.newt.opengl.GLWindow; 
import com.jogamp.opengl.util.Animator;

public class StanfordBunnyTest implements GLEventListener{
 
    private Animator animator;
    
    public static void main(String[] args){
        new StanfordBunnyTest();
    }
    
    public StanfordBunnyTest(){
        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));//(2) 
        GLWindow glWindow = GLWindow.create(caps); //(3)
        glWindow.setTitle("First demo (Newt)"); //(4)
        glWindow.setSize(300, 300); //(5)
        glWindow.addWindowListener(new WindowAdapter() { //(6) 
            @Override
            public void windowDestroyed(WindowEvent evt) {
                System.exit(0);
            }
        });
        
        glWindow.addGLEventListener(this); //(7)
        this.animator = new Animator(); //(8) 
        this.animator.add(glWindow);
        this.animator.start(); 
        glWindow.setVisible(true); //(10)
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {}
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
    
    @Override
    public void display(GLAutoDrawable drawable) {}
    
    @Override
    public void dispose(GLAutoDrawable drawable) {
        if(this.animator != null) this.animator.stop();
    }
    
}