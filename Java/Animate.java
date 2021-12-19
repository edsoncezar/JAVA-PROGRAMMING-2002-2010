/*********************************************************
 *  Animate.java                                         *
 *  ===================================================  *
 *                                                       *
 *  Assignment 3 for course in OO-Java, summer 2002      *
 *  Mike Anderson                                        *
 *                                                       *
 *                                                       *
 *********************************************************/

import java.applet.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;




/*********************************************************
 *  HELPER CLASSES                                       *
 *  ===================================================  *
 *                                                       *
 *                                                       *
 *  Randomizer                                           *
 *  ----------                                           *
 *  Encapsulates simplified random number generation     *
 *                                                       *
 *                                                       *
 *  ColorList                                            *
 *  ---------                                            *
 *  Encapsulates all colors defined in java.awt.color    *
 *  and provides array- and random accress to them       *
 *                                                       *
 *                                                       *
 *  CharList                                             *
 *  --------                                             *
 *  Provides array- and random access to all english     *
 *  upper-case letters                                   *
 *                                                       *
 *********************************************************/

class Randomizer
{
    public static int random( int ceiling ) { return (int)(Math.random()*ceiling); }
    public static int random( int floor, int ceiling ) { return random(ceiling-floor+1)+floor; }
}

class ColorList
{
    public static Color[] colors =
    {
        Color.black,
        Color.blue,
        Color.cyan,
        Color.darkGray,
        Color.gray,
        Color.green,
        Color.lightGray,
        Color.magenta,
        Color.orange,
        Color.pink,
        Color.red,
        Color.white,
        Color.yellow
    };

    public static Color getRandomColor()
    {
        return colors[ Randomizer.random( colors.length ) ];
    }
}

class CharList
{
    public static char[] chars =
    {
        'A','B','C','D','E','F','G','H','I','J','K','L','M',
        'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
    };

    public static char getRandomChar()
    {
        return chars[ Randomizer.random( chars.length ) ];
    }
}



/*********************************************************
 *  SPRITE CLASSES                                       *
 *  ===================================================  *
 *                                                       *
 *  ¤ SpriteBase - abstract base class                   *
 *  ¤ CircleSprite - a round sprite                      *
 *  ¤ LetterSprite - a letter                            *
 *  ¤ SquareSprite - an isometric quadragonal sprite     *
 *                                                       *
 *********************************************************/


/*********************************************************
 *  SPRITEBASE                                           *
 *  ----------                                           *
 *                                                       *
 *  Encapsulates the common data and methods of all      *
 *  sprites                                              *
 *                                                       *
 *********************************************************/

abstract class SpriteBase
{
    protected Point2D pos = new Point2D();
    protected Vector2D dir = new Vector2D();
    protected Color col = ColorList.getRandomColor();
    protected Rectangle bbox = new Rectangle();
    protected Rectangle bbox_javaRect = new Rectangle();
    protected Flags flags = new Flags();
    protected double size = 0.0;

    public class Flags
    {
        public boolean collided = false;
        public int countdown = 0;
        public double growth = 1.0;
        public boolean blink = false;
        public boolean visible = true;
        public boolean showBBox = false;
        public SpriteBase colidedWith = null;
    }



    abstract public void update( Rectangle i_bbox );
    abstract public boolean collidedWith( CircleSprite target );
    abstract public boolean collidedWith( SquareSprite target );
    abstract public boolean collidedWith( LetterSprite target );
    abstract public SpriteBase getNewSprite( SpriteBase other );


    public void setColor( Color color )            { col = color;                           }
    public void setDirection( Vector2D vec )       { dir = vec;                             }
    public void setDirection( double x, double y ) { dir.x = x; dir.y = y;                  }
    public void showBBox( boolean visible )        { flags.showBBox = visible;                    }
    public int getX() { return (int)pos.x; }
    public int getY() { return (int)pos.y; }
    protected boolean withinBBox( Point2D point ) { return (point.x >= bbox.x && point.y >= bbox.y) && (point.x <= bbox.height && point.y <= bbox.height); }

