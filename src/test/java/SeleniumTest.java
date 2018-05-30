import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumTest {

    private static final String TEST_LINE = "Title43565463456";
    private static final String SITE_BLOG = "http://igorakintev.ru/blog/";
    private static final String SITE_ADMIN = "http://igorakintev.ru/admin/";
    private static final String STUB_USER = "silenium";
    private static final String STUB_PASS = "super_password";

    private ChromeDriver driver;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "/inetpub/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void testAddLine() {
        authorizationAdmin();

        Assert.assertTrue(driver.findElement(By.className("dashboard-title")) != null);

        driver.findElement(By.cssSelector("[href='/admin/blog/entry/add/']")).click();
        Assert.assertTrue(driver.findElement(By.id("content")) != null);

        driver.findElement(By.id("id_title")).sendKeys(TEST_LINE);

        WebElement slug = driver.findElement(By.id("id_slug"));
        slug.clear();
        slug.sendKeys("Slug43565463456");

        driver.findElement(By.id("id_text_markdown")).sendKeys("Slug43565463456");
        driver.findElement(By.id("id_text")).sendKeys("Slug43565463456");
        driver.findElement(By.name("_save")).click();
        driver.findElement(By.xpath("//*[@id=\"result_list\"]/tbody")).findElement(By.tagName("tr")).findElement(By.tagName("th")).getText();
    }

    @Test
    public void testCheckLineAndDeleteLine() {
        driver.get(SITE_BLOG);
        driver.findElement(By.linkText(TEST_LINE));
        System.out.println("Запись найдена");
        authorizationAdmin();

        WebElement element = driver.findElement(By.linkText("Entries"));
        Assert.assertNotNull(element);
        element.click();

        WebElement table = driver.findElement(By.tagName("tbody"));
        Assert.assertNotNull(table);
        driver.findElement(By.linkText(TEST_LINE)).findElement(By.xpath("//*[@id=\"result_list\"]/tbody/tr[1]/td[1]")).click();

        Select action = new Select(driver.findElement(By.name("action")));
        action.selectByVisibleText("Удалить выбранные Entries");
        action.selectByIndex(1);

        driver.findElement(By.xpath("//*[@id=\"changelist-form\"]/div[2]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/input[4]")).click();
    }

    private void authorizationAdmin() {
        driver.get(SITE_ADMIN);
        driver.findElementByName("username").sendKeys(STUB_USER);
        driver.findElementByName("password").sendKeys(STUB_PASS);
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }
}
