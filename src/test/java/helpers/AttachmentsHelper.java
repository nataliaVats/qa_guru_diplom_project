package helpers;

import config.ConfigHelper;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static helpers.BrowserstackHelper.getBrowserstackVideoUrl;


public class AttachmentsHelper {

    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] attachScreenshot(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Page source", type = "text/plain")
    public static byte[] attachPageSource() {
        return getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8);
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String attachVideo(String sessionId) {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl(sessionId)
                + "' type='video/mp4'></video></body></html>";
    }

    public static String getVideoUrl(String sessionId) {
        if(ConfigHelper.isWeb()) {
            return getWebVideoUrl(sessionId);
        } else if (ConfigHelper.isAndroid() || ConfigHelper.isIos()){
            return getBrowserstackVideoUrl(sessionId);
        } else {
            return null; // todo isDesktop
        }
    }

    public static String getWebVideoUrl(String sessionId) {
        try {
            return new URL(ConfigHelper.getWebVideoStorage() + "/" + sessionId + ".mp4") + "";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