    public boolean collisionFinished()
    {
        return (flags.collided && flags.countdown <= 0);
    }

    public void setCollide( SpriteBase target )
    {
        if (flags.collided) return;

        flags.countdown = 15;
        flags.collided = true;
        flags.blink = true;
        flags.visible = (Randomizer.random(2) == 0);
        flags.growth = 1.06;
        flags.colidedWith = target;
        setDirection( 0.0, 0.0 );
    }


    protected String bboxToString()
    {
        return new String( "["+bbox.x+","+bbox.y+";"+bbox.width+","+bbox.height+"]");
    }


    public void update()
    {
        if (flags.blink) flags.visible = !flags.visible;
        if (flags.collided) --flags.countdown;
    }


    public void adjustForBounce( Rectangle i_bbox )
    {
        if (bbox.width >= i_bbox.width)   { dir.x *= -1.0;  pos.x -= bbox.width - i_bbox.width;   }
        if (bbox.x <= 0.0)                { dir.x *= -1.0;  pos.x -= bbox.x;                      }
        if (bbox.height >= i_bbox.height) { dir.y *= -1.0;  pos.y -= bbox.height - i_bbox.height; }
        if (bbox.y <= 0.0)                { dir.y *= -1.0;  pos.y -= bbox.y;                      }
    }


    abstract public void paint( Graphics g );
}


/*********************************************************
 *  CIRCLESPRITE                                         *
 *  ------------                                         *
 *                                                       *
 *  Most interesting about this sprite are the routines  *
 *  for collision detection. Aestetic algos.             *
 *                                                       *
 *********************************************************/

class CircleSprite extends SpriteBase
{
    protected double radius = 0;

    public CircleSprite( Point2D pos, int rad, Vector2D dir )
    {
        this.pos = new Point2D( pos );
        this.dir = new Vector2D( dir );
        radius = rad;
        size = radius;
    }

    public CircleSprite( SpriteBase one, SpriteBase two )
    {
        SpriteBase[] tempArr = { one, two };

        pos = tempArr[Randomizer.random(2)].pos;
        dir = new Vector2D( Math.random()*(Randomizer.random(5)+1), Math.random()*(Randomizer.random(5)+1) );
        col = tempArr[Randomizer.random(2)].col;
        radius = Randomizer.random(5, 15);
        size = radius;

        System.out.println("New circle made from concaternating two sprites. Size: " + size + ", pos: " + pos + ", dir: " + dir );
    }


    public String toString()
    {
        return new String( "Circle; pos " + pos + ", radius " + radius );
    }


    public SpriteBase getNewSprite( SpriteBase other )
    {
        return new CircleSprite( this, other );
    }


    public void update( Rectangle i_bbox )
    {
        super.update();

        pos.addVector( dir );

        radius *= flags.growth;

        bbox.x = (int)(pos.x - radius);
        bbox.y = (int)(pos.y - radius);
        bbox.width = (int)(pos.x + radius);
        bbox.height = (int)(pos.y + radius);

        bbox_javaRect.x = bbox.x;
        bbox_javaRect.y = bbox.y;
        bbox_javaRect.width = (int)radius*2;
        bbox_javaRect.height = (int)radius*2;

        adjustForBounce( i_bbox );
    }


    public void paint( Graphics g )
    {
        g.setColor( col );

        if (flags.visible)
            g.fillOval( (int)(pos.x-radius), (int)(pos.y-radius), (int)(radius*2.0), (int)(radius*2.0) );

        if (flags.showBBox)
            g.drawRect( (int)bbox.x, (int)bbox.y, (int)(radius*2.0), (int)(radius*2.0) ); // Plot a bounding box
    }



