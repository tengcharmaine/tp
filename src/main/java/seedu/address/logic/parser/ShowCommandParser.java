package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.IdentityCardNumber;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;

/**
 * Parses input arguments and creates a new ShowCommand object
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShowCommand
     * and returns a ShowCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return ShowCommand.createClearCommand();
        }

        try {
            IdentityCardNumber ic = ParserUtil.parseIC(trimmedArgs);
            return ShowCommand.createShowCommand(new IdentityCardNumberMatchesPredicate(ic));
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE), e);
        }
    }

}
