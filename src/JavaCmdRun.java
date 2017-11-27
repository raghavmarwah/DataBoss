import java.io.IOException;

public class JavaCmdRun {
	public JavaCmdRun(String cmd) throws IOException{
		Runtime.getRuntime().exec(cmd);
	}
}
