/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.com.softproject.utils.text.output;

/**
 *
 * @author Adrian Lapierre alapierre@softproject.com.pl
 */
public interface OutputPorccesor<T> {

    public void process(T t, Output out);
}
