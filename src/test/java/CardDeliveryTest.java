
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {
    String GenerateDate(int daysPlus, String pattern) {
        return LocalDate.now().plusDays(daysPlus).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldBookMeeting() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] .input__control").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.BACK_SPACE));
        $("[data-test-id='date'] .input__control").setValue(GenerateDate(3, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Тестовый Случай");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText(GenerateDate(3, "dd.MM.yyyy"))).shouldBe(visible);
    }

    @Test
    void shouldBookMeetingWithComplexElements() {
        $("[data-test-id='city'] input").setValue("вл");
        $(byText("Владивосток")).click();
        $(".input__icon").click();
        LocalDate dateDefault = LocalDate.now();
        LocalDate dateMeeting = LocalDate.now().plusDays(7);
        if (dateMeeting.getMonthValue() > dateDefault.getMonthValue() | dateMeeting.getYear() > dateDefault.getYear()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(GenerateDate(7, "d"))).click();
        $("[data-test-id='name'] input").setValue("Тестовый Случай");
        $("[data-test-id='phone'] input").setValue("+79200000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(byText("Встреча успешно забронирована на")).shouldBe(visible, Duration.ofSeconds(15));
        $(byText(GenerateDate(7, "dd.MM.yyyy"))).shouldBe(visible);
    }
}
