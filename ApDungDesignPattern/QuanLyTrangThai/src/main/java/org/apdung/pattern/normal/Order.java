package org.apdung.pattern.normal;

public class Order {
    private State state;
    public Order(State state) {
        this.state = state;
    }

    public Order() {
        this.state = State.CREATED;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean changeState(State newState) {
        switch (this.state) {
            case CREATED:
                if(newState == State.PROCESSING ||  newState == State.CANCELLED){
                    this.state = newState;
                    return true;
                }
                break;
            case PROCESSING:
                if(newState == State.DELIVERED || newState == State.CANCELLED){
                    this.state = newState;
                    return true;
                }
                break;

            case DELIVERED:
                return false;
            case CANCELLED:
                return false;
        }
        return false;
    }
}
