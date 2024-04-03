package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.IdentityCardNumber;
import seedu.address.model.person.IdentityCardNumberMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ShowCommand}.
 */
public class ShowCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        IdentityCardNumberMatchesPredicate firstPredicate =
                new IdentityCardNumberMatchesPredicate(new IdentityCardNumber("S1234567A"));
        IdentityCardNumberMatchesPredicate secondPredicate =
                new IdentityCardNumberMatchesPredicate(new IdentityCardNumber("S9876543B"));

        ShowCommand showFirstCommand = ShowCommand.createShowCommand(firstPredicate);
        ShowCommand showSecondCommand = ShowCommand.createShowCommand(secondPredicate);

        // same object -> returns true
        assertTrue(showFirstCommand.equals(showFirstCommand));

        // same values -> returns true
        ShowCommand showFirstCommandCopy = ShowCommand.createShowCommand(firstPredicate);
        assertTrue(showFirstCommand.equals(showFirstCommandCopy));

        // different types -> returns false
        assertFalse(showFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(showFirstCommand.equals(showSecondCommand));

        // different isClear -> returns false
        ShowCommand showFirstCommandClear = ShowCommand.createClearCommand();
        assertFalse(showFirstCommand.equals(showFirstCommandClear));

        // same isClear -> returns true
        ShowCommand showSecondCommandClear = ShowCommand.createClearCommand();
        assertTrue(showFirstCommandClear.equals(showSecondCommandClear));
    }

    @Test
    public void execute_validIC_singlePersonFound() {
        String testNumber = ALICE.getIdentityCardNumber().value;
        String expectedMessage = String.format(ShowCommand.MESSAGE_SHOW_NOTE_SUCCESS, testNumber);
        IdentityCardNumberMatchesPredicate predicate = preparePredicate(testNumber);
        ShowCommand command = ShowCommand.createShowCommand(predicate);
        expectedModel.setDisplayNote(ALICE);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noIC_clearNote() {
        String expectedMessage = ShowCommand.MESSAGE_CLEAR_NOTE_SUCCESS;
        ShowCommand command = ShowCommand.createClearCommand();
        expectedModel.clearDisplayNote();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringMethod() {
        IdentityCardNumberMatchesPredicate predicate = new IdentityCardNumberMatchesPredicate(
                new IdentityCardNumber("S1234567A"));
        ShowCommand showCommand = ShowCommand.createShowCommand(predicate);
        String expected = ShowCommand.class.getCanonicalName() + "{predicate=" + predicate + ", isClear=" + "false}";
        assertEquals(expected, showCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code IdentityCardNumberMatchesPredicate}.
     */
    private IdentityCardNumberMatchesPredicate preparePredicate(String userInput) {
        return new IdentityCardNumberMatchesPredicate(new IdentityCardNumber(userInput));
    }
}
