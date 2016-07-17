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
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import java.lang.Math;
    
public class StanfordBunnyView implements GLEventListener{
    private Animator animator;
    private final GLU glu;
    private float[] rotate = {0.0f,0.0f,0.0f,0.0f};
    private float scale = 0.0f;
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
    
    private final Integer face[][] = {
        {0,1,2,3},
        {1,5,6,2},
        {5,4,7,6},
        {4,0,3,7},
        {4,5,1,0},
        {3,2,6,7}
    };
    
    private final float color[][] = {
        {1.0f,0.0f,0.0f},
        {0.0f,1.0f,0.0f},
        {0.0f,0.0f,1.0f},
        {1.0f,1.0f,0.0f},
        {1.0f,0.0f,1.0f},
        {0.0f,1.0f,1.0f}
    };
    
    private float[] degree = {0.0f,0.0f,0.0f};
    
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
        
        glWindow.addMouseListener(new MouseAdapter(){
            private Integer prevX=-1;
            private Integer prevY=-1;
            @Override 
            public void mouseReleased(MouseEvent e){
                prevX=-1;
                prevY=-1;
            }
            
            @Override
            public void mouseDragged(MouseEvent evt){
                
                Integer x = evt.getX();
                Integer y = evt.getY();
                
                if(!prevX.equals(-1) || !prevY.equals(-1)){
                    Integer distanceX = (x-prevX)/2;
                    Integer distanceY = (y-prevY)/2;
                    
                    setDegree(distanceY.floatValue(),distanceX.floatValue(),0);
                }
                    
                
                prevX = x;
                prevY = y;
                startAnimator();
            }
        });
        
        glWindow.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent key){
                switch(key.getKeyChar()){
                    case KeyEvent.VK_ESCAPE:
                        break;
                        
                    case 'x':
                        rotaX();
                        break;
                    
                    case 'y':
                        rotaY();
                        break;
                        
                    case 'z':
                        rotaZ();
                        break;
                }
            }
        });
        
        
        glWindow.addGLEventListener(this);
        
        
        animator = new Animator();
        animator.add(glWindow);
        animator.start();
        glWindow.setVisible(true);
    }
    
    public static void main(String[] args){
        new StanfordBunnyView();
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
        gl.glRotatef(degree[0],1,0,0);
        gl.glRotatef(degree[1],0,1,0);
        gl.glRotatef(degree[2],0,0,1);
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
    
    public void setDegree(float x,float y,float z){
        degree[0] += x;
        degree[1] += y;
        degree[2] += z;
    }
    
    public void rotaX(){
        setDegree(1,0,0);
    }
    
    public void rotaY(){
        setDegree(0,1,0);
    }
    
    public void rotaZ(){
        setDegree(0,0,1);
    }
    
    public void setScalse(float s){
        scale = s;
    }
    
}