    // Find out the linear path between this center position
    // of this sprite and the target circle's. Normalize that
    // vector, and multiply it with the circle radius to
    // obtain the point closes to this sprite. If this
    // point is within this sprite's bounding box, then the
    // two sprites has collided.
    //
    private boolean collidedWithSquare( SpriteBase target )
    {
        Vector2D targetVector = pos.vectorTo( target.pos ).normalize();
        Point2D closestPoint = new Point2D( this.pos ).addVector( targetVector.mul(radius) );

        return target.bbox_javaRect.contains( closestPoint.x, closestPoint.y );
    }


    public boolean collidedWith( SquareSprite target ) { return collidedWithSquare( target ); }
    public boolean collidedWith( LetterSprite target ) { return collidedWithSquare( target ); }


    // Two circles are colliding if the length
    // of the vector between them is less than
    // or equal to eithers' radius.
    //
    public boolean collidedWith( CircleSprite sprite )
    {
        return pos.vectorTo( sprite.pos ).length() <= radius+sprite.radius;
    }
}



/*********************************************************
 *  LETTERSPRITE                                         *
 *  ------------                                         *
 *                                                       *
 *  A uppercase letter floating around. Otherwise        *
 *  principally the same as a SquareSprite.              *
 *                                                       *
 *********************************************************/

class LetterSprite extends SpriteBase
{
    protected char[] sign           = { CharList.getRandomChar() };
    protected FontRenderContext frc = null;
    protected Font font             = null;
    protected Dimension dim         = new Dimension();
    private FontData fontdata       = new FontData();


    // This class is only for tidying up the
    // code. Makes for more structured
    // reading.
    //
    private class FontData
    {
        public float leading           = 0;
        public float ascent            = 0;
        public float baseline          = 0;
        public float descent           = 0;
    }


    LetterSprite( Point2D pos, Vector2D dir )
    {
        this.pos = new Point2D( pos );
        this.dir = new Vector2D( dir );
        size = 12.0;
    }


    public LetterSprite( SpriteBase one, SpriteBase two )
    {
        SpriteBase[] tempArr = { one, two };

        size = 12.0;
        pos = tempArr[Randomizer.random(2)].pos;
        dir = new Vector2D( Math.random()*(Randomizer.random(5)+1), Math.random()*(Randomizer.random(5)+1) );
        col = tempArr[Randomizer.random(2)].col;

        System.out.println("New letter made from concaternating two sprites. Size: " + size + ", pos: " + pos + ", dir: " + dir );
    }


    public SpriteBase getNewSprite( SpriteBase other )
    {
        LetterSprite temp = new LetterSprite( this, other );
        temp.sign = sign;
        return temp;
    }


    public String toString()
    {
        return new String( "Letter; pos " + pos + ", area " + bboxToString() );
    }


    public void update( Rectangle i_bbox )
    {
        super.update();

        pos.addVector( dir );

        if (flags.growth != 1.0)
        {
            grow( flags.growth );
            frc = null;
        }

        bbox.x = (int)(pos.x - dim.width / 2);
        bbox.y = (int)(pos.y - dim.height / 2);
        bbox.width = (int)(pos.x + dim.width / 2);
        bbox.height = (int)(pos.y + dim.height / 2);

        bbox_javaRect.x = bbox.x;
        bbox_javaRect.y = bbox.y;
        bbox_javaRect.width = bbox.width - bbox.x;
        bbox_javaRect.height = bbox.height - bbox.y;

        adjustForBounce( i_bbox );
    }


