package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author cristopher
 */
public class SnakeBody {
    private final boolean paintFace;
    private Color eyesColor = Color.BLACK;
    
    public static int gridLength = 0;
    public static float xGridLimit = 0, yGridLimit = 0;
    
    private double xGrid, yGrid;
    private Color color;
    
    private int speed = 10;
    private int axisDirection = 1;
    private boolean moveInXAxis = false;
    
    private SnakeDirection direction = SnakeDirection.SOUTH;
    private int maxDirectionPoint = 0;

    public SnakeBody(boolean paintFace, double xGrid, double yGrid) {
        this.paintFace = paintFace;
        this.xGrid = xGrid;
        this.yGrid = yGrid;
    }
    
    public void paintPart(Graphics2D g2D) {
        g2D.setColor(color);
        g2D.fill(new Rectangle2D.Double(xGrid * gridLength, yGrid * gridLength, gridLength, gridLength));
        
        if (paintFace) {
            g2D.setColor(eyesColor);
            g2D.fill(new Rectangle2D.Double(xGrid * gridLength + 3, yGrid * gridLength + 3, 3, 8));
            g2D.fill(new Rectangle2D.Double(xGrid * gridLength + 9, yGrid * gridLength + 3, 3, 8));

//            g2D.setColor(Color.RED);
//            g2D.fill(new Rectangle2D.Float(xGrid * gridLength + 9, yGrid * gridLength + 13, 8, 3));
        }
    }

    public void setColor(Color color) {
        this.color = color;
        if (paintFace) {
            double tmp = color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114;
//            System.out.println("tmp = " + tmp);
//            if (tmp > 186)
            if (tmp > 120)
                eyesColor = Color.BLACK;
            else
                eyesColor = Color.WHITE;
        }
    }

    public void setXGrid(double xGrid) {
        this.xGrid = xGrid;
        
//        System.out.println("xGrid: " + xGrid + ", lim: " + xGridLimit);
        
        if (this.xGrid > xGridLimit)
            this.xGrid = 0;
        if (this.xGrid < 0)
            this.xGrid = xGridLimit;
    }

    public void setYGrid(double yGrid) {
        this.yGrid = yGrid;
        
        if (this.yGrid > yGridLimit)
            this.yGrid = 0;
        if (this.yGrid < 0)
            this.yGrid = yGridLimit;
    }
    
    public void setLocation(Point2D p) {
        setXGrid(p.getX());
        setYGrid(p.getY());
    }
    
    public Point2D getAbsoluteLocation() {
        return new Point2D.Double(xGrid * gridLength + gridLength / 2, yGrid * gridLength + gridLength / 2);
    }
    
    public Point2D getGridLocation(boolean immediatePrevious) {
        if (immediatePrevious) {
            switch (direction) {
                case NORTH:
                return new Point2D.Double(xGrid, yGrid + 1);
                case SOUTH:
                return new Point2D.Double(xGrid, yGrid - 1);
                case WEST:
                return new Point2D.Double(xGrid + 1, yGrid);
                case EAST:
                return new Point2D.Double(xGrid - 1, yGrid);
            }
        }
        return new Point2D.Double(xGrid, yGrid);
    }

    public void increaseMaxDirectionPoint() {
        this.maxDirectionPoint++;
    }
    
    public int getMaxDirectionPoint() {
        return maxDirectionPoint;
    }
    
    public void movePart() {
        if (moveInXAxis)
            setXGrid(xGrid + (speed * axisDirection / 100f));
        else
            setYGrid(yGrid + (speed * axisDirection / 100f));
    }

    private void makeSpeedPositive(boolean b) {
        axisDirection = b ? 1 : -1;
    }
    
    public void setDirection(SnakeDirection direction) {
        if (null != direction) 
            switch (direction) {
                case NORTH:
                    if (this.direction == SnakeDirection.SOUTH)
                        return;
                    this.moveInXAxis = false;
                    makeSpeedPositive(false);
                break;

                case SOUTH:
                    if (this.direction == SnakeDirection.NORTH)
                        return;
                    this.moveInXAxis = false;
                    makeSpeedPositive(true);
                break;

                case WEST:
                    if (this.direction == SnakeDirection.EAST)
                        return;
                    this.moveInXAxis = true;
                    makeSpeedPositive(false);
                break;

                case EAST:
                    if (this.direction == SnakeDirection.WEST)
                        return;
                    this.moveInXAxis = true;
                    makeSpeedPositive(true);
                break;
            }
        
        this.direction = direction;
//        if (this.moveInXAxis)
//            yGrid = Math.round(yGrid);
//        else 
//            xGrid = Math.round(xGrid);
    }
    
    public void modifySpeed(boolean increase) {
        if (increase && speed < 100)
            speed += 10;
        if (!increase && Math.abs(speed) > 10)
            speed -= 10;
        
//        System.out.println("speed = " + speed);
    }

    public void setSpeed(int speed) {
        if (speed > 100)
            speed = 100;
        if (speed < 10)
            speed = 10;
        
        this.speed = speed;
    }
    
    public int getSpeed() {
        return speed;
    }
}
