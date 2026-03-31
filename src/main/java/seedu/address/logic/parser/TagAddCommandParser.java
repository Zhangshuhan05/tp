package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagAddCommand object
 */
public class TagAddCommandParser implements Parser<TagAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagAddCommand
     * and returns a TagAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String preamble = argMultimap.getPreamble().trim();

        String[] preambleParts = preamble.split("\\s+");
        for (String part : preambleParts) {
            if (part.matches("\\S+/\\S*")) { // token contains a prefix-like pattern e.g. l/123 or l/
                throw new ParseException("Invalid prefix detected: " + part);
            }
        }

        if (preambleParts.length == 0) {
            throw new ParseException("Missing index.");
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(preambleParts[0]);
        } catch (ParseException pe) {
            throw new ParseException("Index is not a non-zero unsigned integer.");
        }

        List<String> allTags = argMultimap.getAllValues(PREFIX_TAG);
        if (allTags.isEmpty()) {
            throw new ParseException("Missing tag value. Use prefix t/TAG.");
        }
        if (allTags.size() > 1) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE));
        }

        Tag tag;
        try {
            tag = ParserUtil.parseTag(allTags.get(0));
        } catch (ParseException pe) {
            throw new ParseException("Invalid tag format. Tags must be 1–20 chars (a-z, 0-9, -).");
        }

        return new TagAddCommand(tag, index);
    }
}
