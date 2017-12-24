package elements;

import javafx.scene.control.TextArea;

/**
 * Created by IntelliJ IDEA.<br/>
 * User: krako<br/>
 * Date: 11.12.2017<br/>
 * Time: 22:09<br/>
 */
public class Alphabet72TextArea extends TextArea {
    @Override
    public void replaceText(int start, int end, String text)
    {
        if (validate(text))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
        return text.matches("[А-ЯЁ]|[а-яё]|[,.!?:]*|\\s");
    }
}
