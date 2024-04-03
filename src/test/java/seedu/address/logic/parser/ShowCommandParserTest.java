package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.person.IdentityCardNumber;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;

public class ShowCommandParserTest {

    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_emptyArg_returnsShowCommand() {
        ShowCommand expectedShowCommand = ShowCommand.createClearCommand();
        assertParseSuccess(parser, "", expectedShowCommand);
        assertParseSuccess(parser, "       ", expectedShowCommand);
        assertParseSuccess(parser, " \n \t \t", expectedShowCommand);
    }

    @Test
    public void parse_validArgs_returnsShowCommand() {
        ShowCommand expectedShowCommand = ShowCommand.createShowCommand(new IdentityCardNumberMatchesPredicate(
                new IdentityCardNumber("S1234567A")));
        assertParseSuccess(parser, "S1234567A", expectedShowCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n"
                + " S1234567A \n"
                + " \t  \t", expectedShowCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // IC with incorrect format
        assertParseFailure(parser, "S1234", IdentityCardNumber.MESSAGE_CONSTRAINTS);

        // IC with incorrect format and additional arguments
        assertParseFailure(parser, "S1234 extra", IdentityCardNumber.MESSAGE_CONSTRAINTS);

        // IC with correct format but contains non-alphanumeric characters
        assertParseFailure(parser, "S1234$%^", IdentityCardNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }

}
