package userinterface;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MessageView extends Text
{
    public MessageView(String message)
    {
        super(message);

        setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        setFill(Color.BLUE);
        setTextAlignment(TextAlignment.LEFT);
    }

    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        setFill(Color.BLUE);
        setText(message);
    }

    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        // Display errors in red
        setFill(Color.RED);
        setText(message);
    }

    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        setText("                                     ");
    }
}
