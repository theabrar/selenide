package integration;

import com.codeborne.selenide.ex.DialogTextMismatch;
import com.codeborne.selenide.junit.ScreenShooter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.isHeadless;
import static com.codeborne.selenide.junit.ScreenShooter.failedTests;
import static java.lang.Thread.currentThread;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ConfirmTest {
  @Parameterized.Parameters
  public static List<Object[]> names() {
    // TODO Add ", new String[]{"Серафим"}". Now it works unstable in Chrome.
    return Arrays.asList(new Object[]{"John Mc'Clane"}, new String[]{"L ucie"});
  }

  @Rule
  public ScreenShooter screenshots = failedTests().succeededTests();

  private final String userName;

  public ConfirmTest(String userName) {
    this.userName = userName;
  }

  @Before
  public void openTestPage() {
    open(currentThread().getContextClassLoader().getResource("page_with_alerts.html"));
    $("h1").shouldHave(text("Page with alerts"));
    $(By.name("username")).val(userName);
  }

  @Test
  public void canSubmitConfirmDialog() {
    onConfirmReturn(true);
    $(byText("Confirm button")).click();
    confirm("Get out of this page, " + userName + '?');
    $("h1").shouldHave(text("Page with JQuery"));
  }

  @Test
  public void canCancelConfirmDialog() {
    onConfirmReturn(false);
    $(byText("Confirm button")).click();
    dismiss("Get out of this page, " + userName + '?');
    $("#message").shouldHave(text("Stay here, " + userName));
    $("#container").shouldNotBe(empty);
  }

  @Test
  public void selenideChecksDialogText() {
    $(byText("Confirm button")).click();
    try {
      confirm("Get out of this page, Maria?");
    }
    catch (DialogTextMismatch expected) {
      return;
    }
    if (!isHeadless()) {
      fail("Should throw DialogTextMismatch for mismatching text");
    }
  }
}
