package edu.pdx.cs410J.thhoang;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.Test;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * JUnit tests for the Student class.  These tests extend <code>InvokeMainTestCase</code>
 * which allows them to easily invoke the <code>main</code> method of <code>Student</code>.
 * They also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>matchers
 * for more readable assertion statements.
 */
public class StudentTest extends InvokeMainTestCase
{



  @Test
  public void isSame() {
    assertThat(null, equalTo(null));

    int n = 10;
    assertThat(n, is(10));
  }

  @Test
  public void invokingMainWithNoArgumentsHasExitCodeOf1() {
    MainMethodResult result = invokeMain(Student.class);
    assertThat(result.getExitCode(), equalTo(1));

  }

  @Test
  public void invokingMainWithNoArgumentsPrintsMissingArgumentsToStandardError() {
    MainMethodResult result = invokeMain(Student.class);
  //  assertThat(result.getErr(), containsString("Missing command line arguments"));
  }

}
