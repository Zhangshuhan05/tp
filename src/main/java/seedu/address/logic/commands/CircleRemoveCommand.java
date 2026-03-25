package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.circle.Circle;

/**
 * Removes the circle from a contact in the address book.
 */
public class CircleRemoveCommand extends Command {
    public static final String COMMAND_WORD = "circlerm";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the circle from a contact in the address book.\n"
        + "Parameters: INDEX\n" + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_CIRCLE_PERSON_SUCCESS = "Removed circle from %1$s";
    public static final String MESSAGE_CIRCLE_PERSON_FAILURE = "Remove failed: contact does not have a circle set.";
    public static final String MESSAGE_INVALID_PERSON = "The person does not exist in the address book.";

    private final Index index;

    public CircleRemoveCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size() || index.getOneBased() <= 0) {
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        Person personAtIndex = lastShownList.get(index.getZeroBased());
        if (personAtIndex.getCircle().isEmpty()) {
            throw new CommandException(MESSAGE_CIRCLE_PERSON_FAILURE);
        }

        Person editedPerson = new Person(
            personAtIndex.getName(),
            personAtIndex.getPhone(),
            personAtIndex.getEmail(),
            personAtIndex.getAddress(),
            personAtIndex.getTags(),
            personAtIndex.getFollowUpDate(),
            personAtIndex.getNotes(),
            java.util.Optional.empty()
        );

        model.setPerson(personAtIndex, editedPerson);
        return new CommandResult(String.format(MESSAGE_CIRCLE_PERSON_SUCCESS, editedPerson.getName()));
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .toString();
    }
}
