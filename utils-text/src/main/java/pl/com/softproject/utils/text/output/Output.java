package pl.com.softproject.utils.text.output;

import java.util.Collection;
import java.util.Iterator;

/**
 * The Output is where the elements of the query output their bits of SQL to.
 *
 * 
 */
public class Output {

  /**
   * @param indent String to be used for indenting (e.g. "", "  ", "       ", "\t")
   */
  public Output(String indent) {
    this.indent = indent;
  }

  private StringBuilder result = new StringBuilder();
  private StringBuilder currentIndent = new StringBuilder();
  private boolean newLineComing;

  private final String indent;

  @Override
  public String toString() {
    return result.toString();
  }

  public Output print(Object o) {
    writeNewLineIfNeeded();
    result.append(o);
    return this;
  }

  public Output print(char c) {
    writeNewLineIfNeeded();
    result.append(c);
    return this;
  }

  public Output println(Object o) {
    writeNewLineIfNeeded();
    result.append(o);
    newLineComing = true;
    return this;
  }

  public Output println() {
    newLineComing = true;
    return this;
  }
  
  public Output clearLastChar() {
    result.delete(result.length()-1, result.length());
    return this;  
  }

  public Output clearLastChar(short count) {
    result.delete(result.length()-count, result.length());
    return this;
  }

  public void clear() {
      result = new StringBuilder();
      currentIndent = new StringBuilder();
  }

  /**
   * Iterate through a Collection and append all entries (using .toString()) to
   * a StringBuffer.
   */
  public void appendList(Collection collection, String seperator) {
    Iterator i = collection.iterator();
    boolean hasNext = i.hasNext();

    while (hasNext) {
      Object curr = i.next();
      hasNext = i.hasNext();
      this.print(curr);
      //curr.write(this);
      print(' ');
      if (hasNext) {
        print(seperator);
      }
      println();
    }
  }
  
  //OutputPorccesor
  
  /**
   * Iterate through a Collection and append all entries (using OutputPorccesor.prosess method) to
   * a StringBuffer.
   */
  public void appendList(Collection collection, String seperator, OutputPorccesor porccesor) {
    Iterator i = collection.iterator();
    boolean hasNext = i.hasNext();

    while (hasNext) {
      Object curr = i.next();
      hasNext = i.hasNext();
      porccesor.process(curr, this);  //curr.write(this);
      print(' ');
      if (hasNext) {
        print(seperator);
      }
      println();
    }
  }

  public void indent() {
    currentIndent.append(indent);
  }

  public void unindent() {
    currentIndent.setLength(currentIndent.length() - indent.length());
  }

  private void writeNewLineIfNeeded() {
    if (newLineComing) {
      result.append('\n').append(currentIndent);
      newLineComing = false;
    }
  }
}