    public void paint( Graphics g )
    {
        Graphics2D g2d = (Graphics2D)g;

        // Get object metrics. These cannot be
        // set in the constructor since they
        // are calculated from a graphics context.
        // Setting the frc to null also allows
        // for easy metrical update.
        //
        if (frc == null)
        {
            // Get font properties
            //
            frc = g2d.getFontRenderContext();


            // If a font for this context not
            // already if aquired, obtain it
            //
            if (font == null)
            {
                font = g2d.getFont();
                font.deriveFont( font.getStyle(), (int)size );
            }


            // Get and set dimensions and bounding box
            //
            LineMetrics lm    = font.getLineMetrics( sign, 0, 1, frc );

            fontdata.leading  = lm.getLeading();
            fontdata.ascent   = lm.getAscent();
            fontdata.descent  = lm.getDescent();
            fontdata.baseline = lm.getHeight() - fontdata.descent;

            bbox              = font.getStringBounds( sign, 0, 1, frc).getBounds();
            dim.width         = bbox.width;
            dim.height        = (int)(fontdata.ascent - fontdata.leading);
            bbox.x            = (int)(pos.x - dim.width / 2);
            bbox.y            = (int)(pos.y - dim.height / 2);
            bbox.width        = (int)(pos.x + dim.width / 2);
            bbox.height       = (int)(pos.y + dim.height / 2);
        }


        // Plot the entire enchilada
        // to the graphics context
        //
        if (flags.visible)
        {
            g2d.setFont( font );
            g.drawChars( sign, 0, 1, bbox.x, (int)((bbox.y+fontdata.ascent)-fontdata.leading) );
        }

        if (flags.showBBox)
            g.drawRect( (int)bbox.x, (int)bbox.y, dim.width, dim.height ); // Plot a bounding box
    }


    private void grow( double percent )
    {
        if (font == null) return;

        size *= percent;
        font = font.deriveFont( font.getStyle(), (int)size );
        frc = null;
    }


    // Vanlilla intersection tests. Nothing
    // interesting here...
    //
    public boolean collidedWith( LetterSprite sprite ) { return bbox_javaRect.intersects( sprite.bbox_javaRect ); }
    public boolean collidedWith( SquareSprite sprite ) { return bbox_javaRect.intersects( sprite.bbox_javaRect ); }
    public boolean collidedWith( CircleSprite target ) { return target.collidedWith( this ); }
}




/*********************************************************
 *  SQUARESPRITE                                         *
 *  ------------                                         *
 *                                                       *
 *  A square. Quite square, really. Plain vanilla        *
 *  collision detection, just what you expect.           *
 *                                                       *
 *********************************************************/

class SquareSprite extends SpriteBase
{
    public SquareSprite( Point2D pos, int size, Vector2D dir )
    {
        this.pos = new Point2D( pos );
        this.dir = new Vector2D( dir );
//        this.dim = new Dimension( size, size );
        this.size = size;

        bbox.x = (int)(pos.x - size/2);
        bbox.y = (int)(pos.y - size/2);
        bbox.width = (int)(pos.x + size/2);
        bbox.height = (int)(pos.y + size/2);
    }


    public SquareSprite( SpriteBase one, SpriteBase two )
    {
        SpriteBase[] tempArr = { one, two };

        pos = tempArr[Randomizer.random(2)].pos;
        dir = new Vector2D( Math.random()*(Randomizer.random(5)+1), Math.random()*(Randomizer.random(5)+1) );
        col = tempArr[Randomizer.random(2)].col;
        size = Randomizer.random(10, 30);
        bbox.x = (int)(pos.x - size/2);
        bbox.y = (int)(pos.y - size/2);
        bbox.width = (int)(pos.x + size/2);
        bbox.height = (int)(pos.y + size/2);

        System.out.println("New square made from concaternating two sprites. Size: " + size + ", pos: " + pos + ", dir: " + dir );
    }


    public SpriteBase getNewSprite( SpriteBase other )
    {
        return new SquareSprite( this, other );
    }


    public String toString()
    {
        return new String( "Square; pos " + pos + ", area " + bboxToString() );
    }


    public void update( Rectangle i_bbox )
    {
        super.update();

        pos.addVector( dir );

        size *= flags.growth;

        bbox.x = (int)(pos.x - size/2);
        bbox.y = (int)(pos.y - size/2);
        bbox.width = (int)(pos.x + size/2);
        bbox.height = (int)(pos.y + size/2);

        bbox_javaRect.x = bbox.x;
        bbox_javaRect.y = bbox.y;
        bbox_javaRect.width = bbox.width - bbox.x;
        bbox_javaRect.height = bbox.height - bbox.y;

        adjustForBounce( i_bbox );
    }


