import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
import java.util.List;

public class MenuPage extends CommonAction {
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"tab bar option menu\"]")
    List<WebElement> iconHamburger;
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"tab bar option cart\"]")
    List<WebElement> iconCart;
    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"tab bar option catalog\"]")
    List<WebElement> textMenuCatalog;
    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"menu item log in\"]")
    List<WebElement> textMenuLogin;

    public MenuPage(IOSDriver iosDriver) {
        super(iosDriver);
        PageFactory.initElements(new AppiumFieldDecorator(iosDriver, Duration.ofSeconds(5)), this);
    }

    @Step("Select Menu \"Catalog\"")
    public void selectMenuCatalog(){
        iconHamburger.get(0).click();
        textMenuCatalog.get(0).click();
    }

    @Step("Select Menu \"Log In\"")
    public void selectMenuLogIn(){
        iconHamburger.get(0).click();
        textMenuLogin.get(0).click();
    }
}
