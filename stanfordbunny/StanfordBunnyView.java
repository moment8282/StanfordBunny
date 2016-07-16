package stanfordbunny;
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

public class StanfordBunnyView implements GLEventListener{
    private Animator animator;
    private final GLU glu;
    private float[] rotate = {0.0f,0.0f,0.0f,0.0f};
    private float scale = 0.0f;
    float[][] vertex = {
        {-0.25f,-0.25f,-0.25f},
        {0.25f,-0.25f,-0.25f},
        {0.25f,0.25f,-0.25f},
        {-0.25f,0.25f,-0.25f},
        {-0.25f,-0.25f,0.25f},
        {0.25f,-0.25f,0.25f},
        {0.25f,0.25f,0.25f},
        {-0.25f,0.25f,0.25f}
    };
    
    Integer[][] edge = {
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
    
    // -------------------------------------------------
    // フィールド
    // -------------------------------------------------
    private StanfordBunnyModel      model;
    private StanfordBunnyController controller;
    
    // -------------------------------------------------
    // コンストラクタ
    // -------------------------------------------------
    public StanfordBunnyView(StanfordBunnyModel aModel){
        this();
        this.model = aModel;
        
    }
    
    // -------------------------------------------------
    // セッター
    // -------------------------------------------------
    public void setController(StanfordBunnyController aController){
        this.controller = aController;
    }
    
    public StanfordBunnyView(){
        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        GLWindow glWindow = GLWindow.create(caps);
        glu = new GLU();
        glWindow.setTitle("First Demo(Newt)");
        glWindow.setSize(400,400);
        
        glWindow.addWindowListener(new WindowAdapter(){
            @Override
            public void windowDestroyed(WindowEvent evt){
                System.exit(0);
            }
        });
        
        
        
        
        
        
        glWindow.addGLEventListener(this);
        
        
        animator = new Animator();
        animator.add(glWindow);
        animator.start();
        animator.pause();
        glWindow.setVisible(true);
    }
    
    public static void main(String[] args){
        new StanfordBunnyView();
    }
    
    
    @Override
    public void init(GLAutoDrawable drawble){
        GL2 gl = drawble.getGL().getGL2();
        gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
        
        
        
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
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glBegin(GL2.GL_LINES);
        
        makeAxis(gl);
        gl.glColor3f(0.0f,0.0f,0.0f);
        makeSquare(gl);
        gl.glEnd();
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
        gl.glRotatef(rotate[0],rotate[1],rotate[2],rotate[3]);
        gl.glScalef(scale,scale,scale);
        for(Integer[] element:edge){
            makeLine(gl,vertex[element[0]],vertex[element[1]]);
        }
        gl.glRotatef(-1*rotate[0],rotate[1],rotate[2],rotate[3]);
        gl.glScalef(1/scale,1/scale,1/scale);
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
    
    
                              
    public void setRotate(float r,float x,float y,float z){
        //rotate = {r,x,y,z};  
    }
    
     
    
    
    public void setScalse(float s){
        scale = s;
    }
    
}