    public void paint( Graphics g )
    {
        g.setColor( col );

        if (flags.visible)
            g.fillRect( bbox.x, bbox.y, (int)size, (int)size );//dim.width, dim.height );

        if (flags.showBBox)
            g.drawRect( (int)bbox.x, (int)bbox.y, (int)size, (int)size );//dim.width, dim.height ); // Plot a bounding box
    }


    public boolean collidedWith( LetterSprite sprite ) { return bbox_javaRect.intersects( sprite.bbox_javaRect ); }
    public boolean collidedWith( SquareSprite sprite ) { return bbox_javaRect.intersects( sprite.bbox_javaRect ); }
    public boolean collidedWith( CircleSprite target ) { return target.collidedWith( this ); }
}



/*********************************************************
 *  PUBLIC CLASS ANIMATE                                 *
 *  ---------------------------------------------------  *
 *                                                       *
 *  Class is designed to work both as an applet and a    *
 *  stand-alone application.                             *
 *                                                       *
 *                                                       *
 *  The class' main gizmos are                           *
 *                                                       *
 *  1. Threadedness - the app is also runnable and       *
 *     sets up a thread to do the animation.             *
 *                                                       *
 *  2. The private inner class AppletFrame that          *
 *     creates a graphic context, if class is not run    *
 *     as an applet, and creates an applet object        *
 *     within that context.                              *
 *                                                       *
 *                                                       *
 *********************************************************/

public class Animate extends Applet implements Runnable
{
//  APPLET CODE
//  -----------------------------
//  Below is the functional core of the program,
//  which is contained within the main class scope.

    private LinkedList sprites = new LinkedList();
    private ArrayList collisions = new ArrayList();
    private Rectangle inner_bbox = null;


    public void init()
    {
        Dimension dim = getSize();
        inner_bbox = new Rectangle( 0, 0, dim.width, dim.height );

        addSprite();

        new Thread(this).run();
        start();
    }


    // The actual animation update
    // thread. An infinite loop that
    // updates each object.
    //
    public void run()
    {
        while (true)
        {
            if (Randomizer.random(20) == 0) addSprite();


            for (int i=0; i < sprites.size(); ++i )
            {
                SpriteBase current = (SpriteBase)sprites.get(i);

                current.update( inner_bbox );

                for (int j=i+1; j < sprites.size(); ++j )
                {
                    boolean c = false;
                    SpriteBase testAgainst = (SpriteBase)sprites.get(j);

                    if (testAgainst instanceof CircleSprite) c = current.collidedWith( (CircleSprite)testAgainst );
                        else if (testAgainst instanceof SquareSprite) c = current.collidedWith( (SquareSprite)testAgainst );
                            else if (testAgainst instanceof LetterSprite) c = current.collidedWith( (LetterSprite)testAgainst );

                    if (c)
                    {
                        /*if (!current.flags.collided)
                        {
                            System.out.println( "Collision between " + i +" and " + j + ". BBox overlap " + current.bbox_javaRect.intersects(testAgainst.bbox_javaRect));
                            System.out.println( "(" + current + "; " + testAgainst + ")" + "\n" );
                        }*/

                        current.setCollide( testAgainst );
                        testAgainst.setCollide( current );

                        collisions.add( current );
                        collisions.add( testAgainst );

                        sprites.remove( current );
                        sprites.remove( testAgainst );
                    }
                }
            }


            for (int k = 0; k < collisions.size(); ++k )
            {
                SpriteBase current = (SpriteBase)collisions.get(k);
                current.update( inner_bbox );


                if (current.collisionFinished())
                {
                    SpriteBase[] tempArr = { current, current.flags.colidedWith };
                    int thatOne = Randomizer.random(2);
                    int notThatOne = (thatOne-1) * (thatOne-1);

                    sprites.add( tempArr[thatOne].getNewSprite(tempArr[notThatOne]) );

                    collisions.remove( current.flags.colidedWith );
                    collisions.remove( current );

                    continue;
                }
            }


            repaint();

            try { Thread.sleep(50); }
            catch (InterruptedException e) {}
        }
    }



