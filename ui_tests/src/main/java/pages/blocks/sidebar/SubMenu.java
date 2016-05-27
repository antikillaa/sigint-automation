package pages.blocks.sidebar;

import com.codeborne.selenide.SelenideElement;
import conditions.UIConditions;
import pages.SigintPage;
import conditions.Verify;

import static com.codeborne.selenide.Selenide.page;

public class SubMenu  {

    private SelenideElement element;
    private SigintPage page;

    public SubMenu(SelenideElement element, SigintPage page) {
        this.page = page;
        this.element = element;
    }

    public SigintPage click() {
        element.click();
        Verify.shouldBe(UIConditions.disappear.element(page.getPageLoading()));
        return page(page);

    }
}
