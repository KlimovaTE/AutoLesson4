
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    @Test
    void shouldBookMeeting() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control").sendKeys(Keys.BACK_SPACE);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        LocalDate date = LocalDate.now().plusDays(3);
//        String dataString = date.format(formatter);
        $("[data-test-id='date'] .input__control").setValue(LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        $("[data-test-id='name'] input").setValue("Тестовый Случай");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
    }

}
