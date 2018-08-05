package entry.ren;

import entry.common.option.NoArgOption;
import entry.common.option.OptionBuilder;
import entry.common.option.SingleArgOption;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public enum RenOptions {
    Help(NoArgOption.full("h", "help", "Usage")),
    Fumen(SingleArgOption.full("t", "tetfu", "v115@~", "Specify tetfu data for s-finder settings")),
    Page(SingleArgOption.full("P", "page", "number", "Specify pages of tetfu data for s-finder settings")),
    FieldPath(SingleArgOption.full("fp", "file-path", "path", "File path of field definition")),
    Patterns(SingleArgOption.full("p", "patterns", "definition", "Specify pattern definition, directly")),
    PatternsPath(SingleArgOption.full("pp", "patterns-path", "path", "File path of pattern definition")),
    LogPath(SingleArgOption.full("lp", "log-path", "path", "File path of output log")),
    OutputBase(SingleArgOption.full("o", "output-base", "path", "Base file path of result to output")),
    Hold(SingleArgOption.full("H", "hold", "use or avoid", "If use hold, set 'use'. If not use hold, set 'avoid'")),
    Drop(SingleArgOption.full("d", "drop", "hard or soft", "Specify drop")),;

    private final OptionBuilder optionBuilder;

    RenOptions(OptionBuilder optionBuilder) {
        this.optionBuilder = optionBuilder;
    }

    public String optName() {
        return optionBuilder.getLongName();
    }

    public static Options create() {
        Options options = new Options();

        for (RenOptions renOptions : RenOptions.values()) {
            Option option = renOptions.optionBuilder.toOption();
            options.addOption(option);
        }

        return options;
    }
}
