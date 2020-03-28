package lab6;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BouncingBall implements Runnable {

                                   // ������������ ������, ������� ����� ����� ���
    private static final int MAX_RADIUS = 20;
                                    // ����������� ������, ������� ����� ����� ���
    private static final int MIN_RADIUS = 15;
                                    // ������������ ��������, � ������� ����� ������ ���
    private static final int MAX_SPEED = 30;

    private Field field;
    private int radius;
    private Color color;
                                    // ���� ����� ��� ���
    private boolean superflag=false;

                                    // ������� ���������� ����
    private double x;
    private double y;

                                    // ������������ � �������������� ���������� ��������
    private int speed;
    private double speedX;
    private double speedY;



                                    // ����������� ������ BouncingBall
    public BouncingBall(Field field) {
                                        // ���������� ����� ������ �� ����, �� �������� ������� ���,
                                        // ����� ����������� ����� �� ��� �������

                                        // ����� getWidth(), getHeight()
        this.field = field;
                                        // ������ ���� ���������� �������
        radius = new Double(Math.random()*(MAX_RADIUS - MIN_RADIUS)).intValue() + MIN_RADIUS;
                                        // ���������� �������� �������� ������� �� �������� ����,
                                        // ��� �� ������, ��� ���������

        speed = new Double(Math.round(5*MAX_SPEED / radius)).intValue();


        if (speed>MAX_SPEED) {
            speed = MAX_SPEED;
        }
                                        // ��������� ����������� �������� ���� ��������,
                                        // ���� � �������� �� 0 �� 2PI
        double angle = Math.random()*2*Math.PI;
                                        // ����������� �������������� � ������������ ���������� ��������
        speedX = 3*Math.cos(angle);
        speedY = 3*Math.sin(angle);

                                        // ���� ���� ���������� ��������
        color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
                                        // ��������� ��������� ���� ��������
        x = Math.random()*(field.getSize().getWidth()-2*radius) + radius;
        y = Math.random()*(field.getSize().getHeight()-2*radius) + radius;
                                        // ������� ����� ��������� ������, ��������� ����������
                                        // ������ �� �����, ����������� Runnable (�.�. �� ����)
        Thread thisThread = new Thread(this);
                                        // ��������� �����
        thisThread.start();
    }

                                    // ������� get � set
    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public boolean isSuperflag() { return superflag; }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getX() {
        return x;
    }

    public double getY() { return y; }

    public int getRadius(){ return radius; }


                                    // ������ ����� ������������
    public BouncingBall superball()
    {
        superflag=true;
        radius*=2;
        return this;
    }

                                    // ����� run() ����������� ������ ������. ����� �� ��������� ������,
                                    // �� ����������� � �����
    public void run() {
        try {
                                            // ������ ����������� ����, �.�. ���� ��� �� �������,
                                            // �� �� �������� �����������
            while(true) {
                                                // ������������� ������� �� ����� ������� ����
                                                // ���� �������� ��������� - ���������� �����
                                                // ���������� � �����
                                                // � ��������� ������ - �������� ����� ������
                field.canMove(this);

                                                // �������� �� ������������ � �����������
                
               

                                                    // �������� �� ������������ �� ��������
                if (x + speedX <= radius) {
                                                    // �������� ����� ������, ����������� �����
                    speedX = -speedX;
                    x = radius;
                }
                else if (x + speedX >= field.getWidth() - radius) {
                                                    // �������� ������ ������, ������ �����
                    speedX = -speedX;
                    x=new Double(field.getWidth()-radius).intValue();
                }
                else if (y + speedY <= radius) {
                                                    // �������� ������� ������
                    speedY = -speedY;
                    y = radius;
                }
                else if (y + speedY >= field.getHeight() - radius) {
                                                    // �������� ������ ������
                    speedY = -speedY;
                    y=new Double(field.getHeight()-radius).intValue();
                }

                else {
                                                    // ������ ���������
                    x += speedX;
                    y += speedY;
                }


                                                // �������� �� X �����������, ��� X ������������
                                                // ������ �� ��������
                                                // �������� = 1 (��������), �������� �� 15 ��.
                                                // �������� = 15 (������), �������� �� 1 ��.
                Thread.sleep(16-speed);
            }
        } catch (InterruptedException ex) {
                                            // ���� ��� ��������, �� ������ �� ������
                                            // � ������ ������� (�����������)
        }
    }

                                    // ����� ���������� ������ ����
    public void paint(Graphics2D canvas) {
        if(!superflag) {
            canvas.setColor(color);
            canvas.setPaint(color);
        }
        else {
            canvas.setColor(color);
            canvas.setPaint(Color.RED);

        }
        Ellipse2D.Double ball = new Ellipse2D.Double(x-radius, y-radius, 2*radius, 2*radius);
        canvas.draw(ball);
        canvas.fill(ball);
    }
}