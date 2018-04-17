package gluka.morsecodeggc;

public class Signal {

    final static boolean ON = true;
    final static boolean OFF = false;

    private boolean on = false;
    private long onset = 0L;
    private int duration; // msecs
    private int characterNumber; // position of corresponding char in orig string

    public Signal(){

    }
    public Signal(int _charNum, boolean _state, long _onset, int _duration) {
        this.characterNumber = _charNum;
        this.on = _state;
        this.onset = _onset;
        this.duration = _duration;
    }

    public long getOnset() {
        return onset;
    }
    public boolean isOn() {
        return on;
    }
    public int getDuration() {
        return duration;
    }
    public int getCharacterNumber() {
        return characterNumber;
    }

}
