package integration;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.getSelectedRadio;
import static com.codeborne.selenide.Selenide.selectRadio;
import static com.codeborne.selenide.Selenide.open;
import static java.lang.Thread.currentThread;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RadioTest {
  @Before
  public void openTestPage() {
    open(currentThread().getContextClassLoader().getResource("page_with_selects_without_jquery.html"));
  }

  @Test
  public void userCanSelectRadioButtonByValue() {
    assertNull(getSelectedRadio(By.name("me")));

    selectRadio(By.name("me"), "ready");
    assertEquals("ready", getSelectedRadio(By.name("me")).val());
//    assertEquals("Я готов", $.getSelectedRadio(By.name("me")).getText()); // Text is empty for unknown reason :(
  }

  @Test
  public void userCanSelectRadioButtonByValueOldWay() {
    assertNull(getSelectedRadio(By.name("me")));

    selectRadio(By.name("me"), "ready");
    assertEquals("ready", getSelectedRadio(By.name("me")).getAttribute("value"));
//    assertEquals("Я готов", getSelectedRadio(By.name("me")).getText()); // Text is empty for unknown reason :(
  }
}
