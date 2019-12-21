package agh.cs.evolution;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Animal a);
}
