package spark.template.handlebars;

import com.github.jknack.handlebars.Helper;

import org.junit.Before;
import org.junit.Test;

import spark.ModelAndView;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandlebarsTemplateEngineTest {

    private HandlebarsTemplateEngine hte;

    @Before
    public void setup() throws Exception {
        hte = new HandlebarsTemplateEngine();
    }

    @Test
    public void render() throws Exception {
        String templateVariable = "Hello Handlebars!";
        Map<String, Object> model = new HashMap<>();

        model.put("message", templateVariable);
        String expected = String.format("<h1>%s</h1>", templateVariable);
        String actual = hte.render(new ModelAndView(model, "hello.hbs"));

        assertEquals(expected, actual);
    }

    @Test
    public void helperShouldBeRegistered() throws Exception {
        String name = "stringHelper";
        Helper<String> helper = (s, options) -> options.equals(s);

        hte.registerHelper(name, helper);

        assertTrue(hte.handlebars.helper(name).equals(helper));
    }

    @Test
    public void helpersShouldBeRegistered() throws Exception {
        File file = Paths.get(ClassLoader.getSystemResource("helpers/test.js").toURI()).toFile();
        int initialHelperCount = hte.handlebars.helpers().size();

        hte.registerHelpers(file);

        assertTrue(hte.handlebars.helper("test") != null);
        assertTrue(hte.handlebars.helpers().size() == initialHelperCount + 2);
    }

    @Test
    public void helperShouldOutputDateToView() throws Exception {
        Helper<LocalDate> helper = (s, options) -> LocalDate.now();

        hte.registerHelper("today", helper);
        String actual = hte.render(new ModelAndView(null, "helper_test.hbs"));

        assertTrue(actual.equals("Today is " + LocalDate.now().toString()));
    }
}