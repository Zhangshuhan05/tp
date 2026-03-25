package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Filters the address book to show only contacts in a specific circle.
 */
public class CircleFilterCommand extends Command {
    public static final String COMMAND_WORD = "circlefilter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all contacts in the given circle.\n"
        + "Parameters: CIRCLE_NAME\n" + "Example: " + COMMAND_WORD + " client";
    public static final String MESSAGE_SUCCESS = "Filtered contacts by circle '%1$s'";
    public static final String MESSAGE_NO_CONTACTS_FOUND = "No contacts found in the '%1$s' circle.";

    private final String circleName;

    public CircleFilterCommand(String circleName) {
        this.circleName = circleName.toLowerCase(); // assume parser already validated
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(person -> person.getCircle()
            .map(c -> c.equals(circleName))
            .orElse(false));

        List<Person> filteredList = model.getFilteredPersonList();
        if (filteredList.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_CONTACTS_FOUND, circleName));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, circleName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CircleFilterCommand)) {
            return false;
        }
        CircleFilterCommand otherCommand = (CircleFilterCommand) other;
        return circleName.equals(otherCommand.circleName);
    }

    @Override
    public String toString() {
        return "CircleFilterCommand[circle=" + circleName + "]";
    }
}
