import java.util.ResourceBundle;
import java.util.Locale;
import java.text.MessageFormat;

public class FormatExample {

  public static void main(String[] args) {
    // Load the resource bundle
    ResourceBundle bundle = ResourceBundle.getBundle( "ApplicationResources" );

    // Get the message template
    String requiredFieldMessage = bundle.getString( "error.requiredfield" );

    // Create a string array of size one to hold the arguments
    String[] messageArgs = new String[1];

    // Get the "Name" field from the bundle and load it in as an argument
    messageArgs[0] = bundle.getString( "label.name" );

    // Format the message using the message and the arguments
    String formattedNameMessage =
      MessageFormat.format( requiredFieldMessage, messageArgs );

    System.out.println( formattedNameMessage );

    // Get the "Phone" field from the bundle and load it in as an argument
    messageArgs[0] = bundle.getString( "label.phone" );

    // Format the message using the message and the arguments
    String formattedPhoneMessage =
      MessageFormat.format( requiredFieldMessage, messageArgs );

    System.out.println( formattedPhoneMessage );
  }
}
