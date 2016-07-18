package stanfordbunny;
/**

    StanfordBunnyView
    
    @update     2016/07/18
    @develop    K.Asai, Mitamura
    
*/
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GL2;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import java.lang.Math;
    
public class StanfordBunnyView extends Object implements GLEventListener{
    
    // -------------------------------------------------
    // フィールド
    // -------------------------------------------------
    private StanfordBunnyModel      model;
    private StanfordBunnyController controller;
    
    private Animator animator;
    private GLWindow glWindow;
    private final GLU glu;
    
    // default rotate position
    private float[] rotate = {0.0f,0.0f,0.0f,0.0f};
    
    private float scale = 0.0f;
    // 頂点
    private float[][] vertex = {
        {-0.25f,-0.25f,-0.25f},
        {0.25f,-0.25f,-0.25f},
        {0.25f,0.25f,-0.25f},
        {-0.25f,0.25f,-0.25f},
        {-0.25f,-0.25f,0.25f},
        {0.25f,-0.25f,0.25f},
        {0.25f,0.25f,0.25f},
        {-0.25f,0.25f,0.25f}
    };
    
    // 辺
    private Integer[][] edge = {
        {0,1},
        {1,2},
        {2,3},
        {3,0},
        {4,5},
        {5,6},
        {6,7},
        {7,4},
        {0,4},
        {1,5},
        {2,6},
        {3,7}
    };
    
    // 面
    private final Integer face[][] = {
        {0,1,2,3},
        {1,5,6,2},
        {5,4,7,6},
        {4,0,3,7},
        {4,5,1,0},
        {3,2,6,7}
    };
    
    // 色
    private final float color[][] = {
        {1.0f,0.0f,0.0f},
        {0.0f,1.0f,0.0f},
        {0.0f,0.0f,1.0f},
        {1.0f,1.0f,0.0f},
        {1.0f,0.0f,1.0f},
        {0.0f,1.0f,1.0f}
    };
    
    // 角度
    private float[] degree = {0.0f,0.0f,0.0f};
    

    
    // -------------------------------------------------
    // コンストラクタ
    // -------------------------------------------------
    public StanfordBunnyView(StanfordBunnyModel aModel){
        this();
        this.model = aModel;
    }
    
    // 指定コンストラクタ
    public StanfordBunnyView(){
        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        this.glWindow = GLWindow.create(caps);
        glu = new GLU();
        this.glWindow.setTitle("First Demo(Newt)");
        this.glWindow.setSize(400,400);
        
        this.glWindow.addGLEventListener(this);
        
        
        animator = new Animator();
        animator.add(glWindow);
        animator.start();
        this.glWindow.setVisible(true);
    }
    
    // -------------------------------------------------
    // セッター
    // -------------------------------------------------
    public void setController(StanfordBunnyController aController){
        this.controller = aController;
        
        // ここで、リスナーのセッティングを行う。
        this.glWindow.addWindowListener(new WindowAdapter(){
            @Override
            public void windowDestroyed(WindowEvent evt){
                controller.systemExit(0);
            }
        });
        
        this.glWindow.addMouseListener(new MouseAdapter(){
            @Override 
            public void mouseReleased(MouseEvent e){
                controller.mouseReleased(e);
            }
            
            @Override
            public void mouseDragged(MouseEvent e){                
                degree = controller.mouseDraggedRotation(e);
                startAnimator();
            }
        });
        
        this.glWindow.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent key){
                degree = controller.keyPressedRotation(key);
            }
        });
    }
    
    
    @Override
    public void init(GLAutoDrawable drawble){
        GL2 gl = drawble.getGL().getGL2();
        gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }
    
    
    @Override
    public void reshape(GLAutoDrawable drawable,int x,int y,int width,int height){
        GL2 gl = drawable.getGL().getGL2();

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        
        glu.gluPerspective(20.0,(double)width/(double)height,1.0,300.0);
        glu.gluLookAt(-1.0f,1.0f,3.0f,0.0f,0.0f,0.0f,0.0f,1.0f,0.0f);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
    
    
    @Override
    public void display(GLAutoDrawable drawble){
        GL2 gl = drawble.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glPushMatrix();
        gl.glBegin(GL2.GL_LINES);
        makeAxis(gl);
        gl.glEnd();
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        //rotaX();
        
        moveRotate(gl);
        gl.glBegin(GL2.GL_QUADS);
        makeSquareFill(gl);
        gl.glEnd();
        gl.glPopMatrix();
        
    }
    
    
    @Override
    public void dispose(GLAutoDrawable drawable){
        if(animator != null) animator.stop();
    }
    
    
    private void makeAxis(GL2 gl){
        
        float[] xLineStart = {-1.0f,0.0f,0.0f};
        float[] xLineEnd = {1.0f,0.0f,0.0f};
        float[] xLineColor = {1.0f,0.0f,0.0f};
        makeLine(gl,xLineStart,xLineEnd,xLineColor);
        
        float[] yLineStart = {0.0f,-1.0f,0.0f};
        float[] yLineEnd = {0.0f,1.0f,0.0f};
        float[] yLineColor = {0.0f,1.0f,0.0f};
        makeLine(gl,yLineStart,yLineEnd,yLineColor);
        
        float[] zLineStart = {0.0f,0.0f,-1.0f};
        float[] zLineEnd = {0.0f,0.0f,1.0f};
        float[] zLineColor = {0.0f,0.0f,2.0f};
        makeLine(gl,zLineStart,zLineEnd,zLineColor);
        
    }
    
    private void makeSquare(GL2 gl){
        int i = 0;
        gl.glRotatef(rotate[0],rotate[1],rotate[2],rotate[3]);
        gl.glScalef(scale,scale,scale);
        for(Integer[] element:edge){
            gl.glColor3fv(color[i],0);
            makeLine(gl,vertex[element[0]],vertex[element[1]]);
            i++;
        }
        gl.glRotatef(-1*rotate[0],rotate[1],rotate[2],rotate[3]);
        gl.glScalef(1/scale,1/scale,1/scale);
    }
    
    private void makeSquareFill(GL2 gl){
        Integer i =0;
        gl.glColor3f(0.0f,0.0f,0.0f);
        for (Integer[] elements:face){
            gl.glColor3fv(color[i],0);
            i++;
            for(Integer element:elements){
                gl.glVertex3fv(vertex[element],0);
            }
        }
    }
    
    private void moveRotate(GL2 gl){
        gl.glRotatef(this.degree[0],1,0,0);
        gl.glRotatef(this.degree[1],0,1,0);
        gl.glRotatef(this.degree[2],0,0,1);
    }
    
    protected void makeLine(GL2 gl,float[] start,float[] end,float[] color){
        gl.glColor3f(color[0],color[1],color[2]);
        makeLine(gl,start,end);
    }
    
    
    protected void makeLine(GL2 gl,float[] start,float[] end){
        gl.glVertex3fv(start,0);
        gl.glVertex3fv(end,0);
    }
    
    
    public void resumeAnimator(){
        animator.resume();
    }
    
    
    public void startAnimator(){
        animator.start();
    }
    
    public void pauseAnimator(){
        animator.pause();
    }
    

    
    public void setScalse(float s){
        scale = s;
    }
    
}
