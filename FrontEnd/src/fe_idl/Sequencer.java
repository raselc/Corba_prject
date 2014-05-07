package fe_idl;

public class Sequencer {
	static int MsgSeq = 0;
	
	public synchronized String getSequence(){
		MsgSeq ++;
		return String.valueOf(MsgSeq);
	}
	
}
