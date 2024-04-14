package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;
import seedu.address.model.person.Person;

/**
 * Shows the user a note of a person in the address book.
 * If no IC is given, the displayed note panel will be cleared.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_SHOW_NOTE_SUCCESS = "Displayed note of person: %1$s";

    public static final String MESSAGE_CLEAR_NOTE_SUCCESS = "Note cleared.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the note of the person whose IC matches the "
            + "specified valid IC (case-insensitive) and displays it.\n"
            + "If no IC is given, the displayed note panel will be cleared.\n"
            + "Parameters: IC (optional)\n"
            + "Example (to display note): " + COMMAND_WORD + " t1234567A\n"
            + "Example (to clear display): " + COMMAND_WORD;

    private static final Logger logger = LogsCenter.getLogger(ShowCommand.class);

    private final IdentityCardNumberMatchesPredicate icPredicate;

    private final boolean isClear;

    /**
     * Creates a ShowCommand to show the note of the person whose profile matches the specified {@code icPredicate}.
     * @param icPredicate of the person in the filtered person list to show the note
     */
    private ShowCommand(IdentityCardNumberMatchesPredicate icPredicate, boolean isClear) {
        this.icPredicate = icPredicate;
        this.isClear = isClear;
    }

    /**
     * Factory method to create a ShowCommand to show the note of the person whose profile matches
     * the specified {@code icPredicate}.
     * @param icPredicate of the person in the filtered person list to show the note
     */
    public static ShowCommand createShowCommand(IdentityCardNumberMatchesPredicate icPredicate) {
        return new ShowCommand(icPredicate, false);
    }

    /**
     * Factory method to create a ShowCommand to clear the displayed note panel.
     */
    public static ShowCommand createClearCommand() {
        return new ShowCommand(null, true);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isClear) {
            model.clearDisplayNote();
            logger.info("Show command has been executed and cleared display note.");
            return new CommandResult(MESSAGE_CLEAR_NOTE_SUCCESS);
        }

        Person person = model.getPersonIfExists(icPredicate)
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_NO_MATCHING_IC));
        model.setDisplayNote(person);

        logger.info("Show command has been executed, displaying note of Person with IC Number:"
                + person.getIdentityCardNumber());
        return new CommandResult(
                String.format(MESSAGE_SHOW_NOTE_SUCCESS, person.getIdentityCardNumber())
        );
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShowCommand)) {
            return false;
        }

        ShowCommand e = (ShowCommand) other;
        // icPredicate is not important if isClear is true
        if (isClear && e.isClear) {
            return true;
        }
        if (isClear != e.isClear) {
            return false;
        }
        if (icPredicate == null || e.icPredicate == null) {
            return icPredicate == e.icPredicate;
        }
        return icPredicate.equals(e.icPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", icPredicate)
                .add("isClear", isClear)
                .toString();
    }
}
