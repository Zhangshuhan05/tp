package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Circle;
import seedu.address.model.person.Person;

/**
 * Adds a circle to a contact in the address book.
 */
public class CircleAddCommand extends Command {
    public static final String COMMAND_WORD = "circleadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a circle to a contact.\n"
        + "Parameters: INDEX c/CIRCLE_NAME\n" + "Example: "
        + COMMAND_WORD + " 1 c/client";

    public static final String MESSAGE_CIRCLE_PERSON_SUCCESS = "Added circle '%1$s' to %2$s";
    public static final String MESSAGE_CIRCLE_PERSON_FAILURE = "Add failed: contact already has a circle.";
    public static final String MESSAGE_INVALID_PERSON = "The person does not exist in the address book.";

    private static final Logger logger = LogsCenter.getLogger(CircleAddCommand.class);

    private final Circle circle;
    private final Index index;

    public CircleAddCommand(Circle circle, Index index) {
        requireNonNull(circle);
        requireNonNull(index);
        this.circle = circle;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size() || index.getOneBased() <= 0) {
            logger.warning("Index out of bounds in CircleAddCommand: " + index.getOneBased());
            throw new CommandException(MESSAGE_INVALID_PERSON);
        }

        Person personAtIndex = lastShownList.get(index.getZeroBased());
        if (personAtIndex.getCircle().isPresent()) {
            logger.info("Attempted to add circle to a person who already has one: " + personAtIndex.getName());
            throw new CommandException(MESSAGE_CIRCLE_PERSON_FAILURE);
        }

        assert Circle.isValidCircleName(circle.circleName) : "Circle name should always be valid";

        Person editedPerson = new Person(
            personAtIndex.getName(),
            personAtIndex.getPhone(),
            personAtIndex.getEmail(),
            personAtIndex.getAddress(),
            personAtIndex.getTags(),
            personAtIndex.getFollowUpDate(),
            personAtIndex.getNotes(),
            Optional.of(circle.circleName)
        );

        model.setPerson(personAtIndex, editedPerson);
        logger.fine("Circle added: " + circle.circleName + " to person: " + editedPerson.getName());

        return new CommandResult(String.format(MESSAGE_CIRCLE_PERSON_SUCCESS, circle.circleName, editedPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CircleAddCommand)) {
            return false;
        }
        CircleAddCommand o = (CircleAddCommand) other;
        return index.equals(o.index) && circle.equals(o.circle);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("index", index)
            .add("circle", circle)
            .toString();
    }
}
