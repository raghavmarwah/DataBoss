/*
 * The JAVA ObjectOutputStream corrupts the file stream when trying to append objects
 * This is a workaround class which overrides writeStreamHeader() method to prevent data corruption.
 * https://stackoverflow.com/questions/1194656/appending-to-an-objectoutputstream
 */

import java.io.*;

public class AppendingObjectOutputStream extends ObjectOutputStream {

  public AppendingObjectOutputStream(OutputStream out) throws IOException {
    super(out);
  }

  @Override
  protected void writeStreamHeader() throws IOException {
    // do not write a header, but reset:
    // this line added after another question
    // showed a problem with the original
    reset();
  }

}