    // Just add a sprite of a random
    // type.
    //
    public void addSprite()
    {
        int speed = Randomizer.random( 10 );

        switch ( Randomizer.random(3) )
        {
            case 0:
                sprites.add( new CircleSprite( new Point2D(Randomizer.random(inner_bbox.width-25),Randomizer.random(inner_bbox.width+25)), Randomizer.random(5,15), new Vector2D(Math.random()*speed, Math.random()*speed) ) );
                break;

            case 1:
                sprites.add( new SquareSprite( new Point2D(Randomizer.random(inner_bbox.width+25), Randomizer.random(inner_bbox.width-25)), Randomizer.random(10,30), new Vector2D(Math.random()*speed, Math.random()*speed) ) );
                break;

            case 2:
                sprites.add( new LetterSprite( new Point2D(Randomizer.random(inner_bbox.width+25), Randomizer.random(inner_bbox.width-25)), new Vector2D(Math.random()*speed, Math.random()*speed) ) );
                break;
        }
    }



    // update()-method is overridden
    // to make away with the default clearing
    // of the graphics context.
    //
    public void update( Graphics g )
    {
        paint( g );
    }



    // The paint()- method is Heavily rearranged
    // to allow for smoother double-buffering.
    // Uses class-scoped variables
    // to decrease set-up overhead.
    //
    private Dimension dim = null;
    private Image offscreenImage = null;
    private Graphics offscreenGraphics = null;

    public void paint( Graphics g )
    {
        dim = getSize();

        // Create the offscreen elements, if
        // they do not already exist
        //
        if (offscreenImage == null)
        {
            offscreenImage = createImage( dim.width, dim.height );
            offscreenGraphics = offscreenImage.getGraphics();
        }


        // Clear offscreen elements
        //
        offscreenGraphics.setColor( getBackground() );
        offscreenGraphics.fillRect( 0, 0, dim.width, dim.height );


        // Plot all sprites to offscreen context
        //
        for (int i=0; i < sprites.size(); ++i )
        {
            SpriteBase current = null;

            try   { current = (SpriteBase)sprites.get(i); }
            catch ( IndexOutOfBoundsException e )
            {
                System.out.println("Bounds violation in Animate.sprites on item " + i);
                break;
            }

            current.paint( offscreenGraphics );
        }

        for (int i = 0; i < collisions.size(); ++i )
        {
            SpriteBase current = null;

            try   { current = (SpriteBase)collisions.get(i); }
            catch ( IndexOutOfBoundsException e )
            {
                System.out.println("Bounds violation in Animate.collisions on item " + i );
                break;
            }

            current.paint( offscreenGraphics );
        }


        // Flip the offscreen image over the
        // frame's graphic context
        //
        g.drawImage( offscreenImage, 0, 0, this );
    }





//  APPLICATION ADAPTATION
//---------------------------------
//  Below is the adaptations necessary to make program
//  act and run as a stand-alone application. This is
//  acheived by creaing a swing frame in which the
//  applet methods can perform. The frame is created in
//  the static main method, after which normal applet
//  opertaions take over.


    public static void main( String[] args )
    {
        new AppletFrame( new Animate(), "Animation", 500, 300 ).show();
    }

    static WindowAdapter WinAd = new WindowAdapter()
    {
        public void WindowClosing( WindowEvent we )
        {
            System.out.println( "Received WindowClosing event" );
            System.exit( 0 );
        }
    };

    private static class AppletFrame extends JFrame
    {
        public AppletFrame( Applet applet, String title, int width, int height )
        {

            setTitle( title );
            getContentPane().add( "Center", applet );
            pack();
            setSize( width+getInsets().right + getInsets().left, height+getInsets().top + height+getInsets().bottom );
            setVisible(true);
            show();

            addWindowListener( WinAd );

            applet.init();
            applet.start();
        }
    }
}