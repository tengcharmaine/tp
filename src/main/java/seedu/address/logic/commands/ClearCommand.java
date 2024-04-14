package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import java.util.logging.Logger;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "ClinicMate has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        logger.info("Clear command has been executed.");
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
