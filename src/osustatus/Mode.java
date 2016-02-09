package osustatus;

public enum Mode
    {
        STANDARD(0), TAIKO(1), CTB(2), MANIA(3);
        
        private final int value;
        private Mode(int value)
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return value;
        }
    }