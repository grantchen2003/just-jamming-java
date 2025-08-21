import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Body {
    final private double mass;
    private double posX;
    private double posY;
    private double speedX;
    private double speedY;

    private Body(Builder builder) {
        this.mass = builder.mass;
        this.posX = builder.posX;
        this.posY = builder.posY;
        this.speedX = builder.speedX;
        this.speedY = builder.speedY;
    }

    public void move(List<Force> forces, double time) {
        Force netForce = new Force(0, 0);
        for (final Force force : forces) {
            netForce = new Force(netForce.x() + force.x(), netForce.y() + force.y());
        }

        move(netForce, time);
    }

    public void move(Force force, double time) {
        speedX += force.x() / mass * time;
        speedY += force.y() / mass * time;
        posX += speedX * time;
        posY += speedY * time;
    }

    public double getMass() {
        return mass;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getDistanceTo(Body other) {
        final double dx = posX - other.posX;
        final double dy = posY - other.posY;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public double getAngleTo(Body other) {
        return Math.atan2(other.posY - posY, other.posX - posX);
    }

    public Force gravitationalForceFrom(Body other) {
        final double GRAVITATIONAL_CONSTANT = 500; // not accurate so I don't have to deal with BigDecimal
        final double magnitude = GRAVITATIONAL_CONSTANT * mass * other.getMass() / Math.pow(getDistanceTo(other), 2);
        final double angleTo = getAngleTo(other);
        return new Force(magnitude * Math.cos(angleTo), magnitude * Math.sin(angleTo));
    }

    static class Builder {
        private final double mass;
        private double posX;
        private double posY;
        private double speedX;
        private double speedY;

        Builder(double mass) {
            if (mass <= 0) {
                throw new IllegalArgumentException("Mass must be positive");
            }
            this.mass = mass;
        }

        Builder posX(double posX) {
            this.posX = posX;
            return this;
        }

        Builder posY(double posY) {
            this.posY = posY;
            return this;
        }

        Builder speedX(double speedX) {
            this.speedX = speedX;
            return this;
        }

        Builder speedY(double speedY) {
            this.speedY = speedY;
            return this;
        }

        Body build() {
            return new Body(this);
        }
    }
}
