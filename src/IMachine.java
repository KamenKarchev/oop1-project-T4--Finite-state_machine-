public interface IMachine extends IDirectedGraph{
    //M = ( G = (V, E), Alphabet, Starts, Ends)
    void setAlphabet();
    void isInAlphabet();
    void isStart();
    void getStarts();
    void isEnd();
    void getEnds();
}
