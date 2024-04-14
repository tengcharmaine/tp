package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose IC matches the argument IC.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose profile matches "
            + "the specified IC (case-insensitive) and displays them.\n"
            + "Parameters: IC\n"
            + "Example: " + COMMAND_WORD + " t1234567A";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final IdentityCardNumberMatchesPredicate predicate;

    public FindCommand(IdentityCardNumberMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        assert model.getFilteredPersonList().size() <= 1 : "There should be at most one person in the filtered list";

        if (model.getFilteredPersonList().size() == 1) {
            Person person = model.getFilteredPersonList().get(0);
            model.setDisplayNote(person);
            logger.info("Find command has been executed on Person with IC Number: " + person.getIdentityCardNumber());
        } else {
            logger.info("Find command has been executed with no matching Person found.");
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
